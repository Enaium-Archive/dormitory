import {createApp} from 'vue'
import App from '@/App.vue'
import router from "@/router";
import naive from "@/util/naive";

createApp(App).use(router).use(naive).mount('#app')
