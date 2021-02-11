<template>
  <div>
    <ConfirmDialog position="top"></ConfirmDialog>
    <Toast />
    <Sidebar v-model:visible="showSlideOut" position="right" style="width:700px">
      <ProductDetails :rec="selectedRec" @cancel="showSlideOut = false" @changed="getData()" :isNew="isNewRec"> </ProductDetails>
    </Sidebar>
    <h3> Manage Products </h3>
    <div class="p-d-flex p-flex-row p-mb-1" style="width:1000px">
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
      <Column field="id" header="PRODUCT ID" headerStyle="width:90px;"></Column>
      <Column field="productCode" header="CODE" headerStyle="width:90px"></Column>
      <Column field="productName" header="NAME" headerStyle="width:160px"></Column>
      <Column field="standardCost" header="COST" headerStyle="width:90px"></Column>
      <Column field="listPrice" header="LIST PRICE" headerStyle="width:90px"></Column>
      <Column field="reorderLevel" header="REORDER" headerStyle="width:75px"></Column>
      <Column field="category" header="CATEGORY" headerStyle="width:110px"></Column>
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
import ProductDetails from '@/views/ProductDetails.vue';
import ProductApi from '@/api/product-api'; // eslint-disable-line import/no-cycle
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';

export default defineComponent({
  setup(): unknown {
    const isLoading = ref(false);
    const showSlideOut = ref(false);
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

    const getData = async (page:number, requestedPageSize: number, id = '') => {
      // isLoading.value = true;
      try {
        const resp = await ProductApi.getProducts(page, requestedPageSize, id);
        list.value = resp.data.list;
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
        message: `Do you want to remove ${rec.productName} from product catalog ?`,
        header: 'Remove',
        icon: 'pi pi-question-circle',
        acceptIcon: 'pi pi-check',
        accept: async () => {
          try {
            const resp = await ProductApi.deleteProduct(rec.id as string);
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
      getData(0, pageSize.value);
    });

    return {
      list,
      isLoading,
      showSlideOut,
      pageSize,
      totalPages,
      totalRecs,
      selectedRec,
      isNewRec,
      isCustomer,
      onAddClick,
      onDeleteClick,
      onEditClick,
      onPageChange,
      getData,
    };
  },
  components: {
    ProductDetails,
  },
});
</script>
