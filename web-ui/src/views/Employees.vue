<template>
  <div>
    <ConfirmDialog position="top"></ConfirmDialog>
    <Toast />
    <Sidebar v-model:visible="showSlideOut" position="right" style="width:700px">
      <EmployeeDetails :rec="selectedRec" @cancel="showSlideOut = false" @changed="getData()" :isNew="isNewRec"> </EmployeeDetails>
    </Sidebar>
    <h3> Manage Employees </h3>
    <div class="p-d-flex p-flex-row p-mb-1" style="width:1000px">
      <span class="p-input-icon-left">
        <i class="pi pi-search" />
        <InputText type="text" v-model="searchValue" @keyup="onSearchKeyup" class="p-inputtext-sm" placeholder="Search by name or email" style="width:210px;"/>
      </span>
      <div style="display:inline-block; flex:1"></div>
      <Button icon="pi pi-user" iconPos="right" label="ADD" @click="onAddClick()" class="p-ml-1 p-button-sm"></Button>
    </div>
    <DataTable
      :value = "list"
      :paginator = "true"
      :lazy = "true"
      :rows = "pageSize"
      :totalRecords = "totalRecs"
      :loading = "isLoading"
      @page="onPageChange($event)"
      class="p-datatable-sm p-datatable-hoverable-rows m-border p-mb-4" style="width:1000px">
      <Column field="employeeId" header="EMPLOYEE ID" headerStyle="width:110px;"></Column>
      <Column field="userId" header="USER ID" headerStyle="width:75px"></Column>
      <Column field="fullName" header="NAME"></Column>
      <Column field="email" header="EMAIL" headerStyle="width:210px"></Column>
      <Column field="jobTitle" header="JOB TITLE" headerStyle="width:210px"></Column>
      <Column field="department" header="DEPARTMENT" headerStyle="width:100px" ></Column>
      <Column header="ACTION" headerStyle="width:100px" bodyStyle="padding:3px">
        <template #body="slotProps">
          <Button icon="pi pi-pencil" @click="onEditClick(slotProps.data)" class="p-button-sm p-button-rounded p-button-secondary p-button-text" />
          <Button icon="pi pi-trash"  @click="onDeleteClick(slotProps.data)" class="p-button-sm p-button-rounded p-button-danger p-button-text" />
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<script lang='ts'>
import { ref, onMounted, defineComponent } from 'vue';
import EmployeeDetails from '@/views/EmployeeDetails.vue';
import EmployeeApi from '@/api/employee-api'; // eslint-disable-line import/no-cycle
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';
import { debounce } from '@/shared/utils';

export default defineComponent({
  setup(): unknown {
    const isLoading = ref(false);
    const showSlideOut = ref(false);
    const searchValue = ref('');
    const pageSize = ref(10);
    const totalPages = ref(0);
    const totalRecs = ref(0);
    const selectedRec = ref({});
    const isNewRec = ref(false);
    const isCustomer = ref(false);
    const list = ref([]);
    const confirm = useConfirm();
    const toast = useToast();
    let currentPage = 1;

    const getData = async (page:number, requestedPageSize: number, employeeId = '', nameOrEmail = '') => {
      // isLoading.value = true;
      try {
        const resp = await EmployeeApi.getEmployees(page, requestedPageSize, employeeId, nameOrEmail);
        list.value = resp.data.list.map((v:Record<string, unknown>) => ({
          ...v,
          fullName: `${v.firstName}  ${v.lastName}`,
        }));
        // isLoading.value = false;
        currentPage = resp.data.currentPage;
        totalPages.value = resp.data.totalPages;
        totalRecs.value = resp.data.total;
      } catch (err) {
        console.log('REST ERROR: %O', err.response ? err.response : err);
        isLoading.value = false;
      }
    };

    const confirmDialog = (rec: Record<string, unknown>) => {
      confirm.require({
        message: `Do you want to delete: ${rec.employeeId} ?`,
        header: 'Delete Confirmation',
        icon: 'pi pi-question-circle',
        acceptIcon: 'pi pi-check',
        accept: async () => {
          try {
            const resp = await EmployeeApi.deleteEmployee(rec.employeeId as string);
            if (resp.data.msgType === 'SUCCESS') {
              getData(currentPage, pageSize.value);
              toast.add({ severity: 'success', summary: 'Successfully Deleted', life: 3000 });
            } else {
              toast.add({ severity: 'error', summary: 'Access Denied', detail: resp.data.msg, life: 3000 });
            }
          } catch (e) {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Unable to connect to server', life: 3000 });
          }
        },
        reject: () => {
          console.log('NO');
        },
      });
    };

    const onPageChange = (event:Record<string, unknown>) => {
      if (currentPage !== (event.page as number + 1)) {
        currentPage = event.page as number + 1;
        getData(currentPage, pageSize.value);
      }
    };

    const onSearchKeyup = debounce(() => getData(1, pageSize.value, '', `%${searchValue.value}%`), 400);

    const onAddClick = () => {
      isNewRec.value = true;
      selectedRec.value = { id: '' };
      showSlideOut.value = true;
    };

    const onDeleteClick = (rec:Record<string, unknown>) => {
      confirmDialog(rec);
    };

    const onEditClick = async (rec:Record<string, unknown>) => {
      showSlideOut.value = true;
      selectedRec.value = rec;
    };

    onMounted(() => {
      getData(1, pageSize.value);
    });

    return {
      list,
      isLoading,
      showSlideOut,
      searchValue,
      pageSize,
      totalPages,
      totalRecs,
      selectedRec,
      isNewRec,
      isCustomer,
      onSearchKeyup,
      onAddClick,
      onDeleteClick,
      onEditClick,
      onPageChange,
      getData,
    };
  },
  components: {
    EmployeeDetails,
  },
});
</script>
