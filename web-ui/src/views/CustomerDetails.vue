<template>
  <div class="m-font-regular">
    <Toast />
    <h4>CUSTOMER # <span style="color:var(--primary-color)"> {{recData.customerId ? recData.customerId : 'NEW'}} </span></h4>
    <span class="m-gray-text">Provide some fake details, the data will be refreshed at certain interval</span>

    <!-- Name and Contact -->
    <div class="m-font-bold p-mt-4 p-mb-2">NAME AND CONTACT</div>
    <div>
      <div class="">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >Name </label>
        <InputText type="text" v-model="recData.firstName" class="p-inputtext-sm p-mr-1"/>
        <InputText type="text" v-model="recData.lastName" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> Email </label>
        <InputText type="text" v-model="recData.email" class="p-inputtext-sm" style="width:325px"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> Phone </label>
        <InputText type="text" v-model="recData.phone" class="p-inputtext-sm" style="width:325px"/>
      </div>
    </div>

    <!-- Address -->
    <div class="m-font-bold p-mt-4 p-mb-2">ADDRESS</div>
    <div>
      <div class="">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >Address </label>
        <InputText type="text" v-model="recData.address1" class="p-inputtext-sm p-mr-1"/>
        <InputText type="text" v-model="recData.address2" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> City </label>
        <InputText type="text" v-model="recData.city" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> State </label>
        <InputText type="text" v-model="recData.state" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> Postal Code </label>
        <InputText type="text" v-model="recData.postalCode" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> Country </label>
        <InputText type="text" v-model="recData.country" class="p-inputtext-sm"/>
      </div>
    </div>
    <div class="p-mt-2 p-d-flex p-flex-row p-jc-end" style="width:100%">
      <template v-if= "changesApplied">
        <Button label="CLOSE" @click="$emit('cancel')" class="p-button-sm"></Button>
      </template>
      <template v-else>
        <Button label="CANCEL" @click="$emit('cancel')" class="p-button-sm p-button-outlined p-mr-1"></Button>
        <Button icon="pi pi-check" iconPos="left" label="APPLY CHANGES" @click="onApplyChanges()" class="p-button-sm"></Button>
      </template>
    </div>
  </div>
</template>

<script lang='ts'>
import { defineComponent, ref } from 'vue';
import CustomerApi from '@/api/customer-api';
import { useToast } from 'primevue/usetoast';

export default defineComponent({
  props: {
    rec: { type: Object, required: true },
    isNew: { type: Boolean, default: true, required: false },
  },

  setup(props, { emit }): unknown {
    const toast = useToast();
    const showMessage = ref(false);
    const changesApplied = ref(false);
    const recData = ref(JSON.parse(JSON.stringify(props.rec))); // do not create direct refs to props to avoid making changes to props, instead use a cloned value of prop

    const onApplyChanges = async () => {
      const rawCustomerObj = JSON.parse(JSON.stringify(recData.value));
      rawCustomerObj.id = rawCustomerObj.customerId;
      delete rawCustomerObj.customerId;
      delete rawCustomerObj.userId;
      delete rawCustomerObj.password;
      delete rawCustomerObj.role;
      delete rawCustomerObj.fullName;
      let resp;
      if (rawCustomerObj.id) {
        resp = await CustomerApi.updateCustomer(rawCustomerObj);
      } else {
        resp = await CustomerApi.addCustomer(rawCustomerObj);
      }
      if (resp.data.msgType === 'SUCCESS') {
        toast.add({ severity: 'success', summary: rawCustomerObj.id ? 'Customer Updated' : 'Customer Added', detail: `${rawCustomerObj.firstName} ${rawCustomerObj.lastName}`, life: 3000 });
        if (!rawCustomerObj.id) {
          recData.value.customerId = 'CREATED';
        }
        changesApplied.value = true;
        emit('changed');
      } else {
        toast.add({ severity: 'error', summary: 'Error updating customer', detail: resp.data.msg });
      }
    };

    const onCancel = () => {
      emit('cancel');
    };

    return {
      showMessage,
      recData,
      onApplyChanges,
      onCancel,
    };
  },
});
</script>
