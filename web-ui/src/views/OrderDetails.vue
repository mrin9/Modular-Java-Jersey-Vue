<template>

  <div v-loading="loading" >
    <span class="sw-section-heading">
      ORDER #  <span class="sw-primary-color"> {{orderData.id}}</span>
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

    <el-table :data="orderData.orderLine" style="width:650px;" empty-text="No Data">
      <el-table-column prop="productId"   label="#"          width="50"/>
      <el-table-column prop="productName" label="NAME" />
      <el-table-column prop="quantity"    label="QTY"        width="60"/>
      <el-table-column prop="unitPrice"   label="PRICE"      width="60"/>
      <el-table-column prop="discount"    label="DISCOUNT"   width="80"/>
      <el-table-column prop="orderItemStatus" label="STATUS" width="100"/>
      <el-table-column label="" width="60">
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
    getData(){
      let me = this;
      Rest.getOrders(1,1,this.$data.orderData.id).then(function(resp){
        if (resp.data.msgType==="SUCCESS" && resp.data.list.length===1){
          me.$data.orderData = resp.data.list[0];
        }
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
        me.$data.loading=false;
      });

    },
    
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
        }
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
      });

    },

    onDelete(row){
      let me = this;
      this.$confirm('Are you sure delete?', 'Confirm', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        return Rest.deleteOrderLine( me.$data.orderData.id, row.productId);
      }).then((resp) => {
        if (resp.data.msgType==="SUCCESS"){
          me.getData();
          me.$message({message: 'Successfully deleted', type:'success'});
        }
        else{
          me.$message({message: 'Unable to delete', type:'error', showClose:true, duration:6000});
        }
      })
      .catch((resp) => {
        me.$message({type:'info',message: 'Delete canceled'});          
      });
    },



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
.sw-primary-color{
  color:$sw-primary-color;
}


</style>

