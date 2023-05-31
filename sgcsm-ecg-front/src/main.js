import Vue from "vue";
import App from "./App.vue";
import ElementUI from "element-ui";
import locale from 'element-ui/lib/locale/lang/en'
import VueRouter from "vue-router";
import "element-ui/lib/theme-chalk/index.css";
import "./assets/global.css";
import store from "@/store";
import axios from "axios";
import router from "./router";
import {initMenu} from "@/utils/menu-utils";
import {downloadRequest} from "@/utils/download-utils";

Vue.config.productionTip = false;
// axios configuration
Vue.prototype.$axios = axios;
axios.defaults.baseURL = "http://localhost:8090";
Vue.prototype.downloadRequest = downloadRequest;

Vue.use(ElementUI, {locale});
Vue.use(VueRouter);

// router guard
router.beforeEach((to, from, next) => {
    const curUser = JSON.parse(sessionStorage.getItem("currentUser"));
    // if curUserAccount != null => user logged
    if (to.name !== 'login' && !curUser) {
        // if not logged redirect to log in view
        next({name: 'login'});
        next()
    } else if(curUser){
        initMenu(router, store)
        next()
    }else{
        next()
    }
})


new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount("#app");
