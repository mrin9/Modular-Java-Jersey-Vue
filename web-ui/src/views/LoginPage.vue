<template>
  <div class="m-login">
    <div class="left">
    <div>
      <span class="display:flex" >
        <Logo style="display:inline-block;width:50px;height:50px; margin-bottom:50px;"></Logo>
        <span class="sw-light-text-on-dark" style="font-size:32px">MRIN</span>

        <div class="p-d-flex p-flex-column p-jc-center p-mt-4 p-ml-6" >
          <span class="p-input-icon-left">
            <i class="pi pi-user" />
            <InputText type="text" v-model="username" placeholder="Username" style="background:#222; color:#bbb; border-color:#555; width:100%; font-size:0.875rem"/>
          </span>
          <span class="p-input-icon-left p-mt-1">
            <i class="pi pi-lock" />
            <InputText type="text" v-model="password" placeholder="Password" style="background:#222; color:#bbb; border-color:#666; width:100%;font-size:0.875rem"/>
          </span>
          <div class="p-d-flex p-flex-row p-jc-end p-mt-2">
            <Button label="Register" @click="onRegisterClick" class="p-mr-1 p-button-sm p-button-raised" style="border-color:#777; background-color:#777;"/>
            <Button label="Login" @click="onLoginClick" :disabled="loginDisabled" class="p-button-sm p-button-raised"/>
          </div>
          <!-- Localization is Disaabled as the vue-i18n is still in beta -->
          <!--
          <div class="p-d-flex p-flex-row p-jc-end p-mt-2 p-ai-center" >
            <div class="p-d-flex" @click="onChangeLangClick()" style="fontSize: .75rem; line-height:1rem; color:var(--primary-color); cursor:pointer">
              <div> CHANGE LANGUAGE {{ t('message.hello') }} </div>
              <i class="pi pi-globe p-ml-1"></i>
            </div>
            <Menu ref="languageMenuEl" :model="languages" :popup="true" class="m-small p-mt-1" style="width:9rem"/>
          </div>
          -->
          <div style="margin-top:100px">
            <hr style="border:0; border-top: 1px solid #666;">
            <span style="color: #888; font-size:12px"> Use buttons bellow to login as different roles </span>
            <div class="p-d-flex p-mt-2">
              <Button label="ADMIN" @click="username='admin'; password='password'" class="p-mr-1 m-btn" style="background-color:#adc165; border:0"/>
              <Button label="CUSTOMER" @click="username='customer'; password='password'" class="p-mr-1 m-btn" style="background-color:#ffc73b; border:0"/>
              <Button label="SUPPORT" @click="username='support'; password='password'" class="m-btn" style="background-color:#ff903e; border:0"/>
            </div>
            <div class="p-mt-1">
              <Button label="API DOCS" @click="onApiDocsClick()" class="p-mr-1 m-btn" style="width:100%; background-color:#777;"/>
            </div>
          </div>
        </div>
      </span>
    </div>
    </div>
    <div class="m-arc">
      <svg width="100%" height="100%"  viewBox="0 0 80 800" preserveAspectRatio="none">
        <path d="M0 0 Q 155 400 0 800" stroke-width="4px"/>
      </svg>
    </div>
    <div class="right">
    </div>
  </div>
</template>

<script lang='ts'>
import { ref, onMounted, defineComponent } from 'vue';
import Logo from '@/components/Logo.vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import UsersApi from '@/api/users-api'; // eslint-disable-line import/no-cycle

export default defineComponent({
  setup(): unknown {
    const router = useRouter();
    const store = useStore();
    const username = ref('admin');
    const password = ref('password');
    const loginDisabled = ref(false);
    const win = window;

    const onLoginClick = async () => {
      loginDisabled.value = true;
      const resp = await UsersApi.login(username.value, password.value);
      loginDisabled.value = false;
      if (resp.data.msgType === 'SUCCESS') {
        router.push('/home');
      } else {
        console.log('Unable to login');
      }
    };

    const onRegisterClick = async () => {
      router.push('/register');
    };

    const onApiDocsClick = () => {
      win.location.replace(`${win.location.origin}/api-docs/index.html`);
      // router.push('/api-docs/index.html');
    };

    onMounted(() => {
      store.commit('baseUrl', 'http://localhost:8080/');
    });

    return {
      loginDisabled,
      username,
      password,
      onLoginClick,
      onRegisterClick,
      onApiDocsClick,
    };
  },
  components: {
    Logo,
  },
});
</script>

<style lang="scss" scoped>
.m-login {
  display:flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-items:stretch;
  overflow: hidden;
  width:100%;
  height:100%;
  background-color: var(--bg);
  .left{
    overflow: hidden;
    min-width:320px;
    padding:16px;
    color: var(--fg-alt);
    background-color: var(--bg-alt);
    display:flex;
    justify-content: center;
    flex-direction: column;
  }
  .m-arc {
    width:120px;
    svg {
      fill: var(--bg-alt);
      stroke: var(--accent-color);
    }
  }
  .right {
    display:flex;
    flex-direction: column;
    flex:1;
    justify-content: center;
    align-items: center;
  }
  .m-btn {
    font-size: .7rem;
    font-weight:700;
    max-height:26px;
    color: #333;
    border:0;
  }
}
</style>
