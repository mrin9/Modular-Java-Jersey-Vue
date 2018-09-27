<template>
<div>
  <div class="sw-chart-container">
    <base-bar-chart class="sw-doughnut-chart"
      :chartData="chartData"
      :chartOptions="chartOptions"
      :height="height"
      :width="width"
    />
    <slot></slot>
  </div>
</div>
</template>

<script>
import BaseBarChart from '@/charts/BaseBarChart'
export default {

  props: {
    chartTitle   : { type:String },
    chartData    : { type:Object },
    chartOptions : { type:Object },
    height       : { type:Number, default:250 },
    width        : { type:Number, default:500 }
  },

  data(){
    return {}
  },

  components: {
    BaseBarChart
  }

}
</script>
<style lang="scss" scoped>
.sw-doughnut-chart{
  margin-right:20px;
}
.sw-chart-container{
  //border: 1px solid #333;
  margin:5px;
  display:flex;
  flex-direction: row;
  align-items: center;
}


</style>

