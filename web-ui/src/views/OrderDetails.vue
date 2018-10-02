<template>

  <div v-loading="loading" >
    <span class="sw-section-heading">
      ORDER DETAILS
    </span> <br/>
    <div class="sw-gray-text">Update Order details (the data gets refreshed after certain interval)</div>
    <br/><br/>
    <div class="sw-row">
      <label class="sw-label">Order ID </label>
      <label v-if="rec.id" class="sw-label">{{orderData.id}} </label>
    </div>
    <table style="width:100%">
      <tr>
        <td>
          <div class="sw-row">
            <label class="sw-label">Status</label><input type="text" class="sw-medium" v-model="orderData.orderStatus">
          </div>
          <div class="sw-row">
            <label class="sw-label">Payment</label><input type="text" class="sw-medium" v-model="orderData.paymentType">
          </div>
          <div class="sw-row">
            <label class="sw-label">Payment</label><input type="text" class="sw-medium" v-model="orderData.paymentType">
          </div>
        </td>
        <td>
          <div class="sw-row">
            <label class="sw-label">Status</label><input type="text" class="sw-medium" v-model="orderData.orderStatus">
          </div>
          <div class="sw-row">
            <label class="sw-label">Payment</label><input type="text" class="sw-medium" v-model="orderData.paymentType">
          </div>
          <div class="sw-row">
            <label class="sw-label">Payment</label><input type="text" class="sw-medium" v-model="orderData.paymentType">
          </div>
        </td>
      </tr>
    </table>  

    <br/><br/>
    <span class="sw-section-heading">ORDER ITEMS</span> 

    <el-table :data="orderData.orderLine" style="width:600px;" empty-text="No Data">
      <el-table-column prop="productId"   label="#"          width="50"/>
      <el-table-column prop="productName" label="NAME" />
      <el-table-column prop="quantity"    label="QTY"        width="70"/>
      <el-table-column prop="unitPrice"   label="PRICE"      width="90"/>
      <el-table-column prop="discount"    label="DISCOUNT"   width="80"/>
      <el-table-column prop="orderItemStatus" label="STATUS" width="120"/>
      <el-table-column label="" width="70">
        <template slot-scope="scope">
          <i class="el-icon-edit"   style="font-size:16px; vertical-align: middle; cursor:pointer; color:cornflowerblue" @click="onEdit(scope.row)"></i>
          <i class="el-icon-delete" style="font-size:16px; vertical-align: middle; cursor:pointer; color:orangered;margin-left:8px" @click="onDelete(scope.row)"></i>
        </template>
      </el-table-column>
    </el-table>


    <br/>
    <div class="sw-toolbar">
      <el-button type="primary" size="medium" @click="onApplyChanges" class="sw-toolbar-item">APPLY CHANGES</el-button>
    </div>
  </div>
</template>

<script>
import Rest from '@/rest/Rest';
import router from '@/router';
import MrLogo from '@/components/logo/Logo';


export default {

  props: {
    rec:{type: Object, required:true},
  },

  data:function(){
    return {
      loading:false,
      orderData : this.rec,  // Assign the prop value to data to make it reactive
      role:this.$store.state.role
    }
  },

  methods:{
    
    onApplyChanges(){
      let me = this;
      let methodName = "updateProduct"
      if ( me.$data.productData.id) {
        methodName = "updateProduct";
      }
      else{
        methodName = "addProduct";
      }
     
      Rest[methodName](me.$data.productData).then(function(resp){
        me.$data.loading=false;
        if (resp.data.msgType==="ERROR"){
          me.$message.error('Unable to Update/Add Product - id: '+ me.$data.productData.id)
        }
        else{
          me.$message.success('Successfully Updated');
          me.$emit("changed");
        }
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
      });

    },

    onCancel(){
      
    }


  },

  mounted(){
    console.log("id is :" + this.rec.id)
    if (this.$store.state.role==="ADMIN"){
    }
    else if (this.$store.state.role==="SUPPORT"){
      this.$data.userObj.role="SUPPORT";
    }
    else{
      this.$data.userObj.role="CUSTOMER";
    }
    
  }

}
</script>
<style lang="scss" scoped>
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
.el-input,
input{
  width:200px;
  margin-right:5px;
  margin-top:2px;
}

.sw-gray-text{
  display: inline-block;
  font-size: 12px;
  line-height: 12px;
  vertical-align: middle;
  color: #777;
}
.sw-row{
  display:flex;
  align-items: center;
}


</style>

