<template>
<div class="sw-dashboard">
  <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['390px']" closeHtml='Close'>
    <sw-chart-table-data :chartData="selectedChartData" :title="selectedChartTitle"> </sw-chart-table-data>
  </vue-slideout-panel>

  <div class="sw-row1">

    <div class="sw-chart-common">
      <div class="p1" style="display:inline-block; margin-bottom:12px;color:#777"> Orders by Status </div>
      <clr-icon shape="flag" style="width:20px; height:20px; color:mediumseagreen;float:right"></clr-icon>
      <doughnut-chart
        :chartData="ordersByStatusData"
        :options="$_miniDonutOptions"
        :width="140"
        :height="140"
      >
        <sw-legend :legends="ordersByStatusLegendData" direction="row"></sw-legend>
      </doughnut-chart>
    </div>

    <div class="sw-chart-common">
      <div class="p1" style="display:inline-block; margin-bottom:12px; color:#777"> Orders by Payment Type </div>
      <clr-icon shape="wallet" style="width:20px; height:20px; color:mediumseagreen;float:right"></clr-icon>
      <doughnut-chart
        :chartData="ordersByPaymentTypeData"
        :options="$_miniDonutOptions"
        :width="140"
        :height="140"
      >
        <sw-legend :legends="ordersByPaymentTypeLegendData"></sw-legend>
      </doughnut-chart>
    </div>


  </div>
  <div class="sw-row1">
    <div class="sw-chart-common sw-time-bar-chart">
      <time-bar-chart
          ref="dailySaleChart"
          :chartData="dailySaleData"
          :options="timeChartOptions"
          :width="1000"
          :height="150"
          :buttons="['Table Data']"
          @button-click="onChartButtonClick($refs.dailySaleChart, ...arguments)"
          title="Daily Sale"
          interval=""
          style="width:100%;"
        >
      </time-bar-chart>
    </div>
  </div>

</div>
</template>
<script>

//import store from '@/store'
import DoughnutChart from '@/charts/DoughnutChart'
import BaseHorizontalBarChart from '@/charts/BaseHorizontalBarChart'
import TimeBarChart from '@/charts/TimeBarChart'
import SwLegend from '@/components/legend/Legend'
import Rest from '@/rest/Rest';
import VueSlideoutPanel from 'vue-slideout-panel/src/VueSlideoutPanel'

