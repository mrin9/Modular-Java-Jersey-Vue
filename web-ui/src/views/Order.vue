<template>

  <div style="display:flex;flex-direction:column" v-loading="loading" >


    <!-- The slider shows a single-order-details panel -->
    <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['700px']" closeHtml='Close'>
      <order-details :rec="selectedRec" @changed="getData();showSlideOut=false"> </order-details>
    </vue-slideout-panel>

    <div class="sw-toolbar" style="width:850px;">

    </div>

    <el-table :data="tableData" style="width:750px;" height="400" empty-text="No Data">
      <el-table-column prop="id"          label="ORDER#" width="70"/>
      <el-table-column prop="orderDate"   label="DATE"  width="120"/>
      <el-table-column prop="shipName"    label="SHIP TO" />
      <el-table-column prop="orderStatus" label="STATUS"    width="90"/>
      <el-table-column prop="paymentType" label="PAYMENT"   width="80"/>
      <el-table-column prop="orderTotal"  label="TOTAL"     width="80"/>
      <el-table-column label="" width="70">
        <template slot-scope="scope">
          <i class="el-icon-edit"   style="font-size:16px; vertical-align: middle; cursor:pointer; color:cornflowerblue" @click="onEdit(scope.row)"></i>
          <i class="el-icon-delete" style="font-size:16px; vertical-align: middle; cursor:pointer; color:orangered;margin-left:8px" @click="onDelete(scope.row)"></i>
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
    getData(val){
      let me = this;
      me.$data.loading=true;
      Rest.getOrders(0,1000).then(function(resp){
        me.$data.tableData = resp.data.list;
        me.$data.loading=false;
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
        me.$data.loading=false;
      });
    },

    onDelete(rec){
      let me = this;
      this.$confirm('Are you sure to remove it from the cart?', 'Confirm', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        alert("Do Delete");
      })
      .catch(() => {
        me.$message({type:'info',message: 'Delete canceled'});          
      });
    },

    onEdit(rec){
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

