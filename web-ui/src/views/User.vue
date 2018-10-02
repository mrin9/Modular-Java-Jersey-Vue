<template>

  <div style="display:flex;flex-direction:column" v-loading="loading" >
    
    <!-- The slider shows a single-product-details panel -->
    <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['700px']" closeHtml='Close'>
      <user-details :rec="selectedRec" @changed="getData();showSlideOut=false"> </user-details>
    </vue-slideout-panel>

    <h3> Manage Users </h3>
    <div class="sw-toolbar" style="width:750px;">
      <el-button type="primary" size="small" @click="onOpenAddProduct()" class="sw-toolbar-item">ADD</el-button>
    </div>
    <el-table :data="tableData" style="width:750px;" empty-text="No Data">
      <el-table-column prop="userId"      label="USER #" width="80"/>
      <el-table-column prop="role"        label="ROLE"   width="85"/>
      <el-table-column prop="fullName"    label="NAME"   />
      <el-table-column prop="email"       label="EMAIL"      width="200"/>
      <el-table-column prop="customerId"  label="CUSTOMER #" width="90"/>
      <el-table-column prop="employeeId"  label="EMPLOYEE #" width="90"/>
      <el-table-column label="" width="50">
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
import UserDetails from '@/views/UserDetails'

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
      console.log("Loaded Data");
      me.$data.loading=true;
      Rest.getUsers(0,1000).then(function(resp){
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
        return Rest.deleteUser(rec.id);
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
    onContinueShopping(){
      console.log("Continue Shopping clicked...")
    },

  },
  mounted(){
    this.getData();
  },
  components: {
    UserDetails,
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

