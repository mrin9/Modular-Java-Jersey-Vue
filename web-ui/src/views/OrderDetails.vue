<template>
  <div class="m-font-regular">
    <Toast />
    <h4>ORDER: <span style="color: var(--primary-color)"> {{ recData.id }}</span></h4>
    <span class="m-gray-text">Provide some fake details, the data will be refreshed at certain interval</span>

    <!-- Order Info -->
    <div class="p-d-flex p-mt-4">
      <div>
        <div class="p-d-flex p-ai-center">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">Date </label>
          <InputText type="text" v-model="recData.strOrderDate" class="p-inputtext-sm p-mr-1"/>
        </div>
        <div class="p-d-flex p-ai-center p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">Status</label>
          <Dropdown class="p-inputtext-sm" v-model = "recData.orderStatus" :options = "['Complete', 'On Hold', 'Shipped', 'New']"/>
        </div>
        <div class="p-d-flex p-ai-center p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >Payment Type</label>
          <Dropdown class="p-inputtext-sm" v-model = "recData.paymentType" :options = "['Card', 'Check', 'Cash']"/>
        </div>
        <div class="p-d-flex p-ai-center p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">Ship To</label>
          <InputText type="text" v-model="recData.shipName" class="p-inputtext-sm p-mr-1"/>
        </div>
        <div class="p-d-flex p-ai-center p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">Shipping Fee</label>
          <InputText type="text" v-model="recData.shippingFee" class="p-inputtext-sm p-mr-1"/>
        </div>
      </div>
      <div style="flex:1"></div>
      <div>
        <div class="p-d-flex p-ai-center">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">Address</label>
          <InputText type="text" v-model="recData.shipAddress1" class="p-inputtext-sm p-mr-1"/>
          <InputText type="text" v-model="recData.shipAddress2" class="p-inputtext-sm p-mr-1"/>
        </div>
        <div class="p-d-flex p-ai-center p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">City</label>
          <InputText type="text" v-model="recData.shipCity" class="p-inputtext-sm p-mr-1"/>
        </div>
        <div class="p-d-flex p-ai-center p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">State</label>
          <InputText type="text" v-model="recData.shipState" class="p-inputtext-sm p-mr-1"/>
        </div>
        <div class="p-d-flex p-ai-center p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">Postal</label>
          <InputText type="text" v-model="recData.shipPostalCode" class="p-inputtext-sm p-mr-1"/>
        </div>
        <div class="p-d-flex p-ai-center p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">Country</label>
          <InputText type="text" v-model="recData.shipCountry" class="p-inputtext-sm p-mr-1"/>
        </div>
      </div>
    </div>

    <!-- Order Lines -->
    <ConfirmPopup></ConfirmPopup>
    <div class="p-d-flex p-mt-4 m-font-bold" style = "margin: 2px">
      <span> ORDER ITEMS</span>
      <div style="flex:1"></div>
      <span> ORDER TOTAL : &nbsp;<span style="color: var(--primary-color)"> {{ orderTotal }} </span> </span>
    </div>
    <DataTable :value = "recData.orderLine" class="p-datatable-sm p-datatable-hoverable-rows m-border p-mb-4">
      <Column field="productId" header="#" headerStyle="width:50px;"></Column>
      <Column field="productName" header="NAME"></Column>
      <Column field="quantity" header="QTY" headerStyle="width:50px"></Column>
      <Column field="unitPrice" header="PRICE" headerStyle="width:60px"></Column>
      <Column field="discount" header="DISCOUNT" headerStyle="width:75px"></Column>
      <Column field="orderItemStatus" header="STATUS" headerStyle="width:75px"></Column>
      <Column headerStyle="width:35px" bodyStyle="padding:3px">
        <template #body="slotProps">
          <Button v-if = "!changesApplied"
            @click="onDeleteOrderItemClick($event, slotProps.data)"
            icon="pi pi-trash"
            class="p-button-sm p-button-rounded p-button-danger p-button-text"
          />
        </template>
      </Column>
    </DataTable>

    <div class="p-mt-2 p-d-flex p-flex-row p-jc-end" style="width:100%">
      <template v-if= "changesApplied">
        <Button label="CLOSE" @click="$emit('cancel')" class="p-button-sm"></Button>
      </template>
      <template v-else>
        <Button label="CANCEL" @click="onCancel()" class="p-button-sm p-button-outlined p-mr-1"></Button>
        <Button icon="pi pi-check" iconPos="left" label="APPLY CHANGES" @click="onApplyChanges($event)" class="p-button-sm"></Button>
      </template>
    </div>
  </div>
