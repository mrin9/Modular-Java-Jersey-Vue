
export default {
  "USER":[
    { id:'0',  label:'My Profile', icon:'user', to:"/home/profile"},
    { id:'1',  label:'My Orders' , icon:'shopping-bag', to:"/home/orders"},
    { id:'2',  label:'My Cart'   , icon:'shopping-cart', to:"/home/cart"}
  ],
  "SUPPORT": [
    { id:'0',  label:'My Profile', icon:'user', to:"/home/profile"},
    { id:'1',  label:'Manage'    , icon:'wrench', to:"/home/manage", sideMenu:[]},
  ],
  "ADMIN":[
    { id:'0',  label:'Dashboard', icon:'line-chart', to:"/home/profile"},
    { id:'1',  label:'Manage'   , icon:'wrench', to:"/home/manage", sideMenu:[]},
  ]
};


