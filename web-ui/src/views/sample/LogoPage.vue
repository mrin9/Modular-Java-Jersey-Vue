<template>
	<div>

    <span class="p3" style="padding:16px; color:#aaa"> Sonicwall's Logo component is a pure svg component, not an img tag with an svg source,
    the advantage we get out of this is, we avoid seperate call to download the image from  server,
    the component is loaded as javascript module. svg wrapped inside img tag behaves like a raster graphic,
    it will its resolution upon scaling, the 3rd most important advantage is that it is connfigurable,
    the same component can be used to show in different colors and different backgrounds, with img tags you need multiple copies
    </span>
    <div style="display:flex; height:270px; margin-top:24px;">
      <div style="display:flex; flex:1; padding:16px;flex-direction: column;align-items: center;">
        <span class="p2"> Colors </span><br/>
        <sw-logo color="#9a1b29" style="width:200px; margin:auto"/>
        <sw-logo color="#be906c" style="width:200px; margin:auto"/>
        <sw-logo color="#757881" style="width:200px; margin:auto"/>
        <sw-logo color="#111" style="width:200px; margin:auto"/>
        <sw-logo color="#23416b" style="width:200px; margin:auto"/>
      </div>
      <div style="display:flex; flex:1; padding:16px;flex-direction: column; align-items: center;background-color:#333">
        <span class="p2 sw-dark"> Dark Background  </span><br/>
        <sw-logo color="#fff" style="width:200px; margin:auto"/>
        <sw-logo color="#be906c" style="width:200px; margin:auto"/>
        <sw-logo color="#757881" style="width:200px; margin:auto"/>
        <sw-logo color="#111" style="width:200px; margin:auto"/>
        <sw-logo color="#ff674c" style="width:200px; margin:auto"/>
      </div>
      <div style="display:flex; flex:1; padding:16px;flex-direction: column; align-items: center;">
        <span class="p2" style=""> Sizes  </span><br/>
        <sw-logo  style="width:300px; margin:auto"/>
        <sw-logo  style="width:250px; margin:auto"/>
        <sw-logo  style="width:200px; margin:auto"/>
        <sw-logo  style="width:150px; margin:auto"/>
        <sw-logo  style="width:100px; margin:auto"/>
        <sw-logo  style="width:80px; margin:auto"/>
      </div>





    </div>



	</div>
</template>
<script>
import SwLogo from '@/components/logo/Logo';
export default {
  name:'sw-logo-page',
  components:{
    SwLogo
  }

}

</script>

<style lang="scss" scoped>
.at-dropdown{
  margin:2px 10px;
}
</style>
