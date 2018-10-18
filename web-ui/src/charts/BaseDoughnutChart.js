import { Doughnut, mixins } from 'vue-chartjs'

export default {
  extends: Doughnut,
  mixins: [mixins.reactiveProp],
  props  : ['chartData', 'options'],
  data(){
    return {
      defaultOptions: {
        responsive: true,
        maintainAspectRatio: false,
        //rotation: -Math.PI,
        //circumference: Math.PI,
        cutoutPercentage: 70,
        legend: {
          display :true,
          position:'right'
        },
        animation: {
          animateRotate: true,
          animateScale: false
        }
      }
    }
  },
  mounted () {
    const mergedOptions = Object.assign(this.defaultOptions, this.options);
    this.renderChart(this.chartData, mergedOptions);
  }
}
