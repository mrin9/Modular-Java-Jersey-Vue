
import { SideMenu}  from '@/menu/AppMenu';
let defaultExpanded=false;

const manageMenu:SideMenu = {
  type :'full',
  items:[
    {type:"link" , label:'License Management', to:"/home/manage/license"},
    {type:"link" , label:'Firmware Update'  , to:"/home/manage/firmware"},
    {
      type    : "group",
      label   : 'Backup/Restore',
      id      : 'backup_restore',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Manage Backups', to:"/home/manage/manage-backup"},
        { type:"link", label:'Schedule Backups', to:"/home/manage/schedule-backup"},
        { type:"link", label:'FTP Profiles', to:"/home/manage/ftp-profile"},
      ]
    },
    {
      type    : "group",
      label   : 'Policy',
      id      : 'policy',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Filters', to:"/home/manage/policy-filters"},
        { type:"link", label:'Groups', to:"/home/manage/policy-groups"},
      ]
    },
    {
      type    : "group",
      label   : 'Compliance',
      id      : 'compliance',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Dictionaries', to:"/home/manage/dictionaries"},
        { type:"link", label:'Approval Boxes', to:"/home/manage/approval-boxes"},
        { type:"link", label:'Encryption', to:"/home/manage/encryption"},
        { type:"link", label:'Record ID Definitions', to:"/home/manage/rec-id"},
        { type:"link", label:'Archiving', to:"/home/manage/archiving"},
      ]
    },
    {
      type    : "group",
      label   : 'Server',
      id      : 'server',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Administration', to:"/home/manage/admin"},
        { type:"link", label:'LDAP Configuration', to:"/home/manage/admin-ldap"},
        { type:"link", label:'Updates', to:"/home/manage/admin-updates"},
        { type:"link", label:'Monitoring', to:"/home/manage/admin-monitor"},
        { type:"link", label:'Host Configuration', to:"/home/manage/host-config"},
        { type:"link", label:'Advanced', to:"/home/manage/admin-advanced"},
      ]
    },
    {
      type    : "group",
      label   : 'Certificates',
      id      : 'certificates',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Generate/Import', to:"/home/manage/cert-generate"},
        { type:"link", label:'Generate CSR', to:"/home/manage/cert-csr"},
        { type:"link", label:'Configure', to:"/home/manage/cert-config"}
      ]
    },
    {
      type    : "group",
      label   : 'Users, Groups & Organizations',
      id      : 'user_group_org',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Users', to:"/home/manage/users"},
        { type:"link", label:'Groups', to:"/home/manage/groups"},
        { type:"link", label:'Organizations', to:"/home/manage/org"},
      ]
    },
    {
      type    : "group",
      label   : 'Network',
      id      : 'network',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Server Configuration', to:"/home/manage/network-config"},
        { type:"link", label:'MTA Configuration', to:"/home/manage/network-mta"},
        { type:"link", label:'Email Address Rewriting', to:"/home/manage/email-address-rewrite"},
        { type:"link", label:'Trusted Networks', to:"/home/manage/network-trusted"},
      ]
    },
    {
      type    : "group",
      label   : 'Junk Box',
      id      : 'junk_box',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Message Management', to:"/home/manage/junk-msg-management"},
        { type:"link", label:'Summary Notifications', to:"/home/manage/junk-summary-notify"},
      ]
    },
    {
      type    : "group",
      label   : 'Anti-Spam',
      id      : 'anti_spam',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Spam Management', to:"/home/manage/spam-management"},
        { type:"link", label:'Address Books', to:"/home/manage/spam-address"},
        { type:"link", label:'Anti-Spam Aggressiveness', to:"/home/manage/spam-aggressive"},
        { type:"link", label:'Languages', to:"/home/manage/spam-lang"},
        { type:"link", label:'Black List Services', to:"/home/manage/spam-black-list"},
        { type:"link", label:'Spam Submissions', to:"/home/manage/spam-submit"},
      ]
    },
    {type:"link" , label:'Anti-Spoofing', to:"/home/manage/spoofing"},
    {type:"link" , label:'Anti-Phishing', to:"/home/manage/phishing"},
    {type:"link" , label:'Anti-Virus', to:"/home/manage/virus"},
    {
      type    : "group",
      label   : 'Capture Services',
      id      : 'capture_services',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Attachment Scan'   , to:"/home/manage/capture-attachment"},
        { type:"link", label:'Real time URL Scan', to:"/home/manage/capture-url"},
      ]
    },
    {type:"link" , label:'Encryption Service', to:"/home/manage/encryption-service"},
    {type:"link" , label:'Connection Management', to:"/home/manage/conn-mgmt"},
    {
      type    : "group",
      label   : 'Reporting',
      id      : 'reporting',
      expanded: defaultExpanded,
      items   : [
        { type:"link", label:'Configure Known Networks', to:"/home/manage/report-networks"},
        { type:"link", label:'Scheduled Reports', to:"/home/manage/report-schedule"},
      ]
    },
  ]
}
export default manageMenu;