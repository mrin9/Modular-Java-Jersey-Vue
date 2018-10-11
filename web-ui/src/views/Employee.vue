<template>

  <div style="display:flex;flex-direction:column;width:900px;" v-loading="loading" >
    <!-- The slider shows a single-product-details panel -->
    <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['700px']" closeHtml='Close'>
      <employee-details :rec="selectedRec" @changed="getData();showSlideOut=false"> </employee-details>
    </vue-slideout-panel>

    <h3> Manage Employees </h3>
    <div class="sw-toolbar">
      <el-button type="primary" size="small" @click="onOpenAddEmployee()" class="sw-toolbar-item">ADD</el-button>
    </div>
    <div class="sw-toolbar">
      <input type="text" class="sw-medium"  placeholder="Serach by name" v-model="searchValue" @keyup="applySearch">
      <!--
      <el-button type="primary" size="medium" style="margin-left:5px" icon="el-icon-search" class="sw-toolbar-item"></el-button>
      -->
      <span style="flex:1"/>
      <el-button type="primary" size="medium" @click="onOpenAddEmployee()" class="sw-toolbar-item">ADD</el-button>
    </div>
    <el-table :data="tableData" empty-text="No Data">
      <el-table-column prop="employeeId" label="EMP #" width="50"/>
      <el-table-column prop="userId"     label="USER #" width="80"/>
      <el-table-column prop="fullName"   label="NAME"  />
      <el-table-column prop="email"      label="EMAIL"      width="220" />
      <el-table-column prop="jobTitle"   label="JOB TITLE"  width="190" />
      <el-table-column prop="department" label="DEPARTMENT" width="100" />
      <el-table-column label="" width="60" align="center">
        <template slot-scope="scope">
          <i class="el-icon-edit"   style="font-size:16px; vertical-align: middle; cursor:pointer; color:cornflowerblue" @click="onEdit(scope.row)"></i>
          <i class="el-icon-delete" style="font-size:16px; vertical-align: middle; cursor:pointer; color:orangered;margin-left:8px" @click="onDelete(scope.row)"></i>
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
import EmployeeDetails from '@/views/EmployeeDetails'

export default {
  data:function(){
    return {
      loading:false,
      showSlideOut:false,
      tableData:[],
      selectedRec:{},
      searchValue:'',
      currentPage:1,
      pageSize:10,
      totalPages:0,
      totalRecs:0,
      recsInCurrentPage:0

    }
  },

  methods:{
    getData(start, limit){
      let me = this;
      console.log("Loaded Data");
      me.$data.loading=true;
      Rest.getEmployees(start,limit).then(function(resp){
        me.$data.totalPages = resp.data.totalPages;
        me.$data.totalRecs  = resp.data.total;
        me.$data.recsInCurrentPage = resp.data.list.length;
        me.$data.tableData  = resp.data.list.map(function(v){
          return {
            ...v,
            fullName: (v.firstName + " " + v.lastName)
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
        return Rest.deleteEmployee(rec.employeeId);
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
          me.$message({message: 'Unable to delete, this could be due to the employee being reffered in other tables', type:'error', showClose:true, duration:6000});
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
    onPageChange(pageNum){
      this.getData(pageNum,this.$data.pageSize);
      this.$data.currentPage = pageNum;
    },

    onOpenAddEmployee(){
      this.$data.showSlideOut = true;
      this.$data.selectedRec  = {id:0};
    }

  },
  mounted(){
    this.getData(0,this.$data.pageSize);
  },
  components: {
    VueSlideoutPanel,
    EmployeeDetails
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

</style>

