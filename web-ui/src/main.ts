import { createApp } from 'vue';

// Localization
import { createI18n } from 'vue-i18n';

// prime Vue
import PrimeVue from 'primevue/config';
import 'primevue/resources/themes/saga-blue/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

// Prime Components
import Button from 'primevue/button';
import InputText from 'primevue/inputtext';
import InputNumber from 'primevue/inputnumber';
import Password from 'primevue/password';
import Dropdown from 'primevue/dropdown';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Dialog from 'primevue/dialog';
import ConfirmDialog from 'primevue/confirmdialog';
import ConfirmPopup from 'primevue/confirmpopup';
import Toast from 'primevue/toast';
import Message from 'primevue/message';
import ToastService from 'primevue/toastservice';
import ConfirmationService from 'primevue/confirmationservice';
import Sidebar from 'primevue/sidebar';
import BlockUI from 'primevue/blockui';
import SelectButton from 'primevue/selectbutton';
import Menu from 'primevue/menu';
import Chart from 'primevue/chart';

// Event Bus
import mitt from 'mitt';

// App, Store and Router
import router from './router';
import store from './store';
import App from './App.vue';

// Setup Locale translations
const messages = {
  en: { message: { hello: 'hello world' } },
  ja: { message: { hello: 'こんにちは、世界' } },
};

const i18n = createI18n({
  locale: 'ja',
  fallbackLocale: 'en',
  messages,
});

const AppEvent = mitt();

const app = createApp(App)
  .use(i18n)
  .use(PrimeVue, { ripple: true })
  .use(ToastService)
  .use(ConfirmationService)
  .use(store)
  .use(router);

app.component('Button', Button);
app.component('InputText', InputText);
app.component('InputNumber', InputNumber);
app.component('Password', Password);
app.component('Dropdown', Dropdown);
app.component('SelectButton', SelectButton);
app.component('DataTable', DataTable);
app.component('Column', Column);
app.component('Dialog', Dialog);
app.component('ConfirmDialog', ConfirmDialog);
app.component('ConfirmPopup', ConfirmPopup);
app.component('Toast', Toast);
app.component('Message', Message);
app.component('Sidebar', Sidebar);
app.component('BlockUI', BlockUI);
app.component('Menu', Menu);
app.component('Chart', Chart);

// app.config.globalProperties.$AppEvent = AppEvent;
app.mount('#app');
app.provide('AppEvent', AppEvent);
// eslint-disable-next-line import/prefer-default-export
export { AppEvent };
