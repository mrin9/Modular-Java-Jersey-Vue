<template>
  <div class="m-font-regular">
    <Toast />
    <h4>PRODUCT # <span style="color:var(--primary-color)"> {{ recData.id ? recData.id : 'NEW'}} </span></h4>
    <span class="m-gray-text">Provide some fake details, the data will be refreshed at certain interval</span>

    <div>
      <div class= "p-mt-4">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >Category</label>
        <Dropdown class="p-inputtext-sm" v-model = "recData.category" :options = "['Camera', 'Laptop', 'Tablet', 'Phone']"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >Code & Name </label>
        <InputText type="text" v-model="recData.productCode" class="p-inputtext-sm p-mr-1" style="width:70px"/>
        <InputText type="text" v-model="recData.productName" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> Description </label>
        <InputText type="text" v-model="recData.description" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1">Discontinued?</label>
        <SelectButton
          v-model = "recData.discontinued"
          class = "p-inputtext-sm p-mr-1 p-d-inline-block"
          :options = "[{label:'Yes', code:1}, {label:'No', code:0}]"
          optionLabel = "label"
          optionValue = "code"
        />
      </div>
    </div>

    <!-- Address -->
    <div class="m-font-bold p-mt-4 p-mb-2">COST & STOCK LEVELS</div>
    <div>
      <div>
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >Standard Cost</label>
        <InputNumber id="currency-us" v-model="recData.standardCost" mode="currency" currency="USD" locale="en-US" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >List Price</label>
        <InputNumber id="currency-us" v-model="recData.listPrice" mode="currency" currency="USD" locale="en-US" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >Stock Levels</label>
        <div class="p-inputgroup p-d-inline-flex" style="width:140px">
          <span class="p-inputgroup-addon">Target</span>
          <InputNumber v-model="recData.targetLevel" mode="decimal" :useGrouping="false" class="p-inputtext-sm p-mr-1"/>
        </div>
        <div class="p-inputgroup p-d-inline-flex p-mr-1" style="width:140px">
          <span class="p-inputgroup-addon">Reorder</span>
          <InputNumber v-model="recData.reorderLevel" mode="decimal" :useGrouping="false" class="p-inputtext-sm"/>
        </div>
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
import ProductApi from '@/api/product-api';
import { useToast } from 'primevue/usetoast';

export default defineComponent({
  props: {
    rec: { type: Object, required: true },
  },

  setup(props, { emit }): unknown {
    const toast = useToast();
    const showMessage = ref(false);
    const changesApplied = ref(false);
    const recData = ref(JSON.parse(JSON.stringify(props.rec))); // do not create direct refs to props to avoid making changes to props, instead use a cloned value of prop

    const onApplyChanges = async () => {
      const rawProductObj = JSON.parse(JSON.stringify(recData.value));
      let resp;
      if (rawProductObj.id) {
        resp = await ProductApi.updateProduct(rawProductObj);
      } else {
        resp = await ProductApi.addProduct(rawProductObj);
      }
      if (resp.data.msgType === 'SUCCESS') {
        toast.add({ severity: 'success', summary: rawProductObj.id ? 'Product Updated' : 'Product Added', detail: `${rawProductObj.productName} (${rawProductObj.productCode})`, life: 3000 });
        if (!rawProductObj.id) {
          recData.value.id = 'CREATED';
        }
        changesApplied.value = true;
        emit('changed');
      } else {
        toast.add({ severity: 'error', summary: 'Error', detail: resp.data.msg });
      }
    };

    const onCancel = () => {
      emit('cancel');
    };

    return {
      showMessage,
      changesApplied,
      recData,
      onApplyChanges,
      onCancel,
    };
  },
});
</script>
