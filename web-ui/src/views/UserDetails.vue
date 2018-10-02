<template>
    <div v-loading="loading" >
      <span class="sw-section-heading">
        USER DETAILS
      </span> 
      <span class="sw-gray-text">Provide some fake details, the data will be refreshed at certain interval</span>
      <br/>
      <div class="sw-row">
        <label class="sw-label">User name </label><input type="text" class="sw-medium" v-model="userData.userId">
      </div>
      <div class="sw-row">
        <label class="sw-label">Password </label><input type="text" class="sw-medium" v-model="userData.password">
      </div>
      <div v-if="role=='ADMIN'" class="sw-row">
        <label class="sw-label">Is Employee</label><el-switch v-model="isEmployee"/>
      </div>

      <br/><br/>
      <span class="sw-section-heading">NAME & CONTACT</span> 
      <div class="sw-row">
        <label class="sw-label">Name</label>
        <input type="text" placeholder="First Name" class="sw-medium" v-model="userData.firstName">
        <input type="text" placeholder="Last Name" class="sw-medium" v-model="userData.lastName">
      </div>
      <div class="sw-row">
        <label class="sw-label">Email</label><input type="text" class="sw-medium" v-model="userData.email">
      </div>
      <div class="sw-row">
        <label class="sw-label">Phone</label><input type="text" class="sw-medium" v-model="userData.phone">
      </div>
    
    <br/><br/>
      <span class="sw-section-heading">ADDRESS</span>
      <div class="sw-row">
        <label class="sw-label">Address</label>
        <input type="text" placeholder="Address Line 1" class="sw-medium" v-model="userData.address1">
        <input type="text" placeholder="Address Line 2" class="sw-medium" v-model="userData.address2">
      </div>
      <div class="sw-row">
      </div>
      <div class="sw-row">
        <label class="sw-label">City</label><input type="text" class="sw-medium" v-model="userData.city">
      </div>
      <div class="sw-row">
        <label class="sw-label">State</label><input type="text" class="sw-medium" v-model="userData.state">
      </div>
      <div class="sw-row">
        <label class="sw-label">Postal Code</label><input type="text" class="sw-medium" v-model="userData.postalCode">
      </div>
      <div class="sw-row">
        <label class="sw-label">Country</label><input type="text" class="sw-medium" v-model="userData.country">
      </div>
      <template v-if="role=='ADMIN'">
        <br/><br/>
        <span class="sw-section-heading">Employee Info</span>
        <div class="sw-row">
          <label class="sw-label">Department</label><input type="text" class="sw-medium" v-model="userObj.department">
        </div>
        <div class="sw-row">
          <label class="sw-label">Manager ID</label><input type="text" class="sw-medium" v-model="userObj.managerId">
        </div>
        <div class="sw-row">
          <label class="sw-label">Company</label><input type="text" class="sw-medium" v-model="userData.company">
        </div>
      </template>  
      <div class="sw-toolbar">
        <el-button type="primary" size="medium" @click="onRegister" class="sw-toolbar-item">REGISTER</el-button>
        <el-button size="medium" @click="onCancel" class="sw-toolbar-item">CANCEL</el-button>
      </div>
    </div>
</template>

<script>
import Rest from '@/rest/Rest';
import router from '@/router';

export default {
  props: {
    rec:{type: Object, required:true},
  },

  data:function(){
    return {
      loading : false,
      isEmployee:false,
      role:this.$store.state.role,
      userData : this.rec,  // Assign the prop value to data to make it reactive
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
  }

}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
.sw-register-form{
  margin: 16px auto;
  border: 1px solid #333;
  border-top-width: 3px;
  width:600px;
  display:flex;
  flex-direction:column;
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
.sw-medium{
  margin-top:2px;
  width:120px;
}
.sw-label{
  display:inline-block;
  width:100px;
  text-align:right;
  line-height: 26px;
  margin-right:5px;
}
input{
  width:200px;
  margin-right:5px;
}
el-switch{
  margin-right:5px;
}

.sw-gray-text{
  display: inline-block;
  font-size: 12px;
  line-height: 12px;
  vertical-align: middle;
  margin-left: 2px;
  color: #777;
}
.sw-row{
  display:flex;
  align-items: center;
}


</style>

