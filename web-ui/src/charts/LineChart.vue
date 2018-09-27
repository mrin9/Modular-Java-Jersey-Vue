<template>
<div>
  <div class="sw-chart-container">
    <h3> {{chartTitle}} </h3>
    <base-line-chart :chartData="chartData" :options="options" :height="200"/>
  </div>
</div>
</template>

<script>
import BaseLineChart from '@/charts/BaseLineChart'
export default {
  props  : [
    'chartTitle',
    'chartData',
    'options'
  ],
  data(){
    return {}
  },
  components: {
    BaseLineChart
  }

}
</script>
<style lang="scss" scoped>
.sw-chart-container{
  border: 1px solid #333;
}


</style>

