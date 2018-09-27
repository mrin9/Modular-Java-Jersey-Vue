<template>
<div>
<h2>Frameworks</h2>
<p>Goal is to keep the dependecies to the minimum</p>
<table class="sw-table" style="width:600px;">
  <tr> <th style="width:100px">Framework</th> <th>Why Picked</th> <th> </th> </tr>
  <tr>
    <td><b>Vue</b></td>
    <td>Sonicwall Recomended</td>
    <td></td>
  </tr>
  <tr>
    <td><b>vue-router</b></td>
    <td>Official Router for Vue </td>
    <td></td>
  </tr>
  <tr>
    <td><b>vuex</b></td>
    <td>Official State Manager for Vue </td>
    <td></td>
  </tr>
  <tr>
    <td>vuex-persist</td>
    <td>
      Vuex stores state in memory, a page refresh will lose all the application state. vuex-persist allows to store state in local/session storages, cookies or any other storage of your choice
      For persisting state across page refreshes or even application restarts, this library is ideal.
      Reading and writing states on persistent media can be slow and may effect app performance if it is dealing with too many state data.
      This library provides you a way to choose which states needs to be persisted. The library does not do the job of vuex and is not a replacement for vuex, it compliments it.
    </td>
    <td></td>
  </tr>
  <tr>
    <td>axios</td>
    <td> Wrapper on top of native XMLHttpRequest. Makes life easy for dealing with AJAX request chaining. and many other goodies.</td>
    <td></td>
  </tr>
  <tr>
    <td>jstz</td>
    <td> Email Security, deals with many date-time values, time-seriese data and charts. All dates and times on the server are stored in GMT,
      Our Restfull APIs are designed to convert these GMTs into client's time-zone (browser's timezone).
      This takes the pain away from the rest clients to show dates with respect to its geo-location.
      However for this to work, client must provide timezone info to REST APIs. HTML5's Date().getTimezoneOffset() is not a reliable one to use for this purpose
      as it changes based on the region and day of year.<br/>
      jstz does a good job of figuring out the proper timezones, which is then passed to RESTfull APIs
    </td>
    <td></td>
  </tr>
  <tr>
    <td>vue-i18n</td>
    <td> Internationalization Library, One of the popular intl library, but the reason, I liked it coz, it allows bundelling of language-resources as modules and dynamically loading them, instead of loading through AJAX calls.
      It saves network trips, language bundles are smaller as they get minified.
    </td>
    <td></td>
  </tr>
  <tr>
    <td>Element-UI, <br/>AT-UI, <br/>vuetable-2,<br/>vue-bulma</td>
    <td>
      It is difficult to get a good fit for our basic UI components. I have looked into <a href="https://github.com/ant-design-vue/antv">ant-design-vue</a> which is showcased in one of the PoC project, but the major turnoff for me
      was that its a one-person project, code frequency is less and not enough github stars or community around it.<br/><br/>

      There are many other UI frameworks to pick, but none would fit in all the features that we need. It will also be a pure madness if we try to develop these components in-house.<br/>
      <b> Therefore I set up the following criteria to pick a UI framework </b><br/>
      <table>
        <tr><td>-</td> <td>Be minimalistic, should not try to do a lot of magic (this will allow us to fix things, without the framework-authors dependency)</td></tr>
        <tr><td>-</td> <td>Our components are more inclined towards flat design than material design, therefore all meterial design frameworks are out (else we need to code loads of css to make it look like ours, debugging and maintaining will be a pain) </td></tr>
        <tr><td>-</td> <td>Should have enough github stars, large comunity and the project should be actively developed and maintained  </td></tr>
        <tr><td>-</td> <td>Code should be easy to read and understand</td></tr>
      </table>
      <b>Few of the frameworks, that I zeroed in are:</b>
      <table>
       <tr><td>-</td> <td><a href="https://github.com/ElemeFE/element"> Element-UI </a> Big community, lots of contributor and lots of stars. Flat design </td></tr>
       <tr><td>-</td> <td><a href="https://github.com/AT-UI/at-ui"> AT-UI </a> Flat design with managable amount of javascript magic </td></tr>
       <tr><td>-</td> <td><a href="https://github.com/vue-bulma"> vue-bulma </a>
          bulma is a mordern css only framework. Having a css framework with no javascript is an advantage, it is ideal to be used in libraries which manages its own DOM and dont like outside script to modify it. Vue, angular, react all of these fall under this category,
          Vue-bulma is a framework that adds little (vue)script on top of bulma css.
          Nice thing about this is, it is very modular, you have a choice to imort only the components you need.
       </td></tr>
       <tr><td>-</td> <td><a href="https://github.com/ratiw/vuetable-2"> vuetable-2:</a> Data-tables are complex components, vuetable-2 is one of the most simplest, yet feature rich. </td></tr>
      </table>
    </td>
    <td></td>
  </tr>



</table>

</div>
</template>
