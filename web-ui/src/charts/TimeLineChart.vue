<template>
<div>
  <div class="sw-chart-container">
    <div class="sw-toolbar" style="width:100%">
      <div class="sw-chart-title">{{title}}</div>
      <div style="flex:1"> </div>
      <div class="sw-chart-title">{{interval}}</div>
      <!-- Uncomment below for getting Chart Data Button -->
      <!-- el-button v-for="(btn, index) in buttons" :key="index" size="small"  @click="$emit('button-click', index, simplifyChartData())" >{{buttons[index]}}</el-button -->
    </div>
    <base-line-chart class="sw-time-bar-chart" :chartData="chartData" :options="options" :height="height" :width="width"></base-line-chart>
  </div>
</div>
</template>

<script>
import BaseLineChart from '@/charts/BaseLineChart'
export default {

  props: {
    chartData : { type:Object },
    options   : { type:Object },
    title     : { type:String },
    interval  : { type:String },
    height    : { type:Number, default:250 },
    width     : { type:Number, default:500 },
    buttons   : { type:Array , default:function () { return [] } },
  },

  data(){
    return {}
  },
  methods:{

    simplifyChartData(){
      let dataArray   =  [];
      let items=0;
      let dateRows  = this.chartData.labels;
      let fieldCols = this.chartData.datasets;

      if (dateRows && dateRows.length && fieldCols && fieldCols.length){
        items = dateRows.length;
      }
      if (items > 0){
        for (let i = 0; i < items; i++){
          let row = {};
          row._date = dateRows[i];
          for (let j = 0; j < fieldCols.length; j++){
            row[ fieldCols[j].label ] = fieldCols[j].data[i];
            row[ fieldCols[j].label+"|color" ] = fieldCols[j].backgroundColor;
          }
          dataArray.push(row);
        }
        return dataArray;
      }
      return[];
    }
  },

  components: {
    BaseLineChart
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
  flex-direction: column;
  align-items: center;
}

.sw-chart-title{
  color:#fff;
  font-weight:bold;
}


</style>

