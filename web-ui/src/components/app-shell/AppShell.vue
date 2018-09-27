<template>
  <div id="sw-app-shell" class="sw-app-shell">
    <transition name="wipe" mode="out-in">
      <side-nav v-if="$store.state.currentHeaderItem.sideMenu.type==='full'" class="sw-app-side-nav"/>
    </transition>  
    <div class="sw-main-container" >
        <div class="sw-app-header-container" ref="headerContainer">
          <app-header :label="$t('m.es')" :items="AppMenu.getHeaderItems()" />
        </div>
        <div class="sw-page-container" ref="pageContainer">
          <transition name="fade" mode="out-in">
            <router-view></router-view>
          </transition>  
        </div>
    </div>
  </div>
</template>

<script>

import SideNav from '@/components/app-shell/SideNav';
import AppHeader from '@/components/app-shell/AppHeader';
import AppMenu  from '@/menu/AppMenu';

export default {

  name: 'sw-app-shell',
  data:function(){
    return{
      AppMenu
    }
  },
  components: {
    SideNav,
    AppHeader
  },

  methods:{
    pageScroll:function(){
      if (this.$refs.pageContainer.scrollTop > 16){
        this.$refs.headerContainer.classList.add("shadow")
      }
      else{
        this.$refs.headerContainer.classList.remove("shadow")
      }
    }
  },
  mounted(){
    console.log("Mounted:App Shell");
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
@import "~@/assets/styles/_vars.scss";

.wipe-enter-active,
.wipe-leave-active {
    transition: all 0.3s ease-in-out;
}

.wipe-enter,
.wipe-leave-to{
    transform: translateX(-100%);
}

//.fade-enter-active,
.fade-leave-active 
{
  transition: opacity .3s;
}
.fade-enter, 
.fade-leave-to {
  opacity: 0;
}



.sw-app-shell {
  height:100%;
  display:flex;
  flex-direction:row;
  flex-wrap: nowrap;
  justify-content: flex-start;
  align-content:stretch;
  .sw-app-side-nav{
    order:0;
    width:$sw-side-nav-width;
    background: $sw-dark-bg2;
    color:$sw-light-text;
    padding:0;
    overflow-y:auto;
    overflow-x:hidden;
    &::-webkit-scrollbar {width: 8px;}
    &::-webkit-scrollbar-track {background-color: transparent;}
    &::-webkit-scrollbar-thumb {
      background-color: rgba(0,0,0,0.4);
      border-radius:3px;
    }
  }

  .sw-main-container{
    display:flex;
    flex:1;
    order:1;
    background:$sw-light-bg1;
    flex-direction:column;
    padding:0;
    margin: 0;
    height:100%;
    overflow:hidden;
    .sw-app-header-container{
      display:flex;
      width:100%;
      //height:$sw-header-height;
      flex-direction:column;
      flex-wrap: nowrap;
      overflow: hidden;
    }
    .sw-page-container{
      margin-top:5px;
      padding:8px 16px 16px 16px;
      overflow:auto;
      display:flex;
      flex:1;
      flex-direction: column;
      height:100%;
      &::-webkit-scrollbar {width: 8px;}
      &::-webkit-scrollbar-track {background-color: transparent;}
      &::-webkit-scrollbar-thumb {
        background-color: rgba(0,0,0,0.4);
        border-radius:3px;
      }
    }
    .shadow {
      box-shadow: 0 5px 4px -4px #ccc
    }
  }

}

</style>
