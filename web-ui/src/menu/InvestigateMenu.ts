
import { SideMenu}  from '@/menu/AppMenu';

const investigateMenu:SideMenu = {
  type :'full',
  items:[
    {type:"link" , label:'Junk Box', to:"/home/investigate/junkbox"},
    {
      type    : "group",
      label   : 'Logs',
      id      : 'logs',
      expanded: true,
      items   : [
        { type:"link", label:'Message Logs', to:"/home/investigate/message-logs"},
        { type:"link", label:'Connection Logs', to:"/home/investigate/connection-logs"},
        { type:"link", label:'Capture ATP Logs', to:"/home/investigate/capture-atp-logs"},
      ]
    },
    {
      type    : "group",
      label   : 'Tools',
      id      : 'tools',
      expanded: true,
      items   : [
        { type:"link", label:'Run DMARC Report', to:"/home/investigate/dmarc"},
        { type:"link", label:'Audit Trail', to:"/home/investigate/audit-trail"},
        { type:"link", label:'Diagnostics', to:"/home/investigate/diag"},
      ]
    }
  ]
}
export default investigateMenu;
