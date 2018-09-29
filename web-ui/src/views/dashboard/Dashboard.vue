<template>
<div class="sw-dashboard">
  <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['390px']" closeHtml='Close'>
    <sw-chart-table-data :chartData="selectedChartData" :title="selectedChartTitle"> </sw-chart-table-data>
  </vue-slideout-panel>

  <div class="sw-row1">

    <div class="sw-chart-card">
      <div class="p1" style="display:inline-block; margin-bottom:12px;color:#777"> Orders by Status </div>
      <clr-icon shape="flag" style="width:20px; height:20px; color:mediumseagreen;float:right"></clr-icon>
      <doughnut-chart
        :chartData="inGoodJunkData"
        :options="$_miniDonutOptions"
        :width="100"
        :height="100"
      >
        <sw-legend :legends="inGoodJunkLegendData"></sw-legend>
      </doughnut-chart>
    </div>

    <div class="sw-chart-card">
      <div class="p1" style="display:inline-block; margin-bottom:12px; color:#777"> Orders by Payment Type </div>
      <clr-icon shape="wallet" style="width:20px; height:20px; color:mediumseagreen;float:right"></clr-icon>
      <doughnut-chart
        :chartData="outGoodJunkData"
        :options="$_miniDonutOptions"
        :width="100"
        :height="100"
      >
        <sw-legend :legends="outGoodJunkLegendData"></sw-legend>
      </doughnut-chart>
    </div>

    <div class="sw-chart-card sw-mini-bar-chart">
      <div class="p1" style="display:inline-block; margin-bottom:12px; color:#777">Top Customers </div>
      <clr-icon shape="shield-check" style="width:20px; height:20px; color:mediumseagreen;float:right"></clr-icon>
      <base-horizontal-bar-chart
        :chartData="inBreakdownData"
        :options="$_miniBarOptions"
        :width="250"
        :height="120"
        style="width:262px"
      >
      </base-horizontal-bar-chart>
    </div>

  </div>
  <div class="sw-row1">
    <div class="sw-chart-card sw-time-bar-chart">
      <time-bar-chart
          ref="inGoodVsjunk"
          :chartData="inBreakdownGroupedData"
          :options="timeChartOptions"
          :width="1070"
          :height="160"
          :buttons="['Table Data']"
          @button-click="onChartButtonClick($refs.inGoodVsjunk, ...arguments)"
          title="Good Vs Junk (Incomming)"
          interval=""
          style="width:100%;"
        >
      </time-bar-chart>
    </div>
  </div>


  <div class="sw-row1">
    <div class="sw-chart-card sw-time-bar-chart">
      <time-bar-chart
          ref="outGoodVsjunk"
          :chartData="outBreakdownGroupedData"
          :options="timeChartOptions"
          :width="1075"
          :height="160"
          :buttons="['Table Data']"
          @button-click="onChartButtonClick($refs.outGoodVsjunk, ...arguments)"
          title="Good Vs Junk (Outgoing)"
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
import GoodJunk from '@/charts/GoodVsJunk'
import DoughnutChart from '@/charts/DoughnutChart'
import BaseHorizontalBarChart from '@/charts/BaseHorizontalBarChart'
import TimeBarChart from '@/charts/TimeBarChart'
import SwLegend from '@/components/legend/Legend'
import Rest from '@/rest/Rest';
import VueSlideoutPanel from 'vue-slideout-panel/src/VueSlideoutPanel'
import SwChartTableData from '@/views/dashboard/ChartTableData'



