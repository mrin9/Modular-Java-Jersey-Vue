<template>
<div class="sw-my-cart">
  <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['390px']" closeHtml='Close'>
    <sw-chart-table-data :chartData="selectedChartData" :title="selectedChartTitle"> </sw-chart-table-data>
  </vue-slideout-panel>

  <div>
    <h1> My Cart </h1>
  </div>  

</div>
</template>
<script>

//import store from '@/store'



export default {
  data(){
    return {
      tabelData:{}      

    };
  },
  
  methods:{


  },


  mounted(){
    console.log("My Cart Mounted")
    //this.refreshAllChartData();
  }
}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";



</style>


