<template>
	<div>


    <div style="display:flex; wifth:500px; height:280px; margin:10px">
      <div style="flex:1;padding:16px;">
            <el-dropdown placement="bottom-end">
              <span> Select a Language<i class="el-icon-arrow-down el-icon--right"></i></span>
              <el-dropdown-menu slot="dropdown" >
                <el-dropdown-item command="en" > English</el-dropdown-item>
                <el-dropdown-item command="zh" > Chineese</el-dropdown-item>
                <el-dropdown-item command="hi" > Hindi</el-dropdown-item>
                <el-dropdown-item command="ja" divided > Japanese</el-dropdown-item>
                <el-dropdown-item command="di" disabled > Disabled</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>

            <el-dropdown size="small" placement="bottom-end">
              <el-button size="small">Dropdown <i class="icon icon-chevron-down"></i></el-button>
              <el-dropdown-menu slot="dropdown" >
                <el-dropdown-item command="en" > English</el-dropdown-item>
                <el-dropdown-item command="zh" > Chineese</el-dropdown-item>
                <el-dropdown-item command="hi" > Hindi</el-dropdown-item>
                <el-dropdown-item command="ja" divided> Japanese</el-dropdown-item>
                <el-dropdown-item command="di" disabled> Disabled</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>


            <el-dropdown size="mini" placement="bottom-end" trigger="click">
              <span class="p4"> Click to Open<i style="color:red" class="el-icon-arrow-down el-icon--right"></i></span>
              <el-dropdown-menu slot="dropdown" >
                <el-dropdown-item command="en" > English</el-dropdown-item>
                <el-dropdown-item command="zh" > Chineese</el-dropdown-item>
                <el-dropdown-item command="hi" > Hindi</el-dropdown-item>
                <el-dropdown-item command="ja" divided> Japanese</el-dropdown-item>
                <el-dropdown-item command="di" disabled> Disabled</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
      </div>

      <div style="flex:1; padding:16px 32px;background-color:#333;">
            <el-dropdown  class="sw-dark" size="medium" placement="bottom-end">
              <span class="sw-dark p4"> Language<i style="color:cornflowerblue" class="el-icon-arrow-down el-icon--right"/></span>
              <el-dropdown-menu slot="dropdown" >
                <el-dropdown-item command="en" > English</el-dropdown-item>
                <el-dropdown-item command="zh" > Chineese</el-dropdown-item>
                <el-dropdown-item command="hi" > Hindi</el-dropdown-item>
                <el-dropdown-item command="ja" divided> Japanese</el-dropdown-item>
                <el-dropdown-item command="di" disabled> Disabled</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>


            <el-dropdown  class="sw-dark" size="medium" placement="bottom-end" trigger="click">
              <el-button class="sw-dark" size="medium">Click to Open <i style="color:cornflowerblue" class="el-icon-arrow-down el-icon--right"/></el-button>
              <el-dropdown-menu class="sw-dark" slot="dropdown" >
                <el-dropdown-item command="en" > English</el-dropdown-item>
                <el-dropdown-item command="zh" > Chineese</el-dropdown-item>
                <el-dropdown-item command="hi" > Hindi</el-dropdown-item>
                <el-dropdown-item command="ja" divided> Japanese</el-dropdown-item>
                <el-dropdown-item command="di" disabled> Disabled</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
      </div>
    </div>
    <div style="display:flex; wifth:500px; height:280px">
      <el-select size="large" placeholder="large">
        <el-option label="Exact match" value="exact_match"/>
        <el-option label="Starts with" value="starts_with"/>
        <el-option label="Ends with"   value="ends_with"/>
        <el-option label="Contains"    value="contains"/>
      </el-select>
      <el-select size="medium" placeholder="medium">
        <el-option label="Exact match" value="exact_match"/>
        <el-option label="Starts with" value="starts_with"/>
        <el-option label="Ends with"   value="ends_with"/>
        <el-option label="Contains"    value="contains"/>
      </el-select>
      <el-select size="small" placeholder="small">
        <el-option label="Exact match" value="exact_match"/>
        <el-option label="Starts with" value="starts_with"/>
        <el-option label="Ends with"   value="ends_with"/>
        <el-option label="Contains"    value="contains"/>
      </el-select>
      <el-select size="mini" placeholder="mini">
        <el-option label="Exact match" value="exact_match"/>
        <el-option label="Starts with" value="starts_with"/>
        <el-option label="Ends with"   value="ends_with"/>
        <el-option label="Contains"    value="contains"/>
      </el-select>
    </div>

	</div>
</template>
<style lang="scss" scoped>
.el-dropdown{
  margin:2px 10px;
}
.el-select{
  margin:2px 10px;
}
</style>
