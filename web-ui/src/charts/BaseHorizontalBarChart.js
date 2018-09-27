import { HorizontalBar,mixins } from 'vue-chartjs'

export default {
  extends: HorizontalBar,
  props  : ['chartData', 'options'],
  mixins: [mixins.reactiveProp],
  data() {
    return {
      defaultOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: true
            },
            gridLines: {
              display: true
            }
          }],
          xAxes: [{
            gridLines: {display: false},
            barPercentage :1,
            categoryPercentage:0.98
          }]
        },
        legend: {
          display :true,
          position:'bottom'
        },
        animation: {
          animateRotate: true,
          animateScale: false
        }
      }
    }
  },
  mounted () {
    const options = Object.assign(this.defaultOptions, this.options);
    this.renderChart(this.chartData, options);
  }
}
