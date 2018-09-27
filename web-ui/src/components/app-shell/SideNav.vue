<template>
  <div class="sw-side-nav">
    <div style="padding:16px;" >
      <sw-logo color="#fff" style="width:200px; margin:auto"/>
    </div>

    <template v-for="(m, idx) of $store.state.currentHeaderItem.sideMenu.items">
      <div v-if="m.type === 'group'" :key="idx" class="sw-nav-group">
        <div class="sw-nav-item sw-nav-group-title" @click="toggle(m.id)">
          <span>{{m.label}}</span><i :ref="m.label+'icon'" class="icon" :class="[m.expanded ? 'el-icon-arrow-up' : 'el-icon-arrow-down']"></i>
        </div>
        <div :class="{expanded:m.expanded}" class="sw-nav-group-items" :ref="m.label">
          <router-link v-for="(mc, mc_idx) in m.items" :key="mc_idx" :to="mc.to"  class="sw-nav-item sw-nav-link">
            <div class="sw-active-pointer"></div>
            {{mc.label}}
          </router-link>
        </div>
      </div>
      <div v-else-if="m.type === 'link'" :key="idx" class="sw-nav-item sw-nav-link">
        <router-link  :to="m.to" >
          <div class="sw-active-pointer"></div>
          {{m.label}}
        </router-link>
      </div>
      <!--
      <div v-else-if="m.type === 'label'" :key="idx" class="sw-nav-item sw-nav-label">
        {{m.label}}
      </div>
      <div v-else :key="idx" class="sw-nav-sep" style="background-color:yellow; height:1px"/>
      -->
    </template>

  </div>
 </template>

<script>
import SwLogo from '@/components/logo/Logo';
//import AppMenu from '@/components/app-shell/AppMenu';


export default {
  methods: {
    toggle: function (group_id) {
      let menu = this.$store.state.currentHeaderItem.sideMenu.items;
      for(let i=0; i< menu.length; i++){
        if (menu[i].type==="group" && menu[i].id === group_id){
          menu[i].expanded = !menu[i].expanded;
          break;
        }
      }
    }
  },
  components:{
    SwLogo
  }

}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
.router-link-exact-active .sw-active-pointer{
  width:5px;
  position: absolute;
  top:0;
  right:0;
  bottom:0;
  background-color: $sw-primary-color;
}
.sw-side-nav{
  display:flex;
  flex-direction:column;
  width:100% ;
  overflow:auto;
  a{
    text-decoration: none;
  }
  .sw-nav-item {
    display:block;
    position: relative;
    padding:8px 10px;
    justify-content:space-between;
    align-items: baseline;
    line-height:1.5;
    height:24px;
    &:hover{
      background-color: rgba(255,255,255,0.1);
    }
  }
  .sw-nav-label {
    padding-top:24px;
    font-size:18px;
  }
  .sw-nav-group-title{
    cursor: pointer;
    color:#fff;
    display:flex;
    flex-direction:row;
    font-size:14px;
    font-weight:600;
  }
  .sw-nav-group-items{
    background-color: rgba(0,0,0,0.4);
    display:none;
    &.expanded{
      display:block;
    }
    .sw-nav-link{
      padding-left:20px;
    }
  }
  .sw-nav-link{
    font-size:13px;
  }
  a{
    color:$sw-light-text;
  }
  a.router-link-exact-active{
    color:$sw-primary-color;
    //background-color: rgba(0,0,0,0.3);
  }


.dropdown {
  position: relative;
  height: 0;
  overflow: hidden;
  transition: height 350ms;

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 1rem;
    background-image: linear-gradient(to top, white, rgba(white, 0));
  }

  &-enter,
  &-leave-to { opacity: 0 }

  &-leave,
  &-enter-to { opacity: 1 }

  &-enter-active,
  &-leave-active {
    position: absolute;
    width: 100%;
    transition: opacity 200ms ease-in-out;
  }

  &-enter-active { transition-delay: 100ms }
}




}
</style>
