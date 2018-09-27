<template>
<div class="sw-page-bar">
  <div class="sw-text">{{msg}}</div>
  <button class="sw-btn sw-small"  :class="btn.page==currentPage?'sw-primary':'' " v-for="btn in totalButtons" :key="btn.page">
    {{ btn.page }}
  </button>
</div>

</template>

<script>

export default {
  name: 'page-bar',
  props: {
    pageSize : {type:Number,default:0},
    totalRecs: {type:Number,default:0},
    currentPage : {type:Number,default:0},
    msg      : {type:String,default:''}
  },
  computed:{
    totalPages:function(){
      if (this.$props.pageSize > 0 &&  this.$props.totalRecs > 0 ){
        return Math.ceil(this.$props.totalRecs/this.$props.pageSize)
      }
      else{
        return 0;
      }
    },
    totalButtons:function(){
      let btns=[];
      let startPage = this.$props.currentPage >= 2 ? this.$props.currentPage-2:1;
      let endPage =   ((this.$props.currentPage + 2 <= this.totalPages) ? (this.$props.currentPage+2) : this.totalPages);
      console.log("current:",this.$props.currentPage, "total:", this.totalPages)

      for(let i =startPage; i<=endPage; i++){
        btns.push({page:i})
      }
      return btns;
    }
  }
}

</script>

<style lang="scss" scoped>

.sw-page-bar{
  display:flex;
  align-items: baseline;
  justify-content: flex-end;
  .sw-btn{
    margin:0 0 0 4px;
  }
  .sw-text{
    padding:4px 10px;

  }
}

</style>
