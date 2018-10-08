<template>

  <div style="display:flex;flex-direction:column;width:750px;" v-loading="loading" >
    <!-- The slider shows a single-order-details panel -->
    <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['700px']" closeHtml='Close'>
      <order-details :rec="selectedRec" @changed="getData();"> </order-details>
    </vue-slideout-panel>
    <h3> Manage Orders </h3>
    <div class="sw-toolbar">

    </div>

    <el-table :data="tableData" height="400" empty-text="No Data">
      <el-table-column prop="id"            label="ORDER#" width="70"/>
      <el-table-column prop="strOrderDate"  label="DATE"  width="120"/>
      <el-table-column prop="shipName"      label="SHIP TO" />
      <el-table-column prop="orderStatus"   label="STATUS"    width="90"/>
      <el-table-column prop="paymentType"   label="PAYMENT"   width="80"/>
      <el-table-column prop="strOrderTotal" label="TOTAL"     width="90" align="right"/>
      <el-table-column label="" width="80" align="center">
        <template slot-scope="scope">
          <i v-if="$store.state.role!=='CUSTOMER'" class="el-icon-edit"   style="font-size:16px; vertical-align: middle; cursor:pointer; color:cornflowerblue" @click="onEdit(scope.row)"></i>
          <i v-if="$store.state.role!=='CUSTOMER'" class="el-icon-delete" style="font-size:16px; vertical-align: middle; cursor:pointer; color:orangered;margin-left:8px" @click="onDelete(scope.row)"></i>
          <i v-if="$store.state.role == 'CUSTOMER'" class="el-icon-view"  style="font-size:16px; vertical-align: middle; cursor:pointer;  color:cornflowerblue" @click="onViewDetails(scope.row)"></i>

        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
import Rest from '@/rest/Rest';
import store from '@/store';
import VueSlideoutPanel from 'vue-slideout-panel/src/VueSlideoutPanel'
import OrderDetails from '@/views/OrderDetails'

export default {
  data:function(){
    return {
      loading:false,
      showSlideOut:false,
      tableData:[],
      selectedRec:{}
    }
  },

  methods:{
    getData(){
      let me = this;
      me.$data.loading=true;
      Rest.getOrders(0,10).then(function(resp){
        me.$data.tableData = resp.data.list.map(function(v){
          let dt = new Date(v.orderDate);
          let strOrderDate  = new Intl.DateTimeFormat('en-US', {year:'numeric', month: 'short', day:'numeric'}).format(dt);
          let strOrderTotal = new Intl.NumberFormat('en-US', {useGrouping:true, currency: 'USD'}).format(v.orderTotal);
          return {
            ...v,
            strOrderDate,
            strOrderTotal
          }

        });
        me.$data.loading=false;
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
        me.$data.loading=false;
      });
    },

    onDelete(rec){
      let me = this;
      this.$confirm('Are you sure delete?', 'Confirm', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        return Rest.deleteOrder(rec.id);
      }).then((resp) => {
        if (resp.data.msgType==="SUCCESS"){
          me.$message({message: 'Successfully deleted', type:'success'});
          me.getData()
        }
        else{
          me.$message({message: 'Unable to delete', type:'error', showClose:true, duration:6000});
        }
      })
      .catch((resp) => {
        me.$message({type:'info',message: 'Delete canceled'});          
      });
    },

    onEdit(rec){
      this.$data.showSlideOut = true;
      this.$data.selectedRec  = rec;
    },

    onViewDetails(rec){
      this.$data.showSlideOut = true;
      this.$data.selectedRec  = rec;
    },

    onContinueShopping(){
      console.log("Continue Shopping clicked...")
    },

  },
  mounted(){
    this.getData();
  },
  components: {
    OrderDetails,
    VueSlideoutPanel
  }
}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
.sw-text{
  line-height:24px;
}

.sw-slideout-head{
  position:absolute;
  top:0;
  left:0;
  width:100%;
  display:flex;
  height:60px;
  padding:16px;
  box-shadow: 0px 0px 8px 2px #ccc;
  background-color: #fff;
  z-index:1;
}

.sw-slideout-body{
  margin-top:92px;
}

</style>

