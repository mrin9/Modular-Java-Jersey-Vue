<template>
<div class="sw-dashboard">
  <vue-slideout-panel v-model="showSlideOut" @close="showSlideOut=false" :widths="['390px']" closeHtml='Close'>
    <sw-chart-table-data :chartData="selectedChartData" :title="selectedChartTitle"> </sw-chart-table-data>
  </vue-slideout-panel>

  <div class="sw-summary-header-bar" style="width:1086px">
     <div class="sw-summary-item">EMAIL PROCESSED</div>
     <div class="sw-summary-item">THREATS BLOCKED</div>
     <div class="sw-summary-item">MTA QUEUE</div>
  </div>

  <div class="sw-summary-value-bar" style="width:1086px">
    <div class="sw-summary-item">
      <div class="sw-summary-mark" style="width:5px; height:100%; margin-right:16px; background-color:orange; display:inline-block"></div>
      <div class="sw-summary-value">535</div>
    </div>
    <div class="sw-summary-item">
      <div class="sw-summary-mark" style="width:5px; height:100%; margin-right:16px; background-color:orange; display:inline-block"></div>
      <div class="sw-summary-value">390</div>
    </div>
    <div class="sw-summary-item">
      <div class="sw-summary-mark" style="width:5px; height:100%; margin-right:16px; background-color:orange; display:inline-block"></div>
      <div class="sw-summary-value">5</div>
    </div>
  </div>

  <div class="sw-row1" style="background-color:#333; padding:16px 8px;width:1070px">


    <div class="sw-chart-card sw-chart-small-card">
      <div class="p1" style="display:inline-block; margin-bottom:12px;color:#333"> Incoming </div>
      <i class="el-icon-download" style="font-size:20px;float:right"></i>
      <doughnut-chart
        :chartData="inGoodJunkData"
        :options="$_miniDonutOptions"
        :width="100"
        :height="100"
      >
        <sw-legend :legends="inGoodJunkLegendData"></sw-legend>
      </doughnut-chart>
    </div>

    <div class="sw-chart-card sw-chart-small-card ">
      <div class="p1" style="display:inline-block; margin-bottom:12px; color:#333">Threats Blocked </div>
      <clr-icon shape="shield-check" style="width:20px; height:20px; color:#333;float:right"></clr-icon>
      <base-horizontal-bar-chart
        :chartData="inBreakdownData"
        :options="$_miniBarOptions"
        :width="210"
        :height="100"
      >
      </base-horizontal-bar-chart>
    </div>

    <div class="sw-chart-card sw-chart-small-card ">
      <div class="p1" style="display:inline-block; margin-bottom:12px; color:#333">Top Connecting IPs</div>
      <clr-icon shape="cloud-traffic" style="width:20px; height:20px; color:#333;float:right"></clr-icon>
      <base-horizontal-bar-chart
        :chartData="topConnectingIpData"
        :options="$_miniBarOptions"
        :width="210"
        :height="100"
      >
      </base-horizontal-bar-chart>
    </div>

    <div class="sw-chart-card sw-chart-small-card sw-mini-bar-chart">
      <div class="p1" style="display:inline-block; margin-bottom:12px; color:#333">Top Spam Recipients</div>
      <clr-icon shape="bug" style="width:20px; height:20px; color:#333;float:right"></clr-icon>
      <base-horizontal-bar-chart
        :chartData="inBreakdownData"
        :options="$_miniBarOptions"
        :width="210"
        :height="100"
      >
      </base-horizontal-bar-chart>
    </div>


  </div>
  <div class="sw-row1" >
    <div class="sw-chart-card sw-chart-big-card sw-time-bar-chart">
      <time-bar-chart
          ref="inGoodVsjunk"
          :chartData="inBreakdownGroupedData"
          :options="timeChartOptions"
          :width="1070"
          :height="160"
          :buttons="['Table Data']"
          @button-click="onChartButtonClick($refs.inGoodVsjunk, ...arguments)"
          title="Good Vs Junk (Incoming)"
          interval=""
          style="width:100%;"
        >
      </time-bar-chart>
    </div>
  </div>

  <div class="sw-row1">
    <div class="sw-chart-card sw-chart-big-card sw-time-bar-chart">
      <time-bar-chart
          ref="inVsOut"
          :chartData="inAndOutGroupedData"
          :options="timeChartOptions"
          :width="1075"
          :height="160"
          :buttons="['Table Data']"
          @button-click="onChartButtonClick($refs.inVsOut, ...arguments)"
          title="Incoming Vs Outgoing"
          interval=""
          style="width:100%;"
        >
      </time-bar-chart>
    </div>
  </div>

  <div class="sw-row1">
    <div class="sw-chart-card sw-chart-big-card sw-time-bar-chart">
      <time-bar-chart
          ref="spamVsLikeySpam"
          :chartData="inSpamGroupedData"
          :options="timeChartOptions"
          :width="1075"
          :height="160"
          :buttons="['Table Data']"
          @button-click="onChartButtonClick($refs.spamVsLikeySpam, ...arguments)"
          title="Spam Vs Likely Spam"
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
import TimeLineChart from '@/charts/TimeLineChart'
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
              fontSize:10,
              fontColor:'#aaa'
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
              fontColor:'#777',
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
      let goodColor="#efefef";
      let junkColor="#CC0000";

      Rest.getInboundVsOutBound("1_day",this.$data.showFor, this.$data.domainOrOuName, true)
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

        //Incoming Pie-Chart Chart and Legend
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

        // Incoming Vs Outgoing (Bar chart time seriese)
        me.inAndOutGroupedData={
          labels: dateLabels,// Labels should be Date objects
          datasets: [
            {
              label: "In-Bound",
              data: inCounts,
              borderWidth:0,
              backgroundColor: '#47AFE8',
              pointRadius:0,
              pointHitRadius:10,
              pointHoverRadius:3,
              pointHoverBorderWidth:0
            },
            {
              label: "Out-Bound",
              data: outCounts,
              borderWidth:0,
              backgroundColor: '#7DD7E0',
              pointRadius:0, 
              pointHitRadius:10,
              pointHoverRadius:3,
              pointHoverBorderWidth:0
            },
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

      Rest.getReport(reportName, "1_day", this.$data.showFor, this.$data.domainOrOuName, true)
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
                backgroundColor: '#CC0000',
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
                backgroundColor: '#FF9900',
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
                backgroundColor: ["#CC0000","#CC0000","#CC0000","#CC0000","#CC0000"],
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
                backgroundColor: ["#FF4229","#FF4229","#FF4229","#FF4229","#FF4229"],
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
              borderWidth:3,
              borderColor:'#CC0000',
              showLine: true,
              backgroundColor: '#CC0000',
              pointRadius:3,
              pointBorderWidth:1, 
              pointHitRadius:10,
              pointHoverRadius:3,
              pointBackgroundColor:'#000',  
              pointHoverBorderWidth:0,
              pointHoverBackgroundColor:'#333'
            },
            {
              label: "Good",
              data: goodCounts,
              borderWidth:3,
              borderColor:'#efefef',
              showLine: true,
              backgroundColor: '#efefef',
              pointRadius:3,
              pointBorderWidth:1,
              pointBackgroundColor:'#000',  
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
      Rest[reportName]("1_day", this.$data.showFor, this.$data.domainOrOuName, 5)
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
      //this.getThreatBreakdown("outbound");
      this.getTopReport('topConnectingIps', 'topConnectingIpData', 'IPADDRESS', '#eee');
      //this.getTopReport('topInboundViruses', 'topVirusData', 'VIRUSNAME', '#E45641');
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
            fontSize:10,
            fontColor:'#333'
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
            fontSize:10,
            fontColor:'#333'
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
    this.refreshAllChartData();
  },
  components:{
    GoodJunk,
    DoughnutChart,
    BaseHorizontalBarChart,
    TimeBarChart,
    TimeLineChart,
    SwLegend,
    VueSlideoutPanel,
    SwChartTableData,
  }

}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/_vars.scss";
.sw-dashboard{
  background-color:#000;
}
.sw-row1{
  display: flex;
}
.sw-chart-small-card{
  background-color: #FDA348;
  border:0px solid transparent;
}

