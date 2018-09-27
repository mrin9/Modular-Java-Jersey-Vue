import { SideMenu}  from '@/menu/AppMenu';


const monitorMenu:SideMenu =  {
  type :'none',
  to   :'/home/monitor/dashboard',
  items:[
    {type:"link" , label:'Dashboard', to:"/home/monitor/dashboard"},
    {
      type    : "group",
      label   : 'Event Summaries',
      id      : 'monitor_event_summary',
      expanded: true,
      items   : [
        { type:"link", label:'All Event Connections', to:"/home/monitor/connections"},
        { type:"link", label:'Anti-Spam'            , to:"/home/monitor/spam"},
        { type:"link", label:'Anti-Phishing'        , to:"/home/monitor/phishing"},
        { type:"link", label:'Anti-Virus & Notification', to:"/home/monitor/virus"},
        { type:"link", label:'Anti-Spoof'           , to:"/home/monitor/spoof"},
        { type:"link", label:'Directory Harvest'    , to:"/home/monitor/dirctory-harvest"},
        { type:"link", label:'Capture ATP'          , to:"/home/monitor/capture"},
      ]
    },
    {
      type    : "group",
      label   : 'Policy & Compliance',
      id      : 'monitor_policy',
      expanded: true,
      items: [
        { type:"link", label:'Policy'     , to:"/home/monitor/policy"},
        { type:"link", label:'Compliance' , to:"/home/monitor/compliance"},
        { type:"link", label:'Encryption' , to:"/home/monitor/encryption"},
      ]
    },
    {
      type    : "group",
      id      : 'monitor_appliance_health',
      label   : 'Appliance Health',
      expanded: true,
      items   : [
        { type:"link", label:'Live Monitor'       , to:"/home/monitor/live-monitor"},
        { type:"link", label:'Performance Metrics', to:"/home/monitor/performance"},
        { type:"link", label:'LDAP Users'         , to:"/home/monitor/ldap-users"},
      ]
    },
    {
      type    : "group",
      id      : 'monitor_current_status',
      label   : 'Current Status',
      expanded: true,
      items   : [
        { type:"link", label:'System Status', to:"/home/monitor/system-status"},
        { type:"link", label:'MTA Status'   , to:"/home/monitor/mta-status"}
      ]
    }
  ]
}
export default monitorMenu;