
import { SideMenu}  from '@/menu/AppMenu';

const userMenu:SideMenu =  {
  type :'full',
  items:[
    {type:"link" , label:'Dashboard', to:"/home/user/dashboard"},
    {type:"link" , label:'Junk Box', to:"/home/user/junkbox"},
    {type:"link" , label:'Downloads', to:"/home/user/download"},
    {type:"link" , label:'Change Password', to:"/home/user/password"},
    {type:"link" , label:'Deligate', to:"/home/user/delegate"},
    {
      type    : "group",
      label   : 'Settings',
      id      : 'settings',
      expanded: true,
      items   : [
        { type:"link", label:'Junk Summary Notifications', to:"/home/user/junk-notification"},
        { type:"link", label:'Spam Management', to:"/home/spam-settings"},
        { type:"link", label:'Anti-Spam Aggressiveness', to:"/home/spam-aggressiveness"},
        { type:"link", label:'Languages', to:"/home/spam-language"},
        { type:"link", label:'Address Books', to:"/home/user/address-books"},
      ]
    }
  ]
}
export default userMenu;