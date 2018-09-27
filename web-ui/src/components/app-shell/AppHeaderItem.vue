<template>
  <div class="sw-header-item" :class="[active?'sw-header-item-active':'']" @click="itemClick">
    <div class="sw-circle"><clr-icon :shape="item.icon" size="24"/> </div>
    <span class="sw-header-item-label"> {{item.label}} </span>
  </div>
</template>

<script>
  import store from '@/store'
  import router from '@/router'

  export default {
    name: 'sw-app-header-item',
    store,
    props: {
      item: Object
    },
    computed: {
      active: function(){
        return (this.$store.state.currentHeaderItem.id == this.item.id?true:false);
      }
    },
    methods:{
      itemClick(){
        if (this.$store.state.currentHeaderItem.id !== this.item.id){
          router.push(this.item.to);
          this.$emit('headerItemChange', this.item);
        }
      }
    }
  }
</script>

<style scoped lang="scss">
@import "~@/assets/styles/_vars.scss";
.sw-header-item{
  display:flex;
  min-width:100px;
  margin:10px 15px;
  align-self:center;
  cursor: pointer;
  .sw-header-item-label{
    font-size:18px;
    margin-left:5px;
    line-height: 50px;
    color:$sw-dark-text;
  }
}
.sw-circle{
  width:50px;
  height:50px;
  border-radius:50%;
  display:flex;
  justify-content: center;
  border:2px dashed $sw-orange;
  clr-icon{
    align-self:center;
  }
}
.sw-circle:hover{
    background-color: $sw-orange;
}

.sw-header-item-active .sw-circle{
  background-color: $sw-orange;

}


</style>

