<template>
  <div>
    <ConfirmDialog position="top"></ConfirmDialog>
    <Toast />
    <Sidebar v-model:visible="showSlideOut" position="right" style="width:700px">
      <UserDetails
        :rec = "selectedRec"
        :isCustomer = "isCustomer"
        :isNew = "isNewRec"
        @cancel = "showSlideOut = false"
        @changed="getData()"
      > </UserDetails>
    </Sidebar>
    <h3> Manage Users </h3>
    <DataTable :value="list" class="p-datatable-sm p-datatable-hoverable-rows m-border" style="width:1000px">
      <Column field="userId" header="USER ID" :sortable="true" headerStyle="width:110px;"></Column>
      <Column field="role" header="ROLE" :sortable="true" headerStyle="width:110px"></Column>
      <Column field="fullName" header="NAME"></Column>
      <Column field="email" header="EMAIL" headerStyle="width:260px"></Column>
      <Column field="customerId" header="CUSTOMER ID" headerStyle="width:110px"></Column>
      <Column field="employeeId" header="EMPLOYEE ID" headerStyle="width:110px"></Column>
      <Column header="ACTION" headerStyle="width:100px" bodyStyle="padding:3px">
        <template #body="slotProps">
          <template v-if="($store.getters.role==='ADMIN' || ($store.getters.role=='SUPPORT' &&  slotProps.data.role !== 'ADMIN'))">
            <Button icon="pi pi-pencil" @click="onEditClick(slotProps.data)" class="p-button-sm p-button-rounded p-button-secondary p-button-text" />
            <Button icon="pi pi-trash"  @click="onDeleteClick(slotProps.data)" class="p-button-sm p-button-rounded p-button-danger p-button-text" />
          </template>
        </template>
    </Column>
    </DataTable>
  </div>
</template>

<script lang='ts'>
import { ref, onMounted, defineComponent } from 'vue';
import UserDetails from '@/views/UserDetails.vue';
import UsersApi from '@/api/users-api'; // eslint-disable-line import/no-cycle
import CustomerApi from '@/api/customer-api'; // eslint-disable-line import/no-cycle
import EmployeeApi from '@/api/employee-api'; // eslint-disable-line import/no-cycle
import { useConfirm } from 'primevue/useconfirm';
import { useToast } from 'primevue/usetoast';

