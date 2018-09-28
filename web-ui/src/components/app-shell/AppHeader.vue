<template>
  <div class="sw-app-header">
    <div class="sw-app-header-row1">
      <mr-logo style="height:45px;width:45px;margin-left:5px"></mr-logo>
      <h2 class="sw-app-header-label">
        {{label}}
      </h2>
      <div class="sw-app-header-item-group">
        <template v-if="items.length > 1">
        <sw-app-header-item v-for="(item, i) of items" :key="i" :item="item" />
        </template>
      </div>
      <div class="sw-app-header-info-group">
          <sw-logout-button style="align-self:flex-end;"></sw-logout-button>
      </div>
    </div>
    <div class="sw-app-header-row2">

    </div>
  </div>


</template>

<script>
  import SwAppHeaderItem from '@/components/app-shell/AppHeaderItem'
  import SwLogoutButton from '@/components/logout/LogoutButton'
  import {loadLang} from '@/lang'
  import MrLogo from '@/components/logo/Logo';

  export default {
    name: 'sw-app-header',
    props: {
      label : {type:String, default: 'Product Name'},
      items : {type:Array , required: true}
    },
    methods:{
      changeLang(lang){
        var me = this;
        loadLang(lang).then(function(){
          me.$store.commit('lang',lang);
        });
      }
    },

    components: {
      SwAppHeaderItem,
      SwLogoutButton,
      MrLogo
    }
  }
</script>

<style scoped lang="scss">
@import "~@/assets/styles/_vars.scss";

  .sw-app-header{
    display:flex;
    width:100%;
    height:100%;
    flex-direction:column;
    flex-wrap: nowrap;
    overflow: hidden;
  }

  .sw-app-header-row1{
    width:100%;
    display:flex;
    flex-basis:$sw-header-row1-height;
    flex-direction: row;
    align-items:center;
    flex-wrap: nowrap;
    overflow: hidden;

  }

  .sw-app-header-row2{
    width:100%;
    display:flex;
    flex: 1;
    align-self:stretch;
    flex-direction: row;
    flex-wrap: nowrap;
    overflow: hidden;
  }

  .sw-app-header-item-group,
  .sw-app-header-info-group{
    display:flex;
    height:100%;
    flex-wrap: nowrap;
    overflow: hidden;
  }
  .sw-app-header-item-group{
    flex-direction: row;
    flex:1;
  }
  .sw-app-header-info-group{
    flex-direction:column;
    width:200px;
    padding-right:16px;
  }


  h2.sw-app-header-label{
    display:inline-block;
    color:#fff;
    line-height: $sw-header-row1-height;
    width:200px;
    padding:0px 8px;
  }

  .sw-app-header-row2{
    border: 1px solid #ddd;
    border-width:0 0 1px;
    background-color: #fff;
    &:empty {
      display: none;
    }
}

</style>

