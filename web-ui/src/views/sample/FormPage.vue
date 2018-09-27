<template>
	<div>

    <div class="sw-row">
      <h3>Regular HTML5 Input</h3>
      <input type="text" placeholder="First name"/><br/>
      <input type="password" placeholder="password"/><br/>
      <input type="text" placeholder="Medium" class="sw-medium"/><br/>
      <input type="text" placeholder="Small" class="sw-small" /><br/>
    </div>

    <div class="sw-row" style="background:#333; color:#ccc; width:250px; height:180px; padding:16px;">
      <span class="p2"> On Dark Background </span> <br/><br/>
      <input type="text" placeholder="First name" class="sw-dark"/><br/>
      <input type="text" placeholder="Disabled" class="sw-dark" disabled/><br/>
      <input type="text" placeholder="Medium" class="sw-dark sw-medium"/><br/>
      <input type="text" placeholder="Small" class="sw-dark sw-small" /><br/>

    </div>
    <div class="sw-row">
      <h3>Element Text Inputs</h3>
      <el-input placeholder="Refular input"></el-input>
      <el-input placeholder="Disabled" disabled></el-input>
      <el-input type="password" placeholder="Password"></el-input>
    </div>

    <div class="sw-row">
      <h3>Element Input Icons</h3>
      <el-input placeholder="Pick a date" suffix-icon="el-icon-date"/>
      <el-input placeholder="Search term" prefix-icon="el-icon-search"/>
    </div>
    <br/>

    <h3>Element Input Sizes</h3>
    <div class="sw-input-size">
      <el-input placeholder="Default"></el-input>
      <el-input size="medium" placeholder="Medium"></el-input>
      <el-input size="small" placeholder="Small"></el-input>
      <el-input size="mini" placeholder="mini"></el-input>
    </div>

    <div class="sw-input-button">
      <h3>Element Button Inputs</h3>
      <el-input  placeholder="Please input">
        <template slot="prepend">
          <span>www.</span>
        </template>
      </el-input><br/>

      <el-input  placeholder="email">
        <template slot="append">
          <span>@aotu.io</span>
        </template>
      </el-input><br/>

      <el-input  size="medium" placeholder="Please input" prepend-button>
         <el-button slot="prepend" icon="el-icon-search"></el-button>
      </el-input><br/>

      <el-input size="medium" placeholder="Please input" class="input-with-select">
        <el-select style="width:80px" size="medium" slot="prepend" placeholder="Select" :value="''">
          <el-option label="Restaurant" value="1"></el-option>
          <el-option label="Order No." value="2"></el-option>
          <el-option label="Tel" value="3"></el-option>
        </el-select>
      </el-input>
    </div>
    <br/>

    <div class="sw-row">
      <h3>Checkboxes</h3>
      <el-checkbox-group v-model="checkGroup">
        <el-checkbox label="Option A"></el-checkbox>
        <el-checkbox label="Option B"></el-checkbox>
        <el-checkbox label="Option C"></el-checkbox>
        <el-checkbox label="disabled" disabled></el-checkbox>
        <el-checkbox label="selected and disabled" disabled></el-checkbox>
      </el-checkbox-group>
    </div>

    <div class="sw-row">
      <h3>Radio Groups</h3>
      <el-radio-group v-model="radioGroup1" size="large">
        <el-radio label="1" border>Large 1</el-radio>
        <el-radio label="2" border>Large 2</el-radio>
        <el-radio label="3" border>Large 3</el-radio>
      </el-radio-group><br/><br/>
      <el-radio-group v-model="radioGroup2" size="medium">
        <el-radio label="1" border>Medium 1</el-radio>
        <el-radio label="2" border>Medium 2</el-radio>
        <el-radio label="3" border>Medium 3</el-radio>
      </el-radio-group><br/><br/>
      <el-radio-group v-model="radioGroup3" size="small">
        <el-radio label="1" border>Small 1</el-radio>
        <el-radio label="2" border>Small 2</el-radio>
        <el-radio label="3" border>Small 3</el-radio>
      </el-radio-group>
    </div>

    <br/>
    <div class="sw-row">
      <h3>Switches</h3>
      <el-switch v-model="switchVal1"/><br/>
      <el-switch v-model="switchVal2" :width="35"/> <br/>
      <el-switch v-model="switchVal3" active-color="#13ce66" inactive-color="#ff4949"/><br/>
      <el-switch v-model="switchVal4" active-text="Yes" inactive-text="No" style="margin-left:0"/><br/>
      <el-switch v-model="switchVal5" active-icon-class="el-icon-check" inactive-icon-class="el-icon-close" style="margin-left:0"/><br/>

    </div>
    <br/>


	</div>
</template>
<script>
  export default {
    data () {
      return {
        checkGroup: [],
        radioGroup1:'',
        radioGroup2:'',
        radioGroup3:'',
        switchVal1:false,
        switchVal2:false,
        switchVal3:false,
        switchVal4:false,
        switchVal5:false,
      };
    }
  };
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
  .sw-row{
    display:block;
    margin-top: 5px;
    margin-bottom: 24px;
    input,
    .el-input{
      margin-top:3px;
      width:150px;
    }
  }
  .sw-input-size{
    display:flex;
    align-items:flex-end;
    .el-input + .el-input{
      margin-left:2px;
    }
    .el-input{
      width:100px;
    }
  }

  .sw-input-button{
    .el-input{
      width:300px;
      margin-top:3px;
    }
  }

  .el-input__inner{
    width:100px;
  }
  .el-switch{
    margin-top:3px;
    margin-left:30px;
  }

</style>
