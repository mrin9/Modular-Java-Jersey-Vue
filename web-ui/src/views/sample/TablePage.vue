<template>
<div>

<h3>HTML Table</h3>
<table class="sw-table" style="width:650px">
  <tr>
    <th>Company</th>
    <th>Contact</th>
    <th>Country</th>
  </tr>
  <tr>
    <td>Alfreds Futterkiste</td>
    <td>Maria Anders</td>
    <td>Germany</td>
  </tr>
  <tr>
    <td>Centro comercial Moctezuma</td>
    <td>Francisco Chang</td>
    <td>Mexico</td>
  </tr>
  <tr>
    <td>Ernst Handel</td>
    <td>Roland Mendel</td>
    <td>Austria</td>
  </tr>
  <tr>
    <td>Island Trading</td>
    <td>Helen Bennett</td>
    <td>UK</td>
  </tr>
  <tr>
    <td>Laughing Bacchus Winecellars</td>
    <td>Yoshi Tannamuri</td>
    <td>Canada</td>
  </tr>
  <tr>
    <td>Magazzini Alimentari Riuniti</td>
    <td>Giovanni Rovelli</td>
    <td>Italy</td>
  </tr>
</table>
<br/><br/>

<h3>Element UI Table</h3>
 <el-table :data="tableData" style="width:650px;" empty-text="No Data">
    <el-table-column prop="date" label="Date" width="180"/>
    <el-table-column prop="name" label="Name" width="180"/>
    <el-table-column prop="address" label="Address"/>
</el-table>
<br/><br/>

<h3>With borders</h3>
 <el-table :data="tableData" border style="width:650px;" empty-text="No Data">
    <el-table-column prop="date" label="Date" width="180"/>
    <el-table-column prop="name" label="Name" width="180"/>
    <el-table-column prop="address" label="Address"/>
</el-table>
<br/><br/>


<h3>Row Selection</h3>
 <el-table :data="tableData"  style="width:650px;" empty-text="No Data">
    <el-table-column type="selection" width="55"/>
    <el-table-column prop="date" label="Date" width="180"/>
    <el-table-column prop="name" label="Name" width="180"/>
    <el-table-column prop="address" label="Address"/>
</el-table>
<br/><br/>

<h3>Fixed Header</h3>
 <el-table :data="tableData"  style="width:650px;" height="200" empty-text="No Data">
    <el-table-column type="selection" width="55"/>
    <el-table-column prop="date" label="Date" width="180"/>
    <el-table-column prop="name" label="Name" width="180"/>
    <el-table-column prop="address" label="Address"/>
</el-table>
<br/><br/>

<h3>Empty Table</h3>
<el-date-picker v-model="value1" type="date"></el-date-picker>
<br/><br/>


</div>
</template>
<script>
    export default {
      data() {
        return {
          value1:'',
          tableData: [{
            date: '2016-05-01',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-02',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-03',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-04',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-05',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-06',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-07',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-08',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }]
        }
      }
    }
  </script>
