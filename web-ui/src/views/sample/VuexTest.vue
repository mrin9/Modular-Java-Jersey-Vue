<template>
	<div>
    <h3>{{currentHeaderItem}}</h3>
		<button @click="h1">HeaderItem 1</button>
		<button @click="h2">HeaderItem 2</button>
	</div>
</template>

<script>
import store from '@/store'
export default {
	computed: {
    //currentHeaderItem: () => store.state.currentHeaderItem
    currentHeaderItem(){
      return this.$store.state.currentHeaderItem
    }
  },

	methods: {
    h1: function() {
      return this.$store.commit('currentHeaderItem',{id:'0', label:'Monitor', icon:'icon-monitor'})
    },
		h2: () => store.commit('currentHeaderItem',{id:'1', label:'Manage', icon:'icon-new'}),

	}
}
</script>
