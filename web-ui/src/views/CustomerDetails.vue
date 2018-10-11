<template>
    <div v-loading="loading" >
      <span class="sw-section-heading">
        CUSTOMER #  <span class="sw-primary-color"> {{customerData.customerId ? customerData.customerId : "NEW"}}</span>
      </span> <br/>
      <div class="sw-gray-text">Update Customer details (the data gets refreshed after certain interval)</div>
      <br/><br/>

      <div class="sw-row">
        <label class="sw-label">Name</label>
        <input type="text" placeholder="First Name" class="sw-medium" v-model="customerData.firstName">
        <input type="text" placeholder="Last Name" class="sw-medium" v-model="customerData.lastName">
      </div>
      <div class="sw-row">
        <label class="sw-label">Email</label>
        <input type="text" class="sw-medium" style="width:245px" v-model="customerData.email">
      </div>
      <div class="sw-row">
        <label class="sw-label">Phone</label><input type="text" class="sw-medium" v-model="customerData.phone">
      </div>
    <br/>
      <span class="sw-section-heading">ADDRESS</span>
      <div class="sw-row">
        <label class="sw-label">Address</label>
        <input type="text" placeholder="Address Line 1" class="sw-medium" v-model="customerData.address1">
        <input type="text" placeholder="Address Line 2" class="sw-medium" v-model="customerData.address2">
      </div>
      <div class="sw-row">
      </div>
      <div class="sw-row">
        <label class="sw-label">City</label><input type="text" class="sw-medium" v-model="customerData.city">
      </div>
      <div class="sw-row">
        <label class="sw-label">State</label><input type="text" class="sw-medium" v-model="customerData.state">
      </div>
      <div class="sw-row">
        <label class="sw-label">Postal Code</label><input type="text" class="sw-medium" v-model="customerData.postalCode">
      </div>
      <div class="sw-row">
        <label class="sw-label">Country</label><input type="text" class="sw-medium" v-model="customerData.country">
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
      customerData : this.rec,  // Assign the prop value to data to make it reactive
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

