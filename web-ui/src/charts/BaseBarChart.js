import { Bar, mixins} from 'vue-chartjs'

export default {
  extends: Bar,
  props : ['chartData', 'options'],
  mixins: [ mixins.reactiveProp], // comment it - if not using the mixin as I need to add extra logic to the mixin to generate HTML legend 
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
              display: false
            }
          }],
          xAxes: [{
            type     : 'time',
            gridLines: {display: false},
            barPercentage :1,
            categoryPercentage:0.90,
            ticks:{
              autoSkip: true,
              maxRotation:0,
            }
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
  watch: {
    options() {
       this.renderChart(this.chartData, this.options);
    },
    // Watcher if you are not using mixin
    /*
    chartData(){
      this.renderChart(this.chartData, this.options);
      console.log("Watch chartData: %o",this.chartData);
    }
    */
  },
  mounted () {
    const options =  Object.assign(this.defaultOptions, this.options);
    this.renderChart(this.chartData, options);
  }
}
