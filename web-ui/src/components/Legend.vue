<!-- eslint-disable max-len -->
<template>
  <div :class="'m-legend-wrap ' + direction "  >
    <div :key="index" v-for="(l, index) in legends" class="m-legend-item">
      <div :style="{backgroundColor:l.color}" class="m-legend-color"></div>
      <div class="m-legend-value-wrap">
        <div class="m-legend-value">{{l.value}}</div>
        <div class="m-legend-label">{{l.label}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({
  props: {
    direction: {
      type: String,
      default: 'row',
      validator: (value) => ['row', 'col'].indexOf(value) !== -1, // Only accepts values that contain the string 'cookie-dough'.
    },
    legends: {
      type: Array,
      default: () => [],
    },
  },
});
</script>

<style lang="scss" scoped>
.m-legend-wrap.row {
  flex-direction: row;
  .m-legend-item + .m-legend-item {
    margin-left:20px;
  }
}
.m-legend-wrap.col {
  flex-direction: column;
  .m-legend-item + .m-legend-item {
    margin-top:10px;
  }
}
.m-legend-wrap.row {
  flex-direction: row;
  .sw-legend-item + .sw-legend-item {
    margin-left:16px;
  }
}
.m-legend-wrap {
  display:flex;
  margin:0;
  .m-legend-item {
    display: flex;
    flex-direction: row;
    height: 32px;
    align-items: stretch;
    .m-legend-color {
      width:5px;
    }
    .m-legend-value-wrap {
      margin:0px 3px;
    }
    .m-legend-label {
      font-size:12px;
      height:14px;
      line-height: 15px;
    }
    .m-legend-value {
      font-size:18px;
      height:18px;
    }
  }
}
</style>
