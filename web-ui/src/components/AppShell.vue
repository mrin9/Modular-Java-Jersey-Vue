<template>
  <div id="m-app-shell" class="p-d-flex p-flex-row p-jc-start m-app-shell">
    <div class="m-main-container" >
      <div class="m-app-header-container" ref="headerContainer">
        <app-header label="ORDER MANAGER" :items="TopNav[$store.getters.role]" />
      </div>
      <div class="m-page-container" ref="pageContainer">
        <Dialog position="top" header="Header" :modal="true" :closable="false" v-model:visible="showAppDlg" style="width:500px">
          <template #header>
            <h3>{{ dlgHeader }}</h3>
          </template>
          {{ dlgContent }}
          <template #footer>
            <Button label="OK" @click="onDlgOkClick"/>
          </template>
        </Dialog>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script lang='ts'>
import AppHeader from '@/components/AppHeader.vue';
import TopNav from '@/menu/TopNav';
import { onMounted, defineComponent, ref, inject } from 'vue';
import { Emitter } from 'mitt';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default defineComponent({
  setup(): Record<string, unknown> {
    const store = useStore();
    const router = useRouter();
    const showAppDlg = ref(false);
    const whatEvent = ref('');
    const dlgHeader = ref('');
    const dlgContent = ref('');
    const AppEvent:Emitter = inject('AppEvent') as Emitter;
    onMounted(() => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      AppEvent.on('bad-token', (data:any) => {
        whatEvent.value = 'bad-token';
        dlgHeader.value = 'Session Expired';
        dlgContent.value = data.msg;
        showAppDlg.value = true;
        console.log('event-handler for %o', data);
      }); // listen
      store.commit('baseUrl', 'http://localhost:8080/');
    });

    const onDlgOkClick = () => {
      if (whatEvent.value === 'bad-token') {
        router.push('/login');
      }
    };

    return {
      dlgHeader,
      dlgContent,
      showAppDlg,
      TopNav,
      whatEvent,
      onDlgOkClick,
    };
  },
  components: {
    AppHeader,
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
@import "~@/assets/styles/_vars.scss";
.m-app-shell {
  .m-main-container{
    height:100vh;
    width:100vw;
    padding:0;
    margin: 0;
    overflow:hidden;
    background:var(--bg);
    .m-app-header-container{
      display: flex;
      width: 100%;
      background-color: var(--bg-alt);
      flex-direction:column;
      flex-wrap: nowrap;
      overflow: hidden;
    }
    .m-page-container{
      margin-top:5px;
      padding:8px 16px 16px 16px;
      overflow:auto;
      display:flex;
      flex:1;
      flex-direction: column;
      height:100%;
    }
  }
}
</style>
