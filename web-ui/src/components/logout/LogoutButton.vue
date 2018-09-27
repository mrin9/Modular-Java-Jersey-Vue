<template>
<table class="sw-logout-button">
  <tr>
    <td style="color:#333;font-weight:bold" class="sw-user sw-role p4" >{{$store.state.role}}</td>
    <td class="sw-power" rowspan="2"> <clr-icon shape="power" size="24" @click="logout" style="color:white"/></td>
  </tr>
  <tr>
    <td class="sw-user sw-name p3" >{{$store.state.user}}</td>
  </tr>
</table>
</template>

<script>
  import router from '@/router'

  export default {
    methods:{
      logout(){
        router.push("/login")
      }
    }
  }
</script>

<style scoped lang="scss">
@import "~@/assets/styles/_vars.scss";
.sw-logout-button{
  margin:5px 0;
  .sw-user{
    color:#999;
    text-align: right;
    padding:0 5px;
  }
  .sw-role{
    text-transform: uppercase;
  }
  .sw-power{
    cursor:pointer;
    padding:3px;
    background-color:#cc0000;
    border:0px solid #aaa;
    border-radius:5px;
  }
}
</style>