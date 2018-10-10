<template>

   <div style="display:flex;flex-direction:column;width:750px;" v-loading="loading" >
   
    <!-- The slider shows a single-product-details panel -->
    <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['700px']" closeHtml='Close'>
      <product-details :rec="selectedRec" @changed="getData();showSlideOut=false"> </product-details>
    </vue-slideout-panel>
    <h3> Manage Products </h3>
    <div class="sw-toolbar">
      <el-button type="primary" size="small" @click="onOpenAddProduct()" class="sw-toolbar-item">ADD</el-button>
    </div>
    <el-table :data="tableData" height="400" empty-text="No Data">
      <el-table-column prop="id"           label="PRODUCT #" width="85"/>
      <el-table-column prop="productName"  label="NAME"  />
      <el-table-column prop="standardCost" label="COST"        width="80"  align="right"/>
      <el-table-column prop="listPrice"    label="LIST PRICE"  width="100" align="right"/>
      <el-table-column prop="reorderLevel" label="REORDER"     width="80"/>
      <el-table-column prop="category"     label="CATEGORY"    width="100"/>
      <el-table-column label="" width="80" align="center">
        <template slot-scope="scope">
          <i class="el-icon-edit"   style="font-size:16px; vertical-align: middle; cursor:pointer; color:cornflowerblue" @click="onEdit(scope.row)"></i>
          <i class="el-icon-delete" style="font-size:16px; vertical-align: middle; cursor:pointer; color:orangered;margin-left:8px" @click="onDelete(scope.row)"></i>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="align-self:center" layout="prev, pager, next" :total="totalRecs" :page-size="pageSize" :current-page="currentPage" @current-change="onPageClick"></el-pagination>
  </div>

</template>

<script>
import Rest from '@/rest/Rest';
import store from '@/store';
import VueSlideoutPanel from 'vue-slideout-panel/src/VueSlideoutPanel'
import ProductDetails from '@/views/ProductDetails'

export default {
  data:function(){
    return {
      loading:false,
      showSlideOut:false,
      tableData:[],
      selectedRec:{},
      currentPage:1,
      pageSize:10,
      totalPages:0,
      totalRecs:0
    }
  },

  methods:{
    getData(start,limit){
      let me = this;
      console.log("Loaded Data");
      me.$data.loading=true;

      Rest.getProducts(start,limit).then(function(resp){
        me.$data.totalPages  = resp.data.totalPages;
        me.$data.totalRecs   = resp.data.total;
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
      this.$confirm('Are you sure delete?', 'Confirm', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        return Rest.deleteProduct(rec.id);
      }).then((resp) => {
        if (resp.data.msgType==="SUCCESS"){
          me.$message({message: 'Successfully deleted', type:'success'});
          me.getData()
        }
        else{
          me.$message({message: 'Unable to delete, this could be due to the product being reffered in existing orders', type:'error', showClose:true, duration:6000});
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
    onOpenAddProduct(){
      this.$data.showSlideOut = true;
      this.$data.selectedRec  = {id:0};
    },
    onPageClick(pageNum){
      this.getData(pageNum,this.$data.pageSize);
    },

    onContinueShopping(){
      console.log("Continue Shopping clicked...")
    },

  },
  mounted(){
    this.getData(1, this.$data.pageSize);
  },
  components: {
    ProductDetails,
    VueSlideoutPanel
  },
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

