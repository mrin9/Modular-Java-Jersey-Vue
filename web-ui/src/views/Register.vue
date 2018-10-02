<template>

  <div class="sw-register-form" v-loading="loading" >
    <div class="sw-form-heading">
      <mr-logo style="height:42px;width:42px;margin:5px; display:inline-block"></mr-logo>
      <div>User Registration</div>
    </div>
    <div style="padding:10px">
      <user-details :rec="{}"> </user-details>
    </div>
  </div>
</template>

<script>
import Rest from '@/rest/Rest';
import router from '@/router';
import MrLogo from '@/components/logo/Logo';
import UserDetails from '@/views/UserDetails'

export default {

  data:function(){
    return {
      loading : false,
      isEmployee:false,
      role:this.$store.state.role,
      userObj:{
        "userId": "test",
        "password": "test",
        "role": "CUSTOMER",
        "lastName": "First",
        "firstName": "Last",
        "email": "abc@example,com",
        "company": "",
        "phone": "1234",
        "address1": "add1",
        "address2": "add2",
        "city": "Sunnyvale",
        "state": "CA",
        "postalCode": "94086",
        "country": "USA",
        "department": "",
        "managerId":0
      }
    }
  },

  methods:{
    
    onRegister(){
      let me = this;
      Rest.registerUser(me.$data.userObj).then(function(resp){
        me.$data.loading=false;
        if (resp.data.msgType==="ERROR"){
          me.$message.error('Unable to register the user with id: '+ me.$data.userObj.userId)
        }
        else{
          router.push("/login");
          me.$message.success('User ID created :' + me.$data.userObj.userId);
        }
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
        me.$data.loading=false;
      });




    },

    onCancel(){
      router.push("/login");
    }


  },

  mounted(){
    if (this.$store.state.role==="ADMIN"){

    }
    else if (this.$store.state.role==="SUPPORT"){
      this.$data.userObj.role="SUPPORT";
    }
    else{
      this.$data.userObj.role="CUSTOMER";
    }
    
  },
  components: {
    MrLogo,
    UserDetails
  }

}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";

$sw-form-width:600px;

.sw-register-form{
  top : 24px;
  left: calc(50% - #{$sw-form-width/2});
  position: absolute;
  border: 1px solid #333;
  border-top-width: 3px;
  width:$sw-form-width;
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  .sw-form-heading{
    //background-color: #333;
    height:60px;
    color:#333;
    display:flex;
    justify-content: start;
    align-items: center;
    font-size: 24px;
  }
}



</style>

