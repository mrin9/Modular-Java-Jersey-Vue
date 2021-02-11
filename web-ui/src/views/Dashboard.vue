<template>
<div class="p-d-flex p-flex-column">

  <!-- Mini Donught Charts -->
  <div class="p-d-flex p-flex-row p-jc-start" >

    <div class="p-d-flex p-flex-column m-mini-chart-container">
      <div class="m-chart-title"> ORDERS BY STATUS </div>
      <div class="p-d-flex p-flex-row p-ai-end">
        <Chart :height="30" :width="30" type="doughnut" :data="chartData.ordersByStatus" :options="miniDonutOptions" style="width:100px; height:100px"/>
        <Legend class="p-mb-3 p-ml-2" :legends="chartData.ordersByStatusLegendData" direction="row"></Legend>
      </div>
    </div>

    <div class="p-d-flex p-flex-column m-mini-chart-container p-ml-4">
      <div class="m-chart-title">ORDERS BY PAYMENT TYPE </div>
      <div class="p-d-flex p-flex-row p-ai-end">
        <Chart :height="100" :width="100" type="doughnut" :data="chartData.ordersByPaymentType" :options="miniDonutOptions" style="width:100px; height:100px"/>
        <Legend  class="p-mb-3 p-ml-2" :legends="chartData.ordersByPaymentTypeLegendData"></Legend>
      </div>
    </div>

  </div>

  <!-- Bar Chart for Daily sale -->
  <div class="p-d-flex p-flex-column p-mt-4 m-chart-container">
    <div class="m-chart-title">DAILY SALE</div>
    <Chart :height="130" :width="1000" type="bar" :data="chartData.dailySaleCount" :options="timeChartOptions"/>
  </div>

  <!-- Bar Chart for Daily Orders -->
  <div class="p-d-flex p-flex-column p-mt-4 m-chart-container">
    <div class="m-chart-title">DAILY ORDER COUNT</div>
    <Chart :height="130" :width="1000" type="bar" :data="chartData.dailyOrderCount" :options="timeChartOptions"/>
  </div>

</div>
</template>
<script lang='ts'>
import { ref, reactive, onMounted, defineComponent } from 'vue';
import { timeChartOptions as defaultTimeChartOptions, miniDonutOptions as defaultMiniDonutOptions } from '@/shared/chart-options';
import StatsApi from '@/api/stats-api'; // eslint-disable-line import/no-cycle
import Legend from '@/components/Legend.vue';

