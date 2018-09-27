<template>
  <div>
    <h2>Tab Page</h2>

    <br/><br/><br/><br/><h3>Inline Tabs</h3>
    <div class="sw-row">
      <el-tabs :animated="false" style="width:500px">
        <el-tab-pane label="Tab1" name="name1">
          <p>Content of Tab Pane 1</p>
        </el-tab-pane>
        <el-tab-pane label="Tab2" name="name2">
          <p>Content of Tab Pane 2</p>
        </el-tab-pane>
        <el-tab-pane label="Tab3" name="name3">
          <p>Content of Tab Pane 3</p>
        </el-tab-pane>
      </el-tabs>
    </div>


    <br/><br/><br/><br/><h3>Card Tabs</h3>
    <div class="sw-row">
      <el-tabs type="card" :animated="false" size="small" style="width:500px">
        <el-tab-pane label="Tab1" name="name1">
          <p>Content of Tab Pane 1</p>
        </el-tab-pane>
        <el-tab-pane label="Tab2" name="name2">
          <p>Content of Tab Pane 2</p>
        </el-tab-pane>
        <el-tab-pane label="Tab3" name="name3">
          <p>Content of Tab Pane 3</p>
        </el-tab-pane>
        <div slot="extra">
          <el-button size="small">Extra Content</el-button>
        </div>
      </el-tabs>
    </div>

    <br/><br/><br/><br/><h3>Tab Position</h3>
    <el-radio-group size="medium" v-model="tabPosition" style="margin-bottom: 30px;">
      <el-radio-button label="top">top</el-radio-button>
      <el-radio-button label="right">right</el-radio-button>
      <el-radio-button label="bottom">bottom</el-radio-button>
      <el-radio-button label="left">left</el-radio-button>
    </el-radio-group>

    <el-tabs :tab-position="tabPosition" style="height:200px; width:360px">
      <el-tab-pane label="User">User</el-tab-pane>
      <el-tab-pane label="Config">Config</el-tab-pane>
      <el-tab-pane label="Role">Role</el-tab-pane>
      <el-tab-pane label="Task">Task</el-tab-pane>
    </el-tabs>

    <br/><br/><br/><br/><h3>Icon Tabs</h3>
    <div class="sw-row">
    <el-tabs>
      <el-tab-pane name="name1">
        <span slot="label"><i class="el-icon-date"></i> Route</span>
        <p>Content of Tab Pane 1</p>
      </el-tab-pane>
      <el-tab-pane label="Tab2" name="name2" icon="icon-twitter">
        <p>Content of Tab Pane 2</p>
      </el-tab-pane>
      <el-tab-pane label="Tab3" name="name3" :closable="true">
        <p>Content of Tab Pane 3</p>
      </el-tab-pane>
    </el-tabs>
    </div>
  </div>
</template>

<style lang="scss" scoped>
  .sw-row{
    margin-top: 8px;
  }
</style>

<script>
export default {
  name: 'sw-tab-page',
  data(){
    return{
      tabPosition:'top'
    }
  },
  destroyed(){
    console.log("Tab Destroyed");
  }
}
</script>
