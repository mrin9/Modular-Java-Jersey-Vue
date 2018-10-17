<template>
<div>
  <div class="sw-chart-container">
    <base-doughnut-chart class="sw-doughnut-chart"
      :chartData="chartData"
      :options="options"
      :height="height"
      :width="width"
    />
    <slot></slot>
  </div>
</div>
</template>

<script>
import BaseDoughnutChart from '@/charts/BaseDoughnutChart'
export default {

  props: {
    chartTitle: {type:String},
    chartData : {type:Object},
    options   : {type:Object},
    height:{type:Number, default:150},
    width :{type:Number, default:150}
  },

  data(){
    return {}
  },

  components: {
    BaseDoughnutChart
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
  align-items: flex-end;
}


</style>

