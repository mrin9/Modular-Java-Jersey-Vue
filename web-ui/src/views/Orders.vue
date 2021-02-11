<template>
  <div>
    <ConfirmDialog position="top"></ConfirmDialog>
    <Toast />
    <Sidebar v-model:visible="showSlideOut" position="right" style="width:800px">
      <OrderDetails :rec="selectedRec" @cancel="showSlideOut = false" @changed="getData()" :isNew="isNewRec"> </OrderDetails>
    </Sidebar>
    <h3> Manage Orders </h3>
    <DataTable
      :value = "list"
      :paginator = "true"
      :lazy = "true"
      :rows = "pageSize"
      :totalRecords = "totalRecs"
      :scrollable = "true"
      scrollHeight= "400px"
      :loading = "isLoading"
      @page="onPageChange($event)"
      class="p-datatable-sm p-datatable-hoverable-rows m-border p-mb-4" style="width:1000px">
      <Column field="id" header="ORDER ID" headerStyle="width:90px;" bodyStyle="width:90px"></Column>
      <Column field="strOrderDate" header="DATE" headerStyle="width:110px;" bodyStyle="width:110px"></Column>
      <Column field="shipName" header="SHIP TO"></Column>
      <Column field="strOrderTotal" header="TOTAL" headerStyle="width:90px;" bodyStyle="width:90px"></Column>
      <Column field="orderStatus" header="STATUS" headerStyle="width:90px;" bodyStyle="width:90px">
        <template #body="slotProps">
          <span :class="`m-tag m-tag-${slotProps.data.orderStatus.replace(/\s/g, '-').toLowerCase()}`">
            {{slotProps.data.orderStatus}}
          </span>
        </template>
      </Column>
      <Column field="paymentType" header="PAYMENT" headerStyle="width:90px;" bodyStyle="width:90px"></Column>
      <Column header="ACTION" headerStyle="width:90px;" bodyStyle="width:90px; padding:3px">
        <template #body="slotProps">
          <template v-if="$store.state.role !== 'CUSTOMER'" >
            <Button icon="pi pi-pencil" @click="onEditClick(slotProps.data)" class="p-button-sm p-button-rounded p-button-secondary  p-button-text" />
            <Button icon="pi pi-trash"  @click="onDeleteClick(slotProps.data)" class="p-button-sm p-button-rounded p-button-danger p-button-text" />
          </template>
          <template v-if="$store.state.role == 'CUSTOMER'" >
            <Button icon="pi pi-eye"  @click="onViewClick(slotProps.data)" class="p-button-sm p-button-rounded p-button-secondary p-button-text" />
          </template>
        </template>
    </Column>
    </DataTable>
  </div>
</template>

<script lang='ts'>
import { ref, onMounted, defineComponent } from 'vue';
import OrderDetails from '@/views/OrderDetails.vue';
import OrderApi from '@/api/order-api'; // eslint-disable-line import/no-cycle
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';

export default defineComponent({
  setup(): unknown {
    const isLoading = ref(false);
    const showSlideOut = ref(false);
    const pageSize = ref(20);
    const totalPages = ref(0);
    const totalRecs = ref(0);
    const selectedRec = ref({});
    const isNewRec = ref(false);
    const isCustomer = ref(false);
    const list = ref([]);
    const confirm = useConfirm();
    const toast = useToast();

    let currentPage = 1;

    const getData = async (page:number, requestedPageSize: number) => {
      // isLoading.value = true;
      try {
        const resp = await OrderApi.getOrders(page, requestedPageSize);
        list.value = resp.data.list.map((v:Record<string, unknown>) => {
          const dt = new Date(v.orderDate as string);
          const strOrderDate = new Intl.DateTimeFormat('en-US', { year: 'numeric', month: 'short', day: 'numeric' }).format(dt);
          const strOrderTotal = new Intl.NumberFormat('en-US', { useGrouping: true, currency: 'USD' }).format(v.orderTotal as number);
          return {
            ...v,
            strOrderDate,
            strOrderTotal,
          };
        });
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
        message: `Do you want to delete: ${rec.id} ?`,
        header: 'Delete Confirmation',
        icon: 'pi pi-question-circle',
        acceptIcon: 'pi pi-check',
        accept: async () => {
          try {
            const resp = await OrderApi.deleteOrder(rec.id as string);
            if (resp.data.msgType === 'SUCCESS') {
              getData(currentPage, pageSize.value);
              toast.add({ severity: 'success', summary: 'Successfully Deleted', life: 3000 });
            } else {
              toast.add({ severity: 'error', summary: 'Error', detail: resp.data.msg, life: 3000 });
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

    const onDeleteClick = (rec:Record<string, unknown>) => {
      confirmDialog(rec);
    };
    const onEditClick = async (rec:Record<string, unknown>) => {
      selectedRec.value = rec;
      showSlideOut.value = true;
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
      onDeleteClick,
      onEditClick,
      onPageChange,
      getData,
    };
  },
  components: {
    OrderDetails,
  },
});
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
.m-tag {
  &.m-tag-on-hold,
  &.m-tag-new {
    text-transform: uppercase;
    padding:4px 6px;
    font-size: 0.7rem;
    font-weight: 700;
    border-radius: var(--border-radius);
  }
  &.m-tag-on-hold{ background-color: orangered; color: #fff; }
  &.m-tag-new{ background-color: cornflowerblue; color: #fff; }
}
</style>
