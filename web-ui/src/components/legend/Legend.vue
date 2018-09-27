<template>
  <div class="sw-legend-wrap col">
    <div :key="index" v-for="(l,index) in legends" class="sw-legend-item">
      <div :style="{backgroundColor:l.color}" class="sw-legend-color"></div>
      <div class="sw-legend-value-wrap">
        <div class="sw-legend-value">{{l.value}}</div>
        <div class="sw-legend-label">{{l.label}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    legends : {
      type:Array,
      default:function(){ return []; }
    }
  }
}
</script>

<style lang="scss" scoped>

.sw-legend-wrap.row{
  flex-direction: row;
  .sw-legend-item + .sw-legend-item {
    margin-left:20px;
  }
}
.sw-legend-wrap.col{
  flex-direction: column;
  .sw-legend-item + .sw-legend-item {
    margin-top:10px;
  }
}

.sw-legend-wrap{
  display:flex;
  margin:0;
  .sw-legend-item{
    display:flex;
    flex-direction: row;
    height:32px;

    align-items:stretch;
    .sw-legend-color{
      width:5px;
    }
    .sw-legend-value-wrap{
      margin:0px 3px;
    }
    .sw-legend-label{
      font-size:12px;
      height:14px;
      line-height: 15px;
    }
    .sw-legend-value{
      font-size:18px;
      height:18px;
    }
  }
}


</style>

