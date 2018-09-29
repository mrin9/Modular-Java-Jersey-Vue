<template>
  <div class="sw-login">
      <div class="left">
      <div>
        <span style="display=flex">
          <mr-logo color="#65656A" style="display:inline-block;width:50px;height:50px; margin-bottom:50px;"></mr-logo>
          <span class="sw-light-text-on-dark" style="font-size:32px">MRIN</span>
        </span>
        <br/>

        <div class="sw-form-row">
          <label class="sw-light-text-on-dark t2" style="width:100%"> {{$t('m.username')}} </label><br/>
          <input type="text" class="sw-large sw-dark sw-row-width" placeholder="username" v-model="userName" @keyup="loginErrMsg=''">
          <span class="icon icon-user sw-placeholder-icon"></span>
        </div>
        <div class="sw-form-row">
          <label class="sw-light-text-on-dark t2"> {{$t('m.password')}} </label><br/>
          <input type="password" class="sw-large sw-dark sw-row-width" placeholder="password" v-model="password" @keyup="loginErrMsg=''" @keyup.enter="loginClicked()">
          <span  class="icon icon-lock sw-placeholder-icon"></span>
        </div>
        <div class="p2 sw-row-width" style="color:#ED4337;height:50px; padding:10px 0">
            {{loginErrMsg}}
        </div>

        <el-button class="sw-dark sw-row-width" style="margin:60px 0 20px 0;" type="primary"  @click="loginClicked()" :loading="disableLogin">{{$t('m.login')}}</el-button>
        <div class="sw-row-width" style="height:1px; background-color:#777" />

        <div class="sw-row-width">
            <el-dropdown   style="cursor:pointer;float:right; color:#47AFE8; padding:5px 0 0 0" size="small" placement="top-end" trigger="click" @command="changeLang" >
              <span style = "color:#47AFE8;" class="sw-dark p4"> {{$t('m.change_lang')}}<i style="color:orange" class="el-icon-arrow-down el-icon--right"/></span>
              <el-dropdown-menu class="sw-dark" slot="dropdown" >
                <el-dropdown-item command="en" > English</el-dropdown-item>
                <el-dropdown-item command="zh" > Chineese</el-dropdown-item>
                <el-dropdown-item command="hi" > Hindi</el-dropdown-item>
                <el-dropdown-item command="es" > Spanish</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>

        </div>
      </div>
      </div>
      <div class="sw-arc">
      <svg width="100%" height="100%"  viewBox="0 0 80 800" preserveAspectRatio="none">
        <path d="M0 0 Q 155 400 0 800" stroke-width="4px"/>
      </svg>
      </div>
      <div class="right">
        
        <table class="sw-login-help" style="border-spacing:10px 10px">
          <tr><td colspan="3"> Title</td></tr>
          <tr style="height:550px">
            <td style="border-radius:6px;background:#adc165;border:3px solid #99aa52;">Option1</td>
            <td style="background:#ffc73b;border:3px solid #efb025;">Option2</td>
            <td style="background:#ff903e;border:3px solid #e87425;">Option3</td>
          </tr>



        </table>





      </div>
  </div>
</template>


<script>
  import MrLogo from '@/components/logo/Logo';
  import Rest from '@/rest/Rest';
  import RestUtil from '@/rest/RestUtil';
  import router from '@/router';
  import {loadLang} from '@/lang';

  export default {
    name: 'sw-login-page',
    data(){
      return {
        baseUrl:'',
        userName:'admin',
        password:'admin',
        loginErrMsg:'',
        disableLogin:false
      }
    },

    methods:{

      changeLang(lang){
        let me = this;
        loadLang(lang).then(function(){
          me.$store.commit('lang',lang);
        });
      },

      loginClicked(){
        let me = this;
        this.$data.disableLogin= true;
        this.$data.loginErrMsg='';

        Rest.login(this.userName, this.password)
        .then(function(respData){
          me.$data.disableLogin=false;

            //These are the default routes where the user will be taken once he/she logs in 
            let landingPage = {
              'CUSTOMER': '/home/my-cart',
              'ADMIN'   : '/home/manage/dashboard',
              'SUPPORT' : '/home/manage/users',
            }

            if (landingPage[respData.role]){
              router.push(landingPage[respData.role]);
            }
            else{
              router.push("/register")  
            }
        })
        .catch(function(err){
          me.$data.loginErrMsg="Unable to login";
          if (!err.response){
            if (err.code==="ECONNABORTED"){
              me.$message({message: 'Unable to contact the server (' + err.message + ')', type:'error'});
            }
            else{
              me.$message({message: err.message , type:'error'});
            }
          }
          else{
            console.log("REST ERROR: %O", err);
          }
          me.$data.disableLogin=false;
        });
      }


    },
    components: {
      MrLogo
    },

  }
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss" ;

.sw-login{
  display:flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-items:stretch;
  overflow: hidden;

  width:100%;
  height:100%;
  background-color: $sw-light-bg1;
  .left{
    overflow: hidden;
    min-width:320px;
    padding:16px;
    background-color: $sw-dark-bg1;
    display:flex;
    justify-content: center;
    flex-direction: column;
    .sw-light-text-on-dark{
      color:$sw-light-text;
    }
  }
  .sw-arc{
    width:120px;
    svg{
      fill:$sw-dark-bg1;
      stroke:$sw-primary-color;
    }
  }
  .right{
    display:flex;
    flex-direction: column;
    flex:1;
    justify-content: center;
    align-items: center;
  }

  .sw-placeholder-icon{
    color:$sw-light-text;
    z-index:1;
    margin:0 0 0 -30px;
    font-size:18px;
    vertical-align:middle;
  }
  .sw-form-row{
    margin-top:20px;
    label{
      line-height: 1.8;
    }
  }
  .sw-row-width{
    width:275px;
  }

  .sw-login-help{
    border:1px solid gray;
    width:600px;
    height:600px;
  }

}
</style>
