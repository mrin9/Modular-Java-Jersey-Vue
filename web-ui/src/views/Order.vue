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
      <el-table-column prop="strOrderDate"  label="DATE"   width="120"/>
      <el-table-column prop="shipName"      label="SHIP TO" />
      <el-table-column prop="orderStatus"   label="STATUS" width="80">
        <template slot-scope="scope">
        <span :class=" 'sw-tag-' + scope.row.orderStatus.replace(/\s/g, '-').toLowerCase() ">
          {{scope.row.orderStatus}}
        </span>
        </template>
      </el-table-column>

      <el-table-column prop="paymentType"   label="PAYMENT"  width="80"/>
      <el-table-column prop="strOrderTotal" label="TOTAL"    width="90" align="right"/>
      <el-table-column label="" width="80"  align="center">
        <template slot-scope="scope">
          <i v-if="$store.state.role!=='CUSTOMER'" class="el-icon-edit"   style="font-size:16px; vertical-align: middle; cursor:pointer; color:cornflowerblue" @click="onEdit(scope.row)"></i>
          <i v-if="$store.state.role!=='CUSTOMER'" class="el-icon-delete" style="font-size:16px; vertical-align: middle; cursor:pointer; color:orangered;margin-left:8px" @click="onDelete(scope.row)"></i>
          <i v-if="$store.state.role == 'CUSTOMER'" class="el-icon-view"  style="font-size:16px; vertical-align: middle; cursor:pointer;  color:cornflowerblue" @click="onViewDetails(scope.row)"></i>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="align-self:center" layout="prev, pager, next" :total="totalRecs" :page-size="pageSize" :current-page="currentPage" @current-change="onPageChange"></el-pagination>

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
      selectedRec:{},
      currentPage:1,
      pageSize:20,
      totalPages:0,
      totalRecs:0,
      recsInCurrentPage:0

    }
  },

  methods:{
    getData(start, limit){
      let me = this;
      me.$data.loading=true;
      Rest.getOrders(start, limit).then(function(resp){
        me.$data.tableData = resp.data.list.map(function(v){
          let dt = new Date(v.orderDate);
          let strOrderDate  = new Intl.DateTimeFormat('en-US', {year:'numeric', month: 'short', day:'numeric'}).format(dt);
          let strOrderTotal = new Intl.NumberFormat('en-US', {useGrouping:true, currency: 'USD'}).format(v.orderTotal);
          
          me.$data.totalPages = resp.data.totalPages;
          me.$data.totalRecs  = resp.data.total;
          me.$data.recsInCurrentPage = resp.data.list.length;

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
          if (me.$data.recsInCurrentPage<=1){
            me.getData(me.$data.currentPage-1, me.$data.pageSize);
          }
          else{
            me.getData(me.$data.currentPage, me.$data.pageSize);
          }
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

    onPageChange(pageNum){
      this.getData(pageNum, this.$data.pageSize);
      this.$data.currentPage = pageNum;
    },

  },
  mounted(){
    this.getData(0,this.$data.pageSize);
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

.sw-tag-new,
.sw-tag-on-hold{
  padding:3px 6px;
  border-radius: $sw-border-radius;
  color:#fff;
  text-transform: uppercase;
  font-size: 10px;
  font-weight: bold;
  font-family: arial;
}

.sw-tag-on-hold{background-color: orangered}
.sw-tag-new{background-color: cornflowerblue}


</style>

