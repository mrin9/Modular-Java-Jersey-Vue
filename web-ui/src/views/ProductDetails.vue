<template>

  <div v-loading="loading" >
    <span class="sw-section-heading">
      PRODUCT #  <span class="sw-primary-color"> {{productData.id ? productData.id : "NEW"}}</span>
    </span> <br/>
    <div class="sw-gray-text">Update Produt details (the data gets refreshed after certain interval)</div>
    <br/><br/>
    <div class="sw-row">
      <label class="sw-label">Category</label>
      <el-select size="medium" style="width:185px" v-model="productData.category">
          <el-option label="Camera" value="Camera"></el-option>
          <el-option label="Laptop" value="Laptop"></el-option>
          <el-option label="Tablet" value="Tablet"></el-option>
          <el-option label="Phone" value="Phone"></el-option>
      </el-select>  

    </div>
    <div class="sw-row">
      <label class="sw-label">Code & Name </label>
      <input type="text" style="width:60px" class="sw-medium" v-model="productData.productCode">
      <input type="text" class="sw-medium" v-model="productData.productName">
    </div>
    <div class="sw-row">
      <label class="sw-label">Description</label>
      <input type="text" style="width:185px" class="sw-medium" v-model="productData.description">
    </div>

    <div class="sw-row">
      <label class="sw-label">Discontinued ? </label>
      <el-switch v-model="productData.discontinued" :active-value="1" :inactive-value="0" />
    </div>  

    <br/><br/>
    <span class="sw-section-heading">COST & STOCK LEVELS</span> 
    <div class="sw-row">
      <label class="sw-label">Standard Cost</label>
      <input type="text" class="sw-medium" v-model="productData.standardCost">
    </div>
    <div class="sw-row">
      <label class="sw-label">List Price</label>
      <input type="text" class="sw-medium" v-model="productData.listPrice">
    </div>
    <div class="sw-row">
      <label class="sw-label">Stock Levels</label>
      <el-input style="width:100px" size="medium" v-model="productData.targetLevel">
        <template slot="prepend">Target</template>
      </el-input>
      <el-input style="width:100px" size="medium" v-model="productData.reorderLevel">
        <template slot="prepend">Reorder</template>
      </el-input>
    </div>
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
      productData : this.rec,  // Assign the prop value to data to make it reactive
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

