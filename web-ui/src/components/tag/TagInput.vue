<template>
  <div style="display:flex;flex-wrap: wrap;" :style="{'flex-direction':layout }">
    <span class="sw-tag sw-small" :key="index" v-for="(tag,index) in tags"  >
      {{ tag }} <i class="el-icon-circle-close" @click="onCloseTag(tag)"></i>
    </span>
    <input
      type="text"
      v-model="inputValue"
      :placeholder="placeholderText"
      style="width:80px;margin:1px"
      ref="tagInput"
      class="sw-small"
      @keydown.delete="onRemoveTag"
      @keyup.enter="onAddTag"
      @blur="onAddTag"
      />
  </div>
</template>

<script>

export default {

  props: {
    layout : {type:String,default:'row'},
    placeholderText : {type:String,default:'+ New Tag'},
  },

  data:function(){
    return {
      tags:[],
      inputValue: '',
    }
  },

  methods:{
    onCloseTag(term) {
      this.tags.splice(this.tags.indexOf(term), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.tagInput.focus();
      });
    },

    onAddTag() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.tags.push(inputValue);
      }
      this.inputValue = '';
    },
    onRemoveTag(){
      if (this.inputValue!==undefined && this.inputValue.trim()==""){
        console.log("Remove Tag");
        this.inputValue = this.tags.pop();
      }
    },
    getTags(){
      return this.tags;
    } 
  }

}
</script>

