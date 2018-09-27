
import { SideMenu}  from '@/menu/AppMenu';

const sampleMenu:SideMenu =  {
  type :'full',
  items:[
    {
      type    : "group",
      label   : 'Components',
      id      : 'components',
      expanded: false,
      items   : [
        { type:"link", label:'Buttons', to:"/home/components/button"},
        { type:"link", label:'Form Inputs', to:"/home/components/form"},
        { type:"link", label:'Tabs', to:"/home/components/tab"},
        { type:"link", label:'Messages & Notification', to:"/home/components/notify"},
        { type:"link", label:'Dropdowns', to:"/home/components/dd"},
      ]
    },
    {
      type    : "group",
      label   : 'Sonicwall Componets',
      id      : 'sw_components',
      expanded: false,
      items: [
        { type:"link", label:'Header'  , to:"/home/components/header"},
        { type:"link", label:'Side Nav', to:"/home/components/sidenav"},
        { type:"link", label:'Logo'    , to:"/home/components/logo"},
      ]
    },
    {type:"link" , label:'Typography', to:"/home/components/font"},
    {
      type    : "group",
      id      : 'data',
      label   : 'Data',
      expanded: false,
      items   : [
        { type:"link", label:'Data Tables', to:"/home/components/table"}
      ]
    }
  ]
}
export default sampleMenu;