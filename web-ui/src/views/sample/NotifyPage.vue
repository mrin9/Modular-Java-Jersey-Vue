<template>
  <div>
    <br/><h3> PopConfirm </h3>
    <div class="row" >
      <span style="line-height:30px; margin-right:5px;">There are one hundred junk mails in your inbox, do you like to delete them ? </span>
      <el-popover ref="popover5" placement="top" v-model="show" @toggle="toggleShow">
        <p>Are you sure to delete all the junk mails ?</p>
        <div style="text-align: right; margin-top: 8px;">
          <el-button size="small" @click="popAnswerHandler(false)">Cancel</el-button>
          <el-button type="primary" size="small" @click="popAnswerHandler(true)">Sure</el-button>
        </div>
      </el-popover>
      <el-button v-popover:popover5 size="small" type="danger">Delete</el-button>

    </div>

    <!--
    <br/><h2> Alerts </h2>
    <el-alert class="sw-demo-alert" message="Here is the information~" type="error"></el-alert>
    <el-alert class="sw-demo-alert" message="Here is the information~" type="warning"></el-alert>
    <el-alert class="sw-demo-alert" message="Here is the information~" type="info"></el-alert>
    <el-alert class="sw-demo-alert" message="Alert with an ICON" show-icon></el-alert>
    <el-alert class="sw-demo-alert" message="Title of Success" description="Here is the information" type="success" closable></el-alert>
    <el-alert class="sw-demo-alert" message="Title of Error" description="Here is the information" type="error" closable></el-alert>
    <el-alert class="sw-demo-alert" message="Title of Warning" description="Here is the information" type="warning" closable></el-alert>
    <el-alert class="sw-demo-alert" message="Title of Info" description="Here is the information" type="info" closable></el-alert>
    <el-alert class="sw-demo-alert" message="Here is the information~Here is the information~Here is the information~" type="success"></el-alert>
    -->


    <br/><br/><br/><h3> Modal Message </h3>
    <div class="row" >
      <el-button size="medium" @click="modalClick('alert')">Alert</el-button>
      <el-button size="medium" @click="modalClick('confirm')">Confirm</el-button>
      <el-button size="medium" @click="modalClick('prompt')">Prompt</el-button>
    </div>

    <br/><br/><br/><h3> Dialogs </h3>
    <div class="row" >
      <el-button size="medium" @click="showDialog1=true"> Dialog 1</el-button>
      <el-button size="medium" @click="showDialog2=true">Dialog 2</el-button>
    </div>

    <br/><br/><br/><h3> Toast Message </h3>
    <div class="row" >
      <el-button size="medium" type="success" @click="messageClick('success')">Success</el-button>
      <el-button size="medium" type="error" @click="messageClick('error')">error</el-button>
      <el-button size="medium" type="warning" @click="messageClick('warning')">warning</el-button>
      <el-button size="medium" type="info" @click="messageClick('info')">info</el-button>
    </div>
  
    <el-dialog title="Service Activation" :visible.sync="showDialog1" width="380px">
      <label> Service Code    </label><input type="text" v-model="serviceCode"    placeholder="Service Code"   class="sw-medium"/><br/>
      <label> Activation Code </label><input type="text" v-model="activationCode" placeholder="Activation Code" class="sw-medium"/>
      <span slot="footer" class="dialog-footer">
        <el-button size="medium" type="primary" @click="showDialog1 = false">Try</el-button>
        <el-button size="medium" type="primary" @click="showDialog1 = false">Activate</el-button>
        <el-button size="medium" type="primary" @click="showDialog1 = false">Renew</el-button>
      </span>
    </el-dialog>

    <el-dialog class="sw-dialog" title="Service Activation" :visible.sync="showDialog2" width="380px">
      <label> Service Code    </label><input type="text" v-model="serviceCode"    placeholder="Service Code"   class="sw-medium"/><br/>
      <label> Activation Code </label><input type="text" v-model="activationCode" placeholder="Activation Code" class="sw-medium"/>
      <span slot="footer" class="dialog-footer">
        <el-button size="medium" type="primary" @click="showDialog2 = false">Try</el-button>
        <el-button size="medium" type="primary" @click="showDialog2 = false">Activate</el-button>
        <el-button size="medium" type="primary" @click="showDialog2 = false">Renew</el-button>
      </span>
    </el-dialog>
  
  </div>




</template>
<style lang="scss" scoped>
  .row{
    margin-top: 8px;
    button{
      margin-right:5px;
    }
  }
  .sw-demo-alert{
    width:400px;
    margin-bottom:10px;
  }
  label{
    display:inline-block;
    width:120px;
  }
  input[type]{
    margin-bottom:2px;
  }
</style>


<script>

export default {
  name: 'sw-notify-page',
  data:function(){
    return {
      show:false,
      serviceCode:'',
      activationCode:'',
      showDialog1:false,
      showDialog2:false
    }
  },


  methods:{
    toggleShow(){
      this.$data.show=!this.$data.show;
    },

    popAnswerHandler(answer){
      this.$data.show = false; //Close the Pop Confirm

      // Show a notification message
      if (answer){
        this.$message.success({
          title: 'Junk Mail',
          message: 'Your are free of junk emails now !!!'
        })
      }
      else {
        this.$message.info({
          title: 'Operation cancelled',
          message: 'No action have been taken'
        })
      }
    },

    //Modal Click
    modalClick (type) {
        let me = this;
        if (type==="alert"){
          me.$alert('This is a message', 'Title', {confirmButtonText: 'OK'});
        }
        else if (type==="confirm") {
          me.$confirm('This will permanently delete the file. Continue?', 'Warning', {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning'
          })
          .then(function(){
            me.$message({type: 'success',message: 'Delete completed'})
          })
          .catch(function(){
            me.$message({type: 'info',message: 'Delete cancelled'})
          })
        }
        else if (type==="prompt") {
          me.$prompt('Please input your e-mail', 'Tip', {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
            inputErrorMessage: 'Invalid Email'
          });
        }
      /*
      if (type === 'info') {
        this.$alert('Here is the message for Info', 'Title', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        })
      }
      else if (type === 'success') {
        this.$alert({title: 'Title', content: 'Here is the message for Success'})
      }
      else if (type === 'warning') {
        this.$alert({title: 'Title',content: 'Here is the message for Warning'})
      }
      else if (type === 'error') {
        this.$alert({title: 'Title',content: 'Here is the message for Error'})
      }
      */
    },


    //Message Click
    messageClick (type) {
      if (type === 'info') {
        this.$message({showClose: true, duration:0, message: 'this is a info message.', type: 'info'});
      }
      else if (type === 'success') {
        this.$message({showClose: true, duration:0, message: 'this is a success message.', type: 'success'});
      }
      else if (type === 'warning') {
        this.$message({showClose: true, duration:0, message: 'this is a warning message.', type: 'warning'});
      }
      else if (type === 'error') {
        this.$message({showClose: true, duration:0, message: 'this is a error message.', type: 'error'});
      }
    }







  }


}

</script>

