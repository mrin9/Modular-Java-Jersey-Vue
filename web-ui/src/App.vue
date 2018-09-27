<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import RestUtil from '@/rest/RestUtil';
import Rest from '@/rest/Rest';
  export default {

}



  

</script>

<style lang="scss">
@import "~@/assets/styles/index.scss";
#app{
  min-width:$sw-min-app-width;
  min-height:$sw-min-app-height;
}

</style>