export default defineComponent({
  setup(): unknown {
    const isLoading = ref(false);
    const timeChartOptions = defaultTimeChartOptions;
    const miniDonutOptions = defaultMiniDonutOptions;
    const chartData = reactive({
      dailySaleCount: { },
      dailyOrderCount: { },
      ordersByStatus: { },
      ordersByPaymentType: { },
      ordersByStatusLegendData: {},
      ordersByPaymentTypeLegendData: {},
    });

    const getOrderCountByStatus = async () => {
      try {
        const resp = await StatsApi.getOrdersStats('by-status');
        const byStatus:Record<string, number> = { };
        resp.data.list.forEach((dataRow:Record<string, string | number>) => {
          byStatus[dataRow.category] = dataRow.count as number;
        });
        chartData.ordersByStatus = {
          labels: ['New', 'On Hold', 'Complete', 'Shipped'],
          datasets: [{
            data: [
              byStatus.New,
              byStatus['On Hold'],
              byStatus.Complete,
              byStatus.Shipped,
            ],
            backgroundColor: ['#23A1FC', 'orangered', '#A5BE00', '#FFB41E'],
            borderWidth: 0,
          }],
        };
        chartData.ordersByStatusLegendData = [
          { label: 'New', value: byStatus.New, color: '#23A1FC' },
          { label: 'On Hold', value: byStatus['On Hold'], color: 'orangered' },
          { label: 'Complete', value: byStatus.Complete, color: '#A5BE00' },
          { label: 'Shipped', value: byStatus.Shipped, color: '#FFB41E' },
        ];
      } catch (err) {
        console.log('REST ERROR: %O', err.response ? err.response : err);
      }
    };

    const getOrderCountByPaymenType = async () => {
      try {
        const resp = await StatsApi.getOrdersStats('by-payment-type');
        const byPaymentType:Record<string, number> = { };
        resp.data.list.forEach((dataRow:Record<string, string | number>) => {
          byPaymentType[dataRow.category] = dataRow.count as number;
        });
        chartData.ordersByPaymentType = {
          labels: ['Card', 'Cash', 'Check'],
          datasets: [{
            data: [
              byPaymentType.Card,
              byPaymentType.Cash,
              byPaymentType.Check,
            ],
            backgroundColor: ['#23A1FC', '#A5BE00', 'lightgray'],
            borderWidth: 0,
          }],
        };

        chartData.ordersByPaymentTypeLegendData = [
          { label: 'Card', value: byPaymentType.Card, color: '#23A1FC' },
          { label: 'Cash', value: byPaymentType.Cash, color: '#A5BE00' },
          { label: 'Check', value: byPaymentType.Check, color: '#FFB41E' },
        ];
      } catch (err) {
        console.log('REST ERROR: %O', err.response ? err.response : err);
      }
    };

    const getDailySaleCount = async () => {
      try {
        const resp = await StatsApi.getDailySale();
        const dateLabels:Date[] = [];
        const saleAmounts:number[] = [];
        const discounts:number[] = [];
        resp.data.list.forEach((dataRow:Record<string, string | unknown>) => {
          dateLabels.push(new Date(dataRow.date as string));
          saleAmounts.push(dataRow.saleAmount as number);
          discounts.push(dataRow.discount as number);
        });

        chartData.dailySaleCount = {
          labels: dateLabels, // Labels should be Date objects
          datasets: [
            {
              label: 'Sale',
              data: saleAmounts,
              borderWidth: 0,
              backgroundColor: '#23A1FC',
              pointRadius: 0,
              pointHitRadius: 10,
              pointHoverRadius: 3,
              pointHoverBorderWidth: 0,
            },
            {
              label: 'Discounts',
              data: discounts,
              borderWidth: 0,
              backgroundColor: 'lightgray',
              pointRadius: 0,
              pointHitRadius: 10,
              pointHoverRadius: 3,
              pointHoverBorderWidth: 0,
            },
          ],
        };
      } catch (err) {
        console.log('REST ERROR: %O', err.response ? err.response : err);
      }
    };

    const getDailyOrderCount = async () => {
      try {
        const resp = await StatsApi.getDailyOrderCount();
        const dateLabels:Date[] = [];
        const orderCounts:number[] = [];
        resp.data.list.forEach((dataRow:Record<string, string | unknown>) => {
          dateLabels.push(new Date(dataRow.date as string));
          orderCounts.push(dataRow.count as number);
        });

        chartData.dailyOrderCount = {
          labels: dateLabels, // Labels should be Date objects
          datasets: [
            {
              label: 'Order Count',
              data: orderCounts,
              borderWidth: 0,
              backgroundColor: '#A5BE00',
              pointRadius: 0,
              pointHitRadius: 10,
              pointHoverRadius: 3,
              pointHoverBorderWidth: 0,
            },
          ],
        };
      } catch (err) {
        console.log('REST ERROR: %O', err.response ? err.response : err);
      }
    };

    const refreshAllChartData = () => {
      getDailySaleCount();
      getDailyOrderCount();
      getOrderCountByStatus();
      getOrderCountByPaymenType();
    };

    onMounted(() => {
      refreshAllChartData();
    });

    return {
      isLoading,
      miniDonutOptions,
      timeChartOptions,
      chartData,
    };
  },
  components: {
    Legend,
  },
});
</script>
<style lang="scss" scoped>

.m-chart-container {
  max-width:70rem;
}

.m-mini-chart-container {
  min-width:26rem;
}

.m-chart-container,
.m-mini-chart-container {
  padding:12px;
  background-COLOR: #FFF;
  border:1px solid #AAA;
  border-top-color: #333;
  border-top-width: 4px;
}
.m-chart-title {
  color:#333;
  display: inline-block;
  font-size:.75rem;
  line-height: 1.5rem;
  font-weight:700;
  margin-bottom: .5rem;
}

</style>
