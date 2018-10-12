<template>

  <div v-loading="loading" >
    <span class="sw-section-heading">
      ORDER #  <span class="sw-primary-color"> {{orderData.id}}</span>
    </span> <br/>
    <div class="sw-gray-text">Update Order details (the data gets refreshed after certain interval)</div>
    <br/><br/>
    <table style="width:100%">
      <tr>
        <td>
          <div class="sw-row">
            <label class="sw-label">Date</label>
            <input type="text" class="sw-medium" v-model="orderData.strOrderDate">
          </div>
          <div class="sw-row">
            <label class="sw-label">Status</label>
            <el-select size="medium" style="width:120px" v-model="orderData.orderStatus">
              <el-option label="Complete" value="Complete"></el-option>
              <el-option label="On Hold" value="On Hold"></el-option>
              <el-option label="Shipped" value="Shipped"></el-option>
              <el-option label="New" value="New"></el-option>
            </el-select>  
          </div>
          <div class="sw-row">
            <label class="sw-label">Payment</label>
            <el-select size="medium" style="width:120px" v-model="orderData.paymentType">
              <el-option label="Cash" value="Cash"></el-option>
              <el-option label="Check" value="Check"></el-option>
              <el-option label="Card" value="Card"></el-option>
            </el-select>  
          </div>
        </td>
        <td>
          <div class="sw-row">
            <label class="sw-label">Status</label>
            <input type="text" class="sw-medium" v-model="orderData.orderStatus">
          </div>
          <div class="sw-row">
            <label class="sw-label">Payment</label>
            <input type="text" class="sw-medium" v-model="orderData.paymentType">
          </div>
          <div class="sw-row">
            <label class="sw-label">Payment</label>
            <input type="text" class="sw-medium" v-model="orderData.paymentType">
          </div>
        </td>
      </tr>
    </table>  

    <br/><br/>
    <div class="sw-toolbar sw-section-heading" style="width:650px;">
      <span>ORDER ITEMS</span> 
      <span style="flex:1"></span>
      <span>ORDER TOTAL: &nbsp;</span>
      <span class="sw-primary-color"> {{orderData.strOrderTotal}} </span>
    </div>
    <el-table :data="orderData.orderLine" style="width:650px;" empty-text="No Data">
      <el-table-column prop="productId"   label="#"          width="50"/>
      <el-table-column prop="productName" label="NAME" />
      <el-table-column prop="quantity"    label="QTY"        width="60"/>
      <el-table-column prop="unitPrice"   label="PRICE"      width="60"/>
      <el-table-column prop="discount"    label="DISCOUNT"   width="80"/>
      <el-table-column prop="orderItemStatus" label="STATUS" width="100"/>
      <el-table-column width="60">
        <template slot-scope="scope">
          <!-- i  v-if="$store.state.role !== 'CUSTOMER'" class="el-icon-edit"   style="font-size:16px; vertical-align: middle; cursor:pointer; color:cornflowerblue" @click="onEdit(scope.row)"></i -->
          <i  v-if="$store.state.role !== 'CUSTOMER'" class="el-icon-delete" style="font-size:16px; vertical-align: middle; cursor:pointer; color:orangered;margin-left:8px" @click="onDelete(scope.row)"></i>
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
    }
  },
  
  methods:{
    getData(){
      let me = this;
      Rest.getOrders(1,1000,this.$data.orderData.id).then(function(resp){
        if (resp.data.msgType==="SUCCESS" && resp.data.list.length>0){

          //me.$data.orderData = resp.data.list[0];

          me.$data.orderData = resp.data.list.map(function(v){
            let dt = new Date(v.orderDate);
            let strOrderDate  = new Intl.DateTimeFormat('en-US', {year:'numeric', month: 'short', day:'numeric'}).format(dt);
            let strOrderTotal = new Intl.NumberFormat('en-US', {useGrouping:true, currency: 'USD'}).format(v.orderTotal);
            return {
              ...v,
              strOrderDate,
              strOrderTotal
            }
          })[0];
          me.$forceUpdate();
        }
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
        me.$data.loading=false;
      });

    },
    
    onApplyChanges(){
      alert("Pending implementation...")
      /*
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
      */

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
          //me.$emit("changed");
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
  }

}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
.sw-medium{
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
  margin-top:2px;
  align-items: center;
}
.sw-primary-color{
  color:$sw-primary-color;
}


</style>