</template>

<script lang='ts'>
import { defineComponent, ref, reactive, computed } from 'vue';
import OrderApi from '@/api/order-api';
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';

export default defineComponent({
  emits: ['cancel', 'changed'],
  props: {
    rec: { type: Object, required: true },
    isNew: { type: Boolean, default: true, required: false },
  },

  setup(props, { emit }): unknown {
    const showMessage = ref(false);
    const changesApplied = ref(false);
    const recData = reactive(JSON.parse(JSON.stringify(props.rec))); // do not create direct refs to props to avoid making changes to props, instead use a cloned value of prop
    const confirm = useConfirm();
    const toast = useToast();
    const productIdsToDelete:string[] = [];
    const orderTotal = computed(() => {
      let totalVal = 0;
      recData.orderLine.forEach((v:Record<string, unknown>) => {
        totalVal += (((v.quantity as number) * (v.unitPrice as number)) - (v.discount as number));
      });
      return new Intl.NumberFormat('en-US', { useGrouping: true, currency: 'USD' }).format(totalVal);
    });

    const deleteOrderItemConfirmDlg = (ev:Event) => {
      confirm.require({
        target: ev.currentTarget as EventTarget,
        message: `Are you sure to remove ${productIdsToDelete.length} items from this order?`,
        header: 'Remove Order items',
        icon: 'pi pi-question-circle',
        acceptIcon: 'pi pi-check',
        accept: async () => {
          const commSeparatedProdIds:string = productIdsToDelete.join(',');
          const resp = await OrderApi.deleteOrderLines(recData.id, commSeparatedProdIds);
          if (resp.data.msgType === 'SUCCESS') {
            toast.add({ severity: 'success', summary: 'Order Itmes removed', detail: resp.data.msg, life: 3000 });
            changesApplied.value = true;
            emit('changed');
          } else {
            toast.add({ severity: 'error', summary: 'Unable to remove', detail: resp.data.msg, life: 3000 });
          }
        },
        reject: async () => {
          recData.orderLine = props.rec.orderLine;
          productIdsToDelete.length = 0;
        },
      });
    };

    const addProductIdToDeletedList = (orderItemRec:Record<string, unknown>) => {
      productIdsToDelete.push(orderItemRec.productId as string);
      recData.orderLine = recData.orderLine.filter((v:Record<string, unknown>) => v.productId !== orderItemRec.productId);
    };

    // Do not make API call, just add it to the productIds to delete list
    const onDeleteOrderItemClick = (ev:Event, orderItemRec:Record<string, unknown>) => {
      addProductIdToDeletedList(orderItemRec);
      recData.orderLine = recData.orderLine.filter((v:Record<string, unknown>) => v.productId !== orderItemRec.productId);
    };

    const onApplyChanges = async (ev:Event) => {
      if (productIdsToDelete.length > 0) {
        deleteOrderItemConfirmDlg(ev);
      }
      showMessage.value = true;
    };

    const onCancel = () => {
      emit('cancel');
    };

    return {
      showMessage,
      recData,
      orderTotal,
      onDeleteOrderItemClick,
      changesApplied,
      onApplyChanges,
      onCancel,
    };
  },
});
</script>
