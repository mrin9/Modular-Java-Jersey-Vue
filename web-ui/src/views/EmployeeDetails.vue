<template>
    <div v-loading="loading" >
      <span class="sw-section-heading">
        EMPLOYEE #  <span class="sw-primary-color"> {{employeeData.employeeId ? employeeData.employeeId : "NEW"}}</span>
      </span> <br/>
      <div class="sw-gray-text">Update Employee details (the data gets refreshed after certain interval)</div>
      <br/><br/>

      <div class="sw-row">
        <label class="sw-label">Name</label>
        <input type="text" placeholder="First Name" class="sw-medium" v-model="employeeData.firstName">
        <input type="text" placeholder="Last Name" class="sw-medium" v-model="employeeData.lastName">
      </div>
      <div class="sw-row">
        <label class="sw-label">Email</label>
        <input type="text" class="sw-medium" style="width:245px" v-model="employeeData.email">
      </div>
      <div class="sw-row">
        <label class="sw-label">Phone</label><input type="text" class="sw-medium" v-model="employeeData.phone">
      </div>
      <br/>

      <span class="sw-section-heading">JOB DETAILS</span>
      <div class="sw-row">
        <label class="sw-label">Job Title</label> 
        <input type="text" class="sw-medium" style="width:245px" v-model="employeeData.jobTitle">
      </div>
      <div class="sw-row">
        <label class="sw-label">Department</label>
        <input type="text" class="sw-medium" style="width:245px" v-model="employeeData.department">
      </div>
      <div class="sw-row">
        <label class="sw-label">Manager ID</label>
        <input type="text" class="sw-medium" v-model="employeeData.managerId">
      </div>
      <br/>

      <span class="sw-section-heading">ADDRESS</span>
      <div class="sw-row">
        <label class="sw-label">Address</label>
        <input type="text" placeholder="Address Line 1" class="sw-medium" v-model="employeeData.address1">
        <input type="text" placeholder="Address Line 2" class="sw-medium" v-model="employeeData.address2">
      </div>
      <div class="sw-row">
      </div>
      <div class="sw-row">
        <label class="sw-label">City</label><input type="text" class="sw-medium" v-model="employeeData.city">
      </div>
      <div class="sw-row">
        <label class="sw-label">State</label><input type="text" class="sw-medium" v-model="employeeData.state">
      </div>
      <div class="sw-row">
        <label class="sw-label">Postal Code</label><input type="text" class="sw-medium" v-model="employeeData.postalCode">
      </div>
      <div class="sw-row">
        <label class="sw-label">Country</label><input type="text" class="sw-medium" v-model="employeeData.country">
      </div>
      <div class="sw-toolbar">
          <el-button type="primary" size="medium" @click="onApplyChanges" class="sw-toolbar-item">APPLY CHANGES</el-button>
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
      employeeData : this.rec,  // Assign the prop value to data to make it reactive
    }
  },

  methods:{
    
    onApplyChanges(){
      let me = this;
      let methodName = me.$data.productData.id ? "updateCustomer" :"addCustomer";
      Rest[methodName](me.$data.customerData).then(function(resp){
        me.$data.loading=false;
        if (resp.data.msgType==="ERROR"){
          me.$message.error('Unable to Update/Add Customer - id: '+ me.$data.customerData.id)
        }
        else{
          me.$message.success('Successfully Updated');
          me.$emit("changed");
        }
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
      });
    }

  },

  mounted(){
    
  },
  components: {
  }

}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";

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
.sw-primary-color{
  color:$sw-primary-color;
}

</style>

