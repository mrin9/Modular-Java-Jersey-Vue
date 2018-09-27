
import lodash from 'lodash';
import store from '@/store/index'

import monitorMenu  from '@/menu/MonitorMenu';
import investigateMenu  from '@/menu/InvestigateMenu';
import manageMenu  from '@/menu/ManageMenu';
import userMenu  from '@/menu/UserMenu';
import sampleMenu  from '@/menu/SampleMenu';


export interface HeaderItem {
  id      : string,
  label   : string,
  icon    : string,
  to      : string,
  permit  :string[],
  sideMenu:SideMenu
}

export type SideMenuType = "full" | "none";
export interface SideMenu {
  type:SideMenuType,
  to?:string,
  items:any[]
}


//Header Items
const monitor:HeaderItem ={
  id:'0',  
  label:'Monitor', 
  icon:'line-chart',
  to:"/home/monitor/dashboard",
  permit:['admin','ouadmin'],
  sideMenu:monitorMenu
};

const investigate:HeaderItem = {
  id:'1',  
  label:'Investigate', 
  icon:'list',
  to:"/home/investigate/junkbox",
  permit:['admin','ouadmin'],
  sideMenu:investigateMenu
};

const manage:HeaderItem = {
  id:'2',  
  label:'Manage', 
  icon:'cog',
  to:"/home/manage/license",
  permit:['admin','ouadmin'],
  sideMenu:manageMenu
};

const user:HeaderItem = {
  id:'3',  
  label:'User', 
  icon:'user',
  to:"/home/user/junkbox",
  permit:['user'],
  sideMenu:userMenu
};

const sample:HeaderItem = {
  id:'4',  
  label:'Components', 
  icon:'objects',
  to:"/home/components/button",
  permit:['user','admin','ouadmin'],
  sideMenu:sampleMenu
};

const items:HeaderItem[] = [
  monitor,
  investigate,
  manage,
  user,
  //sample
];

export interface AppMenuUtil {
  search:Function,
  getHeaderItems:Function,
  items:HeaderItem[]
}

const appMenuUtil:AppMenuUtil={
  items:items,
  /*
  * @param {String} value    Search String.
  * @param {String} searchBy 'group' or 'route'.
  */
  search:function(value:string, searchBy:string){
    let currHeaderItem:HeaderItem|undefined;
    let currMenuGroup:any;
    let roleHeaderItems = this.getHeaderItems();
    //Iterate through the Header items
    loop1:
    for (let i = 0; i < roleHeaderItems.length; i++){

      //iterate through sidemenu of each header items
      currHeaderItem = roleHeaderItems[i];
      if ( currHeaderItem !=undefined){
        if (currHeaderItem.sideMenu.type ==="full" && Array.isArray(currHeaderItem.sideMenu.items)){
          for (let j = 0; j < currHeaderItem.sideMenu.items.length; j++){
            //iterate through the links inside the group
            if (currHeaderItem.sideMenu.items[j].type==="group"){
              currMenuGroup = currHeaderItem.sideMenu.items[j];

              if (searchBy === "route") {
                for (let k = 0; k < currMenuGroup.items.length; k++){
                  if (currMenuGroup.items[k].to===value){
                    currMenuGroup.expanded=true;
                    break loop1;
                  }
                }
              }
              else{
                if (currMenuGroup.id===value){
                  break loop1;
                }
              }
            }
            else if (searchBy === "route" && currHeaderItem.sideMenu.items[j].type==="link" && currHeaderItem.sideMenu.items[j].to === value){
              currMenuGroup="";
              break loop1;
            }
          } // End of For
        }
        else if (currHeaderItem.sideMenu.type ==="none" && currHeaderItem.sideMenu.to === value){
            break loop1;
        }
      }
    }
    
    return {headerItem:currHeaderItem, menuGroup:currMenuGroup};
  },

  //Get Header Item applicable to logged in user role \
  getHeaderItems:function(){
    let roleHeaderItems =[];
    if (lodash.isEmpty(store.state.user) || lodash.isEmpty(store.state.role) || lodash.isEmpty(store.state.jwt)){
      return [];
    }
    for (let i=0; i < items.length; i++){
      if (items[i].permit.indexOf(store.state.role) >=0 ){
        roleHeaderItems.push(items[i]);
      }
    }
    return roleHeaderItems;
  }
}

export default appMenuUtil;
