<template>
  <div>
    <div class="sw-section-heading"> {{title}} </div>
    <el-table :data="chartData" empty-text="No Data">
      <el-table-column type="index" />
      <el-table-column v-for="(fld, key) in tableFields" :key=key :prop="key"  :label="key=='_date'?'Date':key" :width="key=='_date'?'150':''"/>
    </el-table>
  </div>
</template>
<script>
import Rest from '@/rest/Rest';

export default {
  props: {
    title    : { type:String },
    chartData: {type: Array, default:function () { return [] } }
  },
  data(){
    return {
      tableFields:{}
    }
  },
  methods: {

  },
  mounted(){
    if (this.chartData.length > 0 ){
      let row = this.chartData[0];
      let fields={};
      for (let prop in row) {
        if (prop.endsWith("|color")===false) {
          fields[prop] = row[prop+"|color"]?row[prop+"|color"]:"";
        }        
      }
      this.$data.tableFields= fields;
      console.log("Chart-Config:%o",this.chartData);
    }
  }

}
</script>