export default {
  data(){
    let me = this;
    return {
      showFor:'all',
      showSlideOut:false,
      dailySaleData:{},
      ordersByStatusData:{},
      ordersByStatusLegendData:[],
      ordersByPaymentTypeData:{},
      ordersByPaymentTypeLegendData:[],

      timeChartOptions:{
        legend:{
          display:false
        },
        tooltips:{
          mode:'index',
          intersect:false,
          xPadding:5,
          cornerRadius:2,
          caretSize:0,
          bodyFontSize:11,
          titleFontSize:11
        },
        scales: {
          yAxes: [{
            stacked:true,
            ticks: {
              beginAtZero: true,
              fontSize:10
            },
            gridLines: {
              display: true,
              drawBorder: false,
            },
            barPercentage :1,
            categoryPercentage:0.50
          }],
          xAxes: [{
            stacked:true,
            type : 'time',
            ticks:{
              display : true,
              fontSize: 10,
              autoSkip: true,
              autoSkipPadding:5,
              maxRotation:0,
            },
            gridLines: {
              display   : true,
              drawBorder: true,
              drawTicks : true,
              drawOnChartArea: false,
              color    : "#333",
              lineWidth: 1.5,
              tickMarkLength: 10
            },
            barPercentage : .55,
            //categoryPercentage:1
          }]
        }
      }

    };
  },
  computed:{
    intervalDesc(){
      if (this.$store.state.dashboardInterval ==="1_day"){ return  "Last 1 Day"}
      else if (this.$store.state.dashboardInterval ==="2_day")  { return "Last 2 Days"}
      else if (this.$store.state.dashboardInterval ==="3_day")  { return "Last 3 Days"}
      else if (this.$store.state.dashboardInterval ==="4_day")  { return  "Last 4 Days"}
      else if (this.$store.state.dashboardInterval ==="5_day")  { return  "Last 5 Days"}
      else if (this.$store.state.dashboardInterval ==="6_day")  { return  "Last 6 Days"}
      else if (this.$store.state.dashboardInterval ==="1_week") { return  "Last week"}
      else if (this.$store.state.dashboardInterval ==="2_week") { return  "Last 2 weeks"}
      else if (this.$store.state.dashboardInterval ==="1_month"){ return  "Last month"}
      else if (this.$store.state.dashboardInterval ==="2_month"){ return  "Last 2 month"}
      else if (this.$store.state.dashboardInterval ==="3_month"){ return  "Last 3 months"}
      else if (this.$store.state.dashboardInterval ==="6_month"){ return  "Last 6 months"}
      else if (this.$store.state.dashboardInterval ==="1_year") { return  "Last 1 year"}
    },

    barChartOptions:function(){
      return
    }

  },
  methods:{
    printdata(val){
      console.log(val)
    },

    onChartButtonClick(el, index, simpleChartData){
      this.$data.selectedChartTitle = el.$props.title;
      this.$data.selectedChartData = simpleChartData.map(function(v){
        let options = { year: 'numeric', month: 'short', day: 'numeric', hour:'numeric', minute:'numeric' };
        v._date = new Intl.DateTimeFormat("en", options).format(v._date)
        //v._date = v._date.toString();
        return v;
      });
      this.$data.showSlideOut=true;
    },

    //Data Provider for Bar Chart - Time seriese
    getDailySale(){
      let me = this;
      Rest.getDailySale().then(function(resp){
        let dateLabels=[];
        let saleAmounts=[];
        let discounts=[];

        resp.data.list.map(function(dataRow){
          dateLabels.push(new Date(dataRow.date));
          saleAmounts.push(dataRow.saleAmount);
          discounts.push(dataRow.discount);
        });

        // Daily Sale Data (Bar chart time seriese)
        me.dailySaleData={
          labels: dateLabels,// Labels should be Date objects
          datasets: [
            {
              label: "Sale",
              data: saleAmounts,
              borderWidth:0,
              backgroundColor: '#484848',
              pointRadius:0, 
              pointHitRadius:10,
              pointHoverRadius:3,
              pointHoverBorderWidth:0
            },
            {
              label: "Discounts",
              data: discounts,
              borderWidth:0,
              backgroundColor: '#b8b8b8',
              pointRadius:0,
              pointHitRadius:10,
              pointHoverRadius:3,
              pointHoverBorderWidth:0
            },
          ]
        }

      }).catch(() => {});
    },

    //Data Provider for Doughnut Chart
    getOrdersStats(statsType){
      let me = this;

      Rest.getOrdersStats(statsType).then(function(resp){

        let stats={};
        resp.data.list.map(function(dataRow){
          stats[dataRow.category] = dataRow.count;
        });
        
        if (statsType==="by-status"){
          //For orderByStatus - Doughnut-Chart
          me.ordersByStatusData={
            labels: ['New', 'On Hold', 'Complete', 'Shipped'],
            datasets: [{
              data: [
                stats["New"],
                stats["On Hold"],
                stats["Complete"],
                stats["Shipped"]
              ],
              backgroundColor: ['red', 'green', 'orange', 'blue'],
              borderWidth: 0
            }]
          }
          me.ordersByStatusLegendData=[
            {label:'New'      , value: stats["New"]      , color:'red'   },
            {label:'On Hold'  , value: stats["On Hold"]  , color:'green' },
            {label:'Complete' , value: stats["Complete"] , color:'orange'},
            {label:'Shipped'  , value: stats["Shipped"]  , color:'blue'  }
          ];
        }
        else{
          //For orderByPaymentType - Doughnut-Chart
          me.ordersByPaymentTypeData={
            labels: ['Card', 'Cash', 'Check'],
            datasets: [{
              data: [
                stats["Card"],
                stats["Cash"],
                stats["Check"]
              ],
              backgroundColor: ['red', 'green', 'orange'],
              borderWidth: 0
            }]
          }
          me.ordersByPaymentTypeLegendData=[
            {label:'Card' , value: stats["Card"]  , color:'red'   },
            {label:'Cash' , value: stats["Cash"]  , color:'green' },
            {label:'Check', value: stats["Check"] , color:'orange'}
          ];

        }

      })
    },

    refreshAllChartData(){
      this.getDailySale();
      this.getOrdersStats('by-status');
      this.getOrdersStats('by-payment-type');
    },

  },
  created(){
    let now = new Date();
    let maxDt = now.getTime();
    let millisInDay = (1000*60*60*24);
    this.$_timeIntervalBasedOption={
      "1_day"  : {format:'MMM-DD HH:mm', min:(maxDt -  millisInDay)     , max:maxDt+(millisInDay*5) },
      "2_day"  : {format:'MMM-DD HH:mm', min:(maxDt - (millisInDay*2))  , max:maxDt },
      "3_day"  : {format:'MMM-DD HH:mm', min:(maxDt - (millisInDay*3))  , max:maxDt },
      "4_day"  : {format:'MMM-DD HH:mm', min:(maxDt - (millisInDay*4))  , max:maxDt },
      "5_day"  : {format:'MMM-DD HH:mm', min:(maxDt - (millisInDay*5))  , max:maxDt },
      "1_week" : {format:'MMM-DD'      , min:(maxDt - (millisInDay*7))  , max:maxDt },
      "2_week" : {format:'MMM-DD'      , min:(maxDt - (millisInDay*14)) , max:maxDt },
      "1_month": {format:'MMM-DD'      , min:(maxDt - (millisInDay*30)) , max:maxDt },
      "2_month": {format:'MMM-DD'      , min:(maxDt - (millisInDay*60)) , max:maxDt },
      "3_month": {format:'MMM-DD'      , min:(maxDt - (millisInDay*90)) , max:maxDt },
      "6_month": {format:'MMM-DD'      , min:(maxDt - (millisInDay*182)), max:maxDt },
      "1_year" : {format:'MMM-DD-YY'   , min:(maxDt - (millisInDay*365)), max:maxDt },
    }
    
    this.$_miniDonutOptions = {
      cutoutPercentage:80,
      legend:{
        display:false
      },
      tooltips:{enabled:false}
    }

  },

  mounted(){
    this.refreshAllChartData();
  },
  components:{
    DoughnutChart,
    BaseHorizontalBarChart,
    TimeBarChart,
    SwLegend,
    VueSlideoutPanel,
  }

}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
.sw-row1{
  display: flex;
}
.sw-chart-common{
  background-color: #fff;
  border:1px solid #aaa;
  border-top:3px solid #333;
  border-radius: 0px;

  padding:10px;
  margin:5px 20px 0 0;
  min-width:474px;
  &.sw-mini-bar-chart{
    padding:10px 5px 0 5px;
    margin:5px 0 0 0;
  }

  &.sw-time-bar-chart{
    width:1000px;
    padding:0 5px 0 5px;
    margin:20px 0 0 0;
  }

}



</style>


