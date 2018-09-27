<template>
  <div>
    <h3> Sonicwall Themed Buttons </h3>

    <br/><h4>Buttons</h4>
    <div class="sw-row">
      <el-button type="primary">Primary Button</el-button>
      <el-button>Default Button</el-button>
      <el-button type="text">Text Button</el-button>
    </div>

    <br/><br/><h4>Status Buttons</h4>
    <div class="sw-row">
      <el-button plain>Default</el-button>
      <el-button type="primary" plain>Primary</el-button>
      <el-button type="success" plain>Success</el-button>
      <el-button type="danger" plain>Danger</el-button>
      <el-button type="warning" plain>Warning</el-button>
      <el-button type="info" plain>Info</el-button>
    </div>
    <div class="sw-row">
      <el-button>Default</el-button>
      <el-button type="primary">Primary</el-button>
      <el-button type="success">Success</el-button>
      <el-button type="danger">Danger</el-button>
      <el-button type="warning">Warning</el-button>
      <el-button type="info">Info</el-button>
    </div>
    <div class="sw-row">
      <el-button round>Default</el-button>
      <el-button round type="primary">Primary</el-button>
      <el-button round type="success">Success</el-button>
      <el-button round type="danger">Danger</el-button>
      <el-button round type="warning">Warning</el-button>
      <el-button round type="info">Info</el-button>
    </div>

    <div class="sw-row">
      <el-button icon="el-icon-search" circle></el-button>
      <el-button type="primary" icon="el-icon-edit" circle></el-button>
      <el-button type="success" icon="el-icon-check" circle></el-button>
      <el-button type="info" icon="el-icon-message" circle></el-button>
      <el-button type="warning" icon="el-icon-star-off" circle></el-button>
      <el-button type="danger" icon="el-icon-delete" circle></el-button>
     </div>

    <br/><br/><h4>Icon Buttons</h4>
    <div class="sw-row">
      <el-button icon="el-icon-edit"></el-button>
      <el-button icon="el-icon-delete">Add User</el-button>
      <el-button icon="el-icon-search"> Search</el-button>
      <el-button type="primary" icon="el-icon-upload"></el-button>
      <el-button type="primary" :loading="true"></el-button>
    </div>


    <br/><br/><h4>Size</h4>
    <div style="margin-top:5px; display: flex;align-items: flex-end;">
      <el-button type="primary">Default</el-button>
      <el-button type="primary" size="medium">Medium</el-button>
      <el-button type="primary" size="small">Small</el-button>
      <el-button type="primary" size="mini">Mini</el-button>
    </div>
    <div style="margin-top:5px; display: flex;align-items: flex-end;">
      <el-button round type="primary">Default</el-button>
      <el-button round type="primary" size="medium">Medium</el-button>
      <el-button round type="primary" size="small">Small</el-button>
      <el-button round type="primary" size="mini">Mini</el-button>
    </div>

    <div style="margin-top:5px; display: flex;align-items: flex-end;">
      <el-button type="primary" icon="el-icon-upload"></el-button>
      <el-button type="primary" size="medium" icon="el-icon-upload"></el-button>
      <el-button type="primary" size="small" icon="el-icon-upload"></el-button>
      <el-button type="primary" size="mini" icon="el-icon-upload"></el-button>
    </div>
    <div style="margin-top:5px;">
      <el-button-group style="display:block;margin-top:4px">
        <el-button size="medium">Left</el-button>
        <el-button size="medium">Center</el-button>
        <el-button size="medium">Right</el-button>
      </el-button-group>
      <el-button-group size="small" style="display:block;margin-top:4px">
        <el-button size="medium" type="primary"> Left</el-button>
        <el-button size="medium" type="primary">Center</el-button>
        <el-button size="medium" type="primary">Right</el-button>
      </el-button-group>
      <el-button-group style="display:block;margin-top:4px" size="medium">
        <el-button>Left</el-button>
        <el-button>Center</el-button>
        <el-button>Right</el-button>
      </el-button-group>

    </div>

    <br/><br/><h4>Textbox and Button</h4>
    <div class="sw-text-button-block">
        <input type="text" placeholder="HTML Input"/> <el-button type="primary">Default</el-button><br/>
        <el-input placeholder="EL-Input"/> <el-button type="primary">Default</el-button><br/>

      <input type="text" placeholder="HTML Input" class="sw-medium"/> <el-button type="primary" size="medium">Medium</el-button><br/>
      <el-input size="medium" placeholder="EL-Input"/> <el-button type="primary" size="medium">Medium</el-button><br/>

      <input type="text" placeholder="HTML Input" class="sw-small" /> <el-button type="primary" size="small">small</el-button><br/>
      <el-input size="small" placeholder="EL-Input"/>   <el-button type="primary" size="small">small</el-button><br/>
    </div>
  </div>
</template>

<style lang="scss" scoped>
  .sw-row{
    margin-top: 8px;
  }
  .sw-text-button-block{
    display:block;

    & > input,
    .el-input{
      margin-top:2px;
      margin-right:2px;
      width:150px;
    }
  }
</style>
