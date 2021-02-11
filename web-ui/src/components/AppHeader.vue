<template>
  <div class="m-app-header">
    <Logo style="height:36px;width:36px;margin-left:5px"></Logo>
    <div class="m-app-header-label">
      {{label}}
    </div>
    <div class="m-app-header-item-group">
      <template v-if="items?.length > 1">
        <router-link v-for="(item, i) of items" :key="i" tag="span" :to="item.to" class="m-app-header-item">
            {{item.label}}
        </router-link>
      </template>
    </div>
    <div style="flex:1"></div>
    <div class="p-d-flex p-flex-row p-jc-end" style="color:var(--fg-alt); font-size:0.75rem">
      <div class="p-d-flex p-flex-column p-ai-end p-jc-between p-mr-2">
        <span> {{ $store.getters.role }} </span>
        <span> {{ $store.getters.userName }} </span>
      </div>
      <Button icon="pi pi-sign-out" class="p-button-rounded" @click="$router.push('/login')"/>
    </div>
  </div>
</template>

<script lang=ts>
import { defineComponent } from 'vue';
import Logo from '@/components/Logo.vue';

export default defineComponent({
  props: {
    label: { type: String, default: 'ORDER MANAGEMENT' },
    items: { type: Array, required: true },
  },
  components: {
    Logo,
  },
});
</script>

<style scoped lang="scss">
@import "~@/assets/styles/_vars.scss";
  $m-header-height: 50px;
  .m-app-header {
    display:flex;
    width:100%;
    height:$m-header-height;
    flex-direction: row;
    align-items: center;
    flex-wrap: nowrap;
    overflow: hidden;
    .m-app-header-item {
      display:inline-block;
      cursor: pointer;
      color  :#ccc;
      border : 0;
      margin : 0 3px;
      padding: 0 16px;
      height : 100%;
      font-size:14px;
      line-height: $m-header-height;
      &:hover{
        background-color: #000;
        color:var(--primary-color);
      }
      &.router-link-exact-active{
        color: var(--primary-color);
      }
    }
  }

  .m-app-header-label{
    display:inline-block;
    color:#fff;
    width:200px;
    padding:0px 8px;
    font-size:20px;
    font-weight: 100;
  }
  a {
    color:var(--alt-fg);
    text-decoration: none;
    &.router-link-exact-active{
      color: var(--primary-color);
      //background-color: rgba(0,0,0,0.3);
    }
  }
</style>