.sw-chart-card{
  border-radius: 0px;
  padding:10px;
  margin:5px 20px 0 0;
  min-width:225px;
  &.sw-db-interval{
    min-width:225px;
    width:225px;
    .sw-align-center{
      display:table;
      margin:0px auto 10px auto;
    }
  }
  &.sw-mini-bar-chart{
    padding:10px 5px 0 5px;
    margin:5px 0 0 0;
  }
  &.sw-time-bar-chart{
    width:1062px;
    padding:0 5px 0 5px;
    margin:20px 0 0 0;
  }

}

.sw-summary-header-bar{
  height:32px;
  background-color: #777;
  width:100%;
  display:flex;
  color:#fff;
  font-size:16px;
  line-height:32px; 
  justify-content:center;
}
.sw-summary-value-bar{
  height:48px;
  background-color: #000;
  color:#fff;
  width:100%;
  display:flex;
  font-size:20px;
  line-height:48px; 
  justify-content:center;
}
.sw-summary-item{
  width:300px;
  display:flex;
}
.sw-summary-mark{
  width:5px; 
  height:100%; 
  margin-right:16px; 
  background: linear-gradient(#000 2%,#333 10%, orange 40%, orange 70%, #333 90%,#000 98%);
  display:inline-block
}

.sw-summary-value{
  font-size:20px;
  line-height:48px; 
  color:#fff;
}


</style>


