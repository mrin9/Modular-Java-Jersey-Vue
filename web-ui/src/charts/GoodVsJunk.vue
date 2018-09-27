<template>
<div>
    <line-chart :chartData="chartData" style="width:100%;"/>
</div>
</template>

<script>
import LineChart from '@/charts/LineChart'
export default {
  data(){
    return{
      chartData:{
        //labels: ['January', 'February','March', 'April'],
        datasets: [
          {
            label: 'GitHub Commits',
            backgroundColor: '#f87979',
            borderWidth: 0 ,
            data: [
              { x: 'Mar 13, 2018 10:00:00 AM', y: 10 },
              { x: 'Mar 13, 2018 11:00:00 AM', y: 25 },
              { x: 'Mar 13, 2018 12:00:00 PM', y: 28 },
              { x: 'Mar 13, 2018 1:00:00 PM', y: 23 },
              { x: 'Mar 13, 2018 2:00:00 PM', y: 37 },
              { x: 'Mar 13, 2018 3:00:00 PM', y: 55 },
              { x: 'Mar 13, 2018 4:00:00 PM', y: 40 }
            ]
          }
        ]
      }
    }
  },
  components: {
    LineChart
  }

}
</script>
<style lang="scss" scoped>


</style>

