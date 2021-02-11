<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <Logo style="display:inline-block;width:50px;height:50px;"></Logo>
    <HelloWorld msg="Mrin Welcome to Your Vue.js + TypeScript App"/>
    <Button label="Submit" />

  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import Logo from '@/components/Logo.vue';
import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src

export default defineComponent({
  name: 'Home',
  components: {
    HelloWorld,
    Logo,
  },
});
</script>
