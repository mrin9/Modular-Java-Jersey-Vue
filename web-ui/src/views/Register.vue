<template>
  <div class="m-register-form" >
    <div class="m-form-heading">
      <Logo style="height:42px;width:42px;margin:5px; display:inline-block"></Logo>
      <div>User Registration</div>
    </div>
    <div style="padding:10px">
      <UserDetails :rec="{userId:'NEW', role:'CUSTOMER'}" :isCustomer="true" :isRegister="true"> </UserDetails>
    </div>
  </div>
</template>
<script lang='ts'>
import { defineComponent } from 'vue';
import Logo from '@/components/Logo.vue';
import UserDetails from '@/views/UserDetails.vue';

export default defineComponent({
  components: {
    UserDetails,
    Logo,
  },
});
</script>
<style lang="scss" scoped>
$sw-form-width:600px;
.m-register-form {
  top : 24px;
  left: calc(50% - #{$sw-form-width/2});
  position: absolute;
  border: 1px solid #333;
  border-top-width: 3px;
  width:$sw-form-width;
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  .m-form-heading {
    //background-color: #333;
    height:60px;
    color:#333;
    display:flex;
    justify-content: flex-start;
    align-items: center;
    font-size: 24px;
  }
}
</style>
