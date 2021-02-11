<template>
  <div class="m-font-regular">
    <BlockUI :blocked="isLoading" :fullScreen="true"></BlockUI>
    <div v-if="$props.isRegister">
      <h4>USER REGISTRATION</h4>
      <span class="m-gray-text">Provide some fake details, the data will be refreshed at certain interval</span>
    </div>
    <div v-else>
      <span class="m-font-bold">USER ID: </span> <span style="color: var(--primary-color)"> {{ $props.rec.userId }}</span>
      &nbsp;
      <span class="m-font-bold">ROLE: </span> <span style="color: var(--primary-color)"> {{ $props.rec.role }} </span>
    </div>
    <div class="p-mt-4"></div>
    <transition name="p-message">
      <Message v-if="showMessage" severity="info" @close="showMessage = false"> {{ userMessage }}</Message>
    </transition>
    <div>
      <!-- ID & Password -->
      <div v-if="$props.isRegister">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >User name </label>
        <InputText type="text" v-model="recData.userId" class="p-inputtext-sm"/>
      </div>
      <div class="p-mt-1">
        <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> Password </label>
        <Password v-model="recData.password" toggleMask class="p-inputtext-sm"></Password>
      </div>
    </div>

    <!-- Name and Contact -->
    <div class="p-d-inline-block m-font-bold p-mt-4 p-mb-2">NAME AND CONTACT</div>
    <span style="color: var(--fg-gray)"> (Optional)</span>
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
    <div class="p-d-inline-block m-font-bold p-mt-4 p-mb-2">ADDRESS</div>
    <span style="color: var(--fg-gray)"> (Optional)</span>
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

    <!-- Employee Info -->
    <template v-if="recData.role !== 'CUSTOMER' ">
      <div class="m-font-bold p-mt-4 p-mb-2">EMPLOYEE INFO</div>
      <div>
        <div>
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1" >Department </label>
          <InputText type="text" v-model="recData.department" class="p-inputtext-sm"/>
        </div>
        <div class="p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> Manager ID </label>
          <InputText type="text" v-model="recData.managerId" class="p-inputtext-sm"/>
        </div>
        <div class="p-mt-1">
          <label class="p-d-inline-block m-label-size-2 p-text-right p-mr-1"> Company </label>
          <InputText type="text" v-model="recData.company" class="p-inputtext-sm"/>
        </div>
      </div>
    </template>
    <div v-if="$props.isRegister" class="p-mt-2 p-d-flex p-flex-row p-jc-end" style="width:100%">
      <Button label="LOGIN" @click="$router.push('/login')" class="p-button-sm p-button-outlined p-mr-1"></Button>
      <Button icon="pi pi-check" iconPos="left" label="REGISTER" @click="onRegister()" class="p-button-sm"></Button>
    </div>
    <div v-else class="p-mt-2 p-d-flex p-flex-row p-jc-end" style="width:100%">
      <Button label="CANCEL" @click="$emit('cancel')" class="p-button-sm p-button-outlined p-mr-1"></Button>
      <Button icon="pi pi-check" iconPos="left" label="APPLY CHANGES" @click="onApplyChanges()" class="p-button-sm"></Button>
    </div>
  </div>
</template>

<script lang='ts'>
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
import UsersApi from '@/api/users-api';

export default defineComponent({
  props: {
    rec: { type: Object, required: true },
    isCustomer: { type: Boolean, default: true, required: false },
    isRegister: { type: Boolean, default: false, required: false },
  },

  setup(props): unknown {
    const showMessage = ref(false);
    const recData = ref(props.rec);
    const userMessage = ref('');
    const router = useRouter();

    const onApplyChanges = () => {
      userMessage.value = 'Updating User Details is disabled';
      showMessage.value = true;
    };
    const onRegister = async () => {
      const rawUserObj = JSON.parse(JSON.stringify(recData.value));
      const resp = await UsersApi.registerUser(rawUserObj);
      if (resp.data.msgType === 'SUCCESS') {
        router.push('/login');
      } else {
        userMessage.value = resp.data.msg; // 'Error during registration';
        showMessage.value = true;
      }
    };

    return {
      router,
      showMessage,
      userMessage,
      recData,
      onApplyChanges,
      onRegister,
    };
  },
});
/*

import Rest from '@/rest/Rest';
import router from '@/router';
export default {
  props: {
    rec:{type: Object, required:true},
    isCustomer:{type: Boolean, default:true, required:false},
    isNew:{type: Boolean, default:true, required:false},
},
  data:function(){
    return {
      loading : false,
      isEmployee:false,
      userData : this.rec,  // Assign the prop value to data to make it reactive
    }
  },
  methods:{
    onRegister(){
      let me = this;
      Rest.registerUser(me.$data.userData).then(function(resp){
        me.$data.loading=false;
        if (resp.data.msgType==="ERROR"){
          me.$message.error('Unable to register the user with id: '+ me.$data.userData.userId)
        }
        else{
          router.push("/login");
          me.$message.success('User ID created :' + me.$data.userData.userId);
        }
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
        me.$data.loading=false;
      });
    },
    onApplyChanges(){

    },
    onCancel(){
      router.push("/login");
    }
  },
}
*/
</script>