export default defineComponent({
  setup(): unknown {
    const isLoading = ref(false);
    const showSlideOut = ref(false);
    const selectedRec = ref({});
    const isNewRec = ref(false);
    const isCustomer = ref(false);
    const list = ref([]);
    const confirm = useConfirm();
    const toast = useToast();

    const getData = async () => {
      console.log('Loaded Data');
      isLoading.value = true;
      try {
        const resp = await UsersApi.getUsers(0, 1000);
        isLoading.value = false;
        list.value = resp.data.list;
      } catch (err) {
        console.log('REST ERROR: %O', err.response ? err.response : err);
        isLoading.value = false;
      }
    };

    const confirmDialog = (rec: Record<string, unknown>) => {
      confirm.require({
        message: `Do you want to delete: ${rec.userId} ?`,
        header: 'Delete Confirmation',
        icon: 'pi pi-question-circle',
        acceptIcon: 'pi pi-check',
        accept: async () => {
          try {
            const resp = await UsersApi.deleteUser(rec.userId as string);
            if (resp.data.msgType === 'SUCCESS') {
              getData();
              toast.add({ severity: 'success', summary: 'Successfully Deleted', life: 3000 });
            } else {
              toast.add({ severity: 'error', summary: 'Access Denied', detail: resp.data.msg, life: 3000 });
            }
          } catch (e) {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Unable to connect to server', life: 3000 });
          }
        },
        reject: () => {
          console.log('NO');
        },
      });
    };

    const onAddClick = () => {
      isNewRec.value = true;
      selectedRec.value = { userId: 'NEW' };
      showSlideOut.value = true;
    };

    const onDeleteClick = (rec:Record<string, unknown>) => {
      confirmDialog(rec);
    };

    const onEditClick = async (rec:Record<string, unknown>) => {
      let resp;
      try {
        if (rec.role === 'CUSTOMER') {
          resp = await CustomerApi.getCustomers(1, 1, rec.customerId as string);
          isCustomer.value = true;
        } else if (rec.role === 'SUPPORT' || rec.role === 'ADMIN') {
          isCustomer.value = false;
          resp = await EmployeeApi.getEmployees(1, 1, rec.employeeId as string);
        }
        if (resp?.data.msgType === 'SUCCESS') {
          selectedRec.value = { ...rec, ...resp.data.list[0] };
          isNewRec.value = false;
          showSlideOut.value = true;
        }
      } catch (e) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Unable to connect to server', life: 3000 });
      }
    };

    onMounted(() => {
      getData();
    });

    return {
      list,
      isLoading,
      showSlideOut,
      selectedRec,
      isNewRec,
      isCustomer,
      onAddClick,
      onDeleteClick,
      onEditClick,
      getData,
    };
  },
  components: {
    UserDetails,
  },
});
/*
import Rest from '@/rest/Rest';
import store from '@/store';
import VueSlideoutPanel from 'vue-slideout-panel/src/VueSlideoutPanel'
import UserDetails from '@/views/UserDetails'
export default {
  data:function(){
    return {
      loading:false,
      showSlideOut:false,
      tableData:[],
      selectedRec:{},
      isNew:false,
    }
  },
  methods:{
    getData(){
      let me = this;
      console.log("Loaded Data");
      me.$data.loading=true;
      Rest.getUsers(0,1000).then(function(resp){
        me.$data.tableData = resp.data.list;
        me.$data.loading=false;
      })
      .catch(function(err){
        console.log("REST ERROR: %O", err.response?err.response:err);
        me.$data.loading=false;
      });
    },
    onDelete(rec){
      let me = this;
      me.$confirm('The action will remove <ul><li> All orders placed by the customer</li><li> Associated customer details </li> <li> Shopping Cart items by this user </li> </ul>  ', 'Confirm', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
        dangerouslyUseHTMLString:true
      }).then(() => {
        return Rest.deleteUser(rec.userId);
      }).then((resp) => {
        if (resp.data.msgType==="SUCCESS"){
          me.$message({message: 'Successfully deleted', type:'success'});
          me.getData()
        }
        else{
          me.$message({message: 'Unable to delete, this could be due to the product being reffered in existing orders', type:'error', showClose:true, duration:6000});
        }
      })
      .catch((resp) => {
        me.$message({type:'info',message: 'Delete canceled'});
      });
    },
    onEdit(rec){
      let me = this;
      let methodName = "";
      let id ="";
      if (rec.role==="CUSTOMER"){
        methodName="getCustomers"
        id = rec.customerId;
      }
      else if (rec.role==="SUPPORT" || rec.role==="ADMIN"){
        methodName="getEmployees";
        id = rec.employeeId;
      }
      if (!id){
        return;
      }
      Rest[methodName](1,1,id).then(function(resp){
        if (resp.data.msgType==="SUCCESS" && resp.data.list.length === 1){
          me.$data.selectedRec  = {...rec, ...resp.data.list[0]};
          me.$data.isNew = false;
          me.$data.showSlideOut = true;
        }
      })
      .catch(function(err){
        me.$message({ message:'Unable to retrieve selected data', type:'error', showClose:true, duration:6000});
        console.log("REST ERROR: %O", err.response?err.response:err);
      });
    },
    onOpenAddProduct(){
      this.$data.isNew = true;
      this.$data.selectedRec  = {userId:'NEW'};
      this.$data.showSlideOut = true;
    },
    onContinueShopping(){
      console.log("Continue Shopping clicked...")
    },
  },
  mounted(){
    this.getData();
  },
  components: {
    UserDetails,
    VueSlideoutPanel
  },
}
*/
</script>
<style lang="scss">
@import "~@/assets/styles/_vars.scss";
.sw-text{
  line-height:24px;
}
.sw-slideout-head{
  position:absolute;
  top:0;
  left:0;
  width:100%;
  display:flex;
  height:60px;
  padding:16px;
  box-shadow: 0px 0px 8px 2px #ccc;
  background-color: #fff;
  z-index:1;
}
.sw-slideout-body{
  margin-top:92px;
}
</style>
