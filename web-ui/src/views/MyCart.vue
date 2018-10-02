<template>

  <div style="display:flex;flex-direction:column" v-loading="loading" >

    <h3> My Cart </h3>

    <div class="sw-toolbar" style="width:600px;">
      <el-button type="primary" size="small" @click="onContinueShopping()" class="sw-toolbar-item">CONTINUE SHOPPING</el-button>
      <span style="flex:1"></span>
      <span>CART TOTAL:</span> <span style="font-weight:bold;margin-left:5px">{{orderTotal}}</span>
    </div>

    <el-table :data="computedTableData" style="width:600px;" empty-text="No Data">
      <el-table-column prop="productName"   label="Product"/>
      <el-table-column prop="listPrice"     label="Price"     width="80"/>
      <el-table-column prop="quantity"      label="Quantity"  width="80"/>
      <el-table-column prop="productTotal"  label="Total"     width="80"/>
      <el-table-column label="" width="40">
        <template slot-scope="scope">
          <i class="el-icon-delete" style="font-size:16px; vertical-align: middle; cursor:pointer; color:orangered" @click="onDelete(scope.row)"></i>
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
import Rest from '@/rest/Rest';
import store from '@/store';

export default {
  data:function(){
    return {
      tableData:[],
      loading:false
    }
  },

  methods:{
    getData(val){
      let me = this;
      me.$data.loading=true;
      Rest.getCartItems().then(function(resp){
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

    onContinueShopping(){
      console.log("Continue Shopping clicked...")
    },

  },
  computed:{
      computedTableData(){
        return this.tableData.map(function(v){
          return {
            ...v,
            productTotal:(parseInt(v.listPrice) * parseInt(v.quantity))
          }
        });
      },
      orderTotal(){
        return this.tableData.reduce(function(total, v){
          return (parseInt(total) + (parseInt(v.listPrice) * parseInt(v.quantity)))
        },0);
      }

      
  },
  mounted(){
    this.getData();
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