export default {
  data(){
    let me = this;
    return {
      showFor:'all',
      showSlideOut:false,
      domainOrOuName:"",
      selectedChartData:[],
      selectedChartTitle:"",
      outGoodJunkData :{},
      inGoodJunkData  :{},
      inBreakdownData :{},
      outBreakdownData:{},
      inBreakdownGroupedData :{},
      outBreakdownGroupedData:{},
      inSpamGroupedData:{},
      inAndOutGroupedData:{},
      topConnectingIpData:{},
      topVirusData:{},
      inGoodJunkLegendData:[],
      outGoodJunkLegendData:[],

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
            categoryPercentage:0.90
          }],
          xAxes: [{
            stacked:true,
            type : 'time',
            time: {
              max: new Date().getTime(),
              min: undefined,
              displayFormats: {
                'hour'   : 'MMM-DD HH:mm',
                'day'    : 'MMM-DD',
                'week'   : 'MMM-DD',
                'month'  : 'MMM-DD YYYY',
                'quarter': 'MMM-DD',
                'year'   : 'MMM-DD YY',
              }
            },
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
            barPercentage :1,
            categoryPercentage:0.90
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

    getInboundVsOutbound(){
      let me = this;
      let goodColor="lightgray";
      let junkColor="orangered";

      Rest.getInboundVsOutBound(this.$store.state.dashboardInterval,this.$data.showFor, this.$data.domainOrOuName, true)
      .then(function(resp){
        let dateLabels=[];
        let outCounts=[];
        let inCounts=[];
        let inJunkTotal =0;
        let inGoodTotal =0;
        let outJunkTotal=0;
        let outGoodTotal=0;

        resp.data.list.map(function(dataRow){
          dateLabels.push(new Date(dataRow.DTIME));
          inJunkTotal  = inJunkTotal  + dataRow.INBOUND_JUNK_COUNT;
          inGoodTotal  = inGoodTotal  + dataRow.INBOUND_GOOD_COUNT;
          outJunkTotal = outJunkTotal + dataRow.OUTBOUND_JUNK_COUNT;
          outGoodTotal = outGoodTotal + dataRow.OUTBOUND_GOOD_COUNT;
          //For 100% Stack
          //inCounts.push( ((dataRow.INBOUND_JUNK_COUNT   + dataRow.INBOUND_GOOD_COUNT)/(dataRow.INBOUND_JUNK_COUNT+ dataRow.INBOUND_GOOD_COUNT+dataRow.OUTBOUND_JUNK_COUNT + dataRow.OUTBOUND_GOOD_COUNT) *100));
          //outCounts.push(((dataRow.OUTBOUND_JUNK_COUNT + dataRow.OUTBOUND_GOOD_COUNT)/(dataRow.INBOUND_JUNK_COUNT+ dataRow.INBOUND_GOOD_COUNT+dataRow.OUTBOUND_JUNK_COUNT + dataRow.OUTBOUND_GOOD_COUNT) *100)) ;
          inCounts.push(dataRow.INBOUND_JUNK_COUNT   + dataRow.INBOUND_GOOD_COUNT);
          outCounts.push(dataRow.OUTBOUND_JUNK_COUNT + dataRow.OUTBOUND_GOOD_COUNT);

        });
        //console.log("In-Junk: %s, In-Good:%s, Out-Junk:%s, Out-Good:%s", inJunkTotal, inGoodTotal, outJunkTotal, outGoodTotal);

        //Incomming Pie-Chart Chart and Legend
        me.inGoodJunkData={
          labels: ['Good', 'Junk'],
          datasets: [{
            data: [
              inGoodTotal,
              inJunkTotal
            ],
            backgroundColor: [goodColor, junkColor],
            borderWidth: 0,
          }]
        }
        me.inGoodJunkLegendData=[
          {label:'Good Mails', value:inGoodTotal, color:goodColor},
          {label:'Junk Mails', value:inJunkTotal, color:junkColor},
        ];

        //Outgoing Pie-Chart Chart and Legend 
        me.outGoodJunkData={
          labels: ['Good', 'Junk'],
          datasets: [{
            data: [
              outGoodTotal,
              outJunkTotal
            ],
            backgroundColor: [goodColor, junkColor],
            borderWidth: 0
          }]
        }
        me.outGoodJunkLegendData=[
          {label:'Good Mails', value:outGoodTotal, color:goodColor},
          {label:'Junk Mails', value:outJunkTotal, color:junkColor},
        ];

        // Incomming Vs Outgoing (Bar chart time seriese)
        me.inAndOutGroupedData={
          labels: dateLabels,// Labels should be Date objects
          datasets: [
            {
              label: "Out-Bound",
              data: outCounts,
              borderWidth:0,
              backgroundColor: '#816C5B',
              pointRadius:0, 
              pointHitRadius:10,
              pointHoverRadius:3,
              pointHoverBorderWidth:0
            },
            {
              label: "In-Bound",
              data: inCounts,
              borderWidth:0,
              backgroundColor: '#A9A18C',
              pointRadius:0,
              pointHitRadius:10,
              pointHoverRadius:3,
              pointHoverBorderWidth:0
            }
          ]
        }

      }).catch(() => {});
    },

    getThreatBreakdown(innoundOrOutbound){
      let me = this;
      let reportName ="";
      let groupedData={};
      if(innoundOrOutbound==="inbound"){
        reportName="inbound-breakdown";
        groupedData = "inBreakdownGroupedData";
      }
      else if(innoundOrOutbound==="outbound"){
        reportName="outbound-breakdown";
        groupedData = "outBreakdownGroupedData";
      }

      Rest.getReport(reportName, this.$store.state.dashboardInterval, this.$data.showFor, this.$data.domainOrOuName, true)
      .then(function(resp){
        let dateLabels=[];
        let junkCounts=[];
        let goodCounts=[];
        let inSpamCounts=[];
        let inLikelySpamCounts=[];
        let inSpamTotal=0, inLikelySpamTotal=0, inVirusTotal=0, inLikelyVirusTotal=0, inFraudTotal=0, inLikelyFraudTotal=0, inDhaTotal=0, inPolicyTotal=0;

        resp.data.list.map(function(dataRow){
          dateLabels.push(new Date(dataRow.DATE_TIME));
          junkCounts.push(dataRow.JUNK_COUNT);
          goodCounts.push(dataRow.GOOD_COUNT);
          if(innoundOrOutbound==="inbound"){
              
              inSpamCounts.push(dataRow.SPAM_COUNT);
              inLikelySpamCounts.push(dataRow.LIKELY_SPAM_COUNT);

              inSpamTotal        = inSpamTotal + dataRow.SPAM_COUNT;
              inLikelySpamTotal  = inLikelySpamTotal + dataRow.LIKELY_SPAM_COUNT;
              inVirusTotal       = inVirusTotal + dataRow.VIRUS_COUNT;
              inLikelyVirusTotal = inLikelyVirusTotal + dataRow.LIKELY_VIRUS_COUNT;
              inFraudTotal       = inFraudTotal + dataRow.FRAUD_COUNT;
              inLikelyFraudTotal = inLikelyFraudTotal + dataRow.LIKELY_FRAUD_COUNT;
              inDhaTotal         = inDhaTotal + dataRow.DHA_COUNT;
              inPolicyTotal      = inPolicyTotal + dataRow.POLICY_COUNT;
          }
        });
        if(innoundOrOutbound==="inbound" ){
          // Data for Spam caught
          me.inSpamGroupedData={
            labels: dateLabels,// Labels should be Date objects
            datasets: [
              {
                label: "Spam",
                data: inSpamCounts,
                borderWidth:0,
                backgroundColor: 'orangered',
                pointRadius:0, 
                pointHitRadius:10,
                pointHoverRadius:3,
                pointHoverBorderWidth:0,
                pointHoverBackgroundColor:'#333'

              },
              {
                label: "Likely Spam",
                data: inLikelySpamCounts,
                borderWidth:0,
                backgroundColor: 'orange',
                pointRadius:0, 
                pointHitRadius:10,
                pointHoverRadius:3,
                pointHoverBorderWidth:0,
                pointHoverBackgroundColor:'#333'
              },
            ]
          }

          // console.log("spam:%s, likey-spam:%s, virus:%s, likey-virus:%s, fraud:%s, likey-fraud:%s, dha:%s, policy:%s", inSpamTotal, inLikelySpamTotal, inVirusTotal, inLikelyVirusTotal, inFraudTotal, inLikelyFraudTotal, inDhaTotal, inPolicyTotal);
          // Data for Horizontal-mini-bar chart
          me.inBreakdownData={
            labels: [["Spam"], ["Virus"], ["Fraud"], ["DHA"],["Policy"]  ],
            datasets: [
              {
                label: "Confirmed",
                backgroundColor: ["#E45641","#E45641","#E45641","#E45641","#E45641"],
                data: [
                  inSpamTotal,
                  inVirusTotal,
                  inFraudTotal,
                  inDhaTotal,
                  inPolicyTotal
                ]
              },
              {
                label: "Likely",
                backgroundColor: ["#F1A94E","#F1A94E","#F1A94E","#F1A94E","#F1A94E"],
                data: [
                  inLikelySpamTotal ,
                  inLikelyVirusTotal,
                  inLikelyFraudTotal,
                  0,0
                ]
              }
            ]
          }

        }
        me[groupedData]={
          // Labels should be Date objects
          labels: dateLabels,
          datasets: [
            {
              label: "Junk",
              data: junkCounts,
              borderWidth:0,
              backgroundColor: 'orangered',
              pointRadius:0, 
              pointHitRadius:10,
              pointHoverRadius:3,
              pointHoverBorderWidth:0,
              pointHoverBackgroundColor:'#333'
            },
            {
              label: "Good",
              data: goodCounts,
              borderWidth:0,
              backgroundColor: 'lightgray',
              pointRadius:0, 
              pointHitRadius:10,
              pointHoverRadius:3,
              pointHoverBorderWidth:0,
              pointHoverBackgroundColor:'#333'
            },
          ]
        }

      }).catch(() => {}); // End of Response

    },

    getTopReport(reportName, dataVariable, labelFieldName, color){
      let me = this;
      Rest[reportName](this.$store.state.dashboardInterval, this.$data.showFor, this.$data.domainOrOuName, 10)
      .then(function(resp){
          let labels=[];
          let count=[];
          resp.data.list.map(function(dataRow){
            labels.push(dataRow[labelFieldName]);
            count.push(dataRow.EMAIL_COUNT);
          });

          me[dataVariable]={
            labels:labels,
            datasets:[{
              backgroundColor:color,
              data:count
            }]
          }
      })
      .catch(() => {})
    },

    refreshAllChartData(){
      this.getInboundVsOutbound();
      this.getThreatBreakdown("inbound" );
      this.getThreatBreakdown("outbound");
      this.getTopReport('topConnectingIps', 'topConnectingIpData', 'IPADDRESS', 'cornflowerblue');
      this.getTopReport('topInboundViruses', 'topVirusData', 'VIRUSNAME', '#E45641');
    },

    onIntervalChange(val){
      /*
      // Change options (Works but chart rendering has little issue)
      this.$data.timeChartOptions = Object.assign({}, this.$data.timeChartOptions, {
        scales:{
          xAxes:[{
            stacked:true,
            type : 'time',
            time :{
              min:this.$_timeIntervalBasedOption[val].min,
              max:this.$_timeIntervalBasedOption[val].max,
              displayFormats: {
                'hour'   : 'MMM-DD HH:mm',
                'day'    : 'MMM-DD',
                'week'   : 'MMM-DD',
                'month'  : 'MMM-DD YYYY',
                'quarter': 'MMM-DD',
                'year'   : 'MMM-DD YY',
              }
            }
          }]
        }
      });

      */

      this.$store.commit('dashboardInterval',val);
      this.refreshAllChartData();
    }

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

    this.$_miniBarOptions={
      barThickness:1,
      legend:{display:false},
      tooltips:{
        mode: 'y',
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
            display: false,
            drawBorder: false,
          },
          barPercentage :1,
          categoryPercentage:0.95
        }],
        xAxes: [{
          stacked: true,
          ticks:{
            fontSize:10
          },
          gridLines: {
            display: false,
            drawBorder: false,
          },
          barPercentage :1,
          categoryPercentage:0.98
        }]
      },
      animation:{
        onComplete: function () {
          /*
          this.config.data.datasets.forEach(function (dataset) {
            dataset.bars.forEach(function (bar) {
              console.log(bar.value, bar.x, bar.y - 5);
              //ctx.fillText(bar.value, bar.x, bar.y - 5);
            });
          })
          */
        }
      }
    }

  },

  mounted(){
    //this.refreshAllChartData();
  },
  components:{
    GoodJunk,
    DoughnutChart,
    BaseHorizontalBarChart,
    TimeBarChart,
    SwLegend,
    VueSlideoutPanel,
    SwChartTableData,
  }

}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
.sw-row1{
  display: flex;
}
.sw-chart-card{
  background-color: #fff;
  border:1px solid #aaa;
  border-top:3px solid #333;
  border-radius: 0px;

  padding:10px;
  margin:5px 20px 0 0;
  min-width:320px;
  &.sw-mini-bar-chart{
    padding:10px 5px 0 5px;
    margin:5px 0 0 0;
  }

  &.sw-time-bar-chart{
    width:1044px;
    padding:0 5px 0 5px;
    margin:20px 0 0 0;
  }

}



</style>


