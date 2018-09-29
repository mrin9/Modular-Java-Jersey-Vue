<template>
  <div class="sw-app-header">
    <div class="sw-app-header-row sw-row1">
      <mr-logo style="height:36px;width:36px;margin-left:5px"></mr-logo>
      <div class="sw-app-header-label">
        {{label}}
      </div>
      <div class="sw-app-header-item-group">

        <template v-if="items.length > 1">
          <router-link v-for="(item, i) of items" :key="i" tag="span" :to="item.to" class="sw-app-header-item">
            <a> {{item.label}}</a>
          </router-link>
        </template>

      </div>
      <div style="flex:1"></div>
      <sw-logout-button></sw-logout-button>
          
    </div>
    <div class="sw-app-header-row sw-row2">
        Order Processing System
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

  .sw-app-header-row{
    width:100%;
    display:flex;
    flex-wrap: nowrap;
    overflow: hidden;
    flex-direction: row;
    &.sw-row1{
      align-items:center;
      padding:0 8px;
      background-color: #333;
      height:60px;
      .sw-app-header-item-group{
        display:flex;
        flex-direction: row;
        .sw-app-header-item{
          cursor: pointer;
          color  :#fff;
          border : 0;
          margin : 0 3px;
          padding: 0 16px;
          height : 60px;
          line-height: 60px;
          font-size:20px;
          &:hover{
            background-color: #000;
            a {
              color:#fff;
            }
          }
          &.router-link-exact-active{
            a{color:$sw-primary-color;}
          }
        }
      }
    }
    &.sw-row2{
      padding: 5px;
      background-color: #fff;
      border: 1px solid #ddd;
      border-width:0 0 1px;
      font-size:14px;
      font-weight: bold;
      padding: 5px;
      &:empty {
        display: none;
      }

    }

  }

  .sw-app-header-label{
    display:inline-block;
    color:#fff;
    line-height: $sw-header-row1-height;
    width:200px;
    padding:0px 8px;
    font-size:36px;
  }

  a{
    color:$sw-light-text;
    text-decoration: none;
    &.router-link-exact-active{
      color:$sw-primary-color;
      //background-color: rgba(0,0,0,0.3);
    }
  }
  
</style>

