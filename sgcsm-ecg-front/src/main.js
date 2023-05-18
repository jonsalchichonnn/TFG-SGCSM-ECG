import Vue from "vue";
import App from "./App.vue";
import ElementUI from "element-ui";
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

Vue.use(ElementUI);
Vue.use(VueRouter);

// router guard
router.beforeEach((to, from, next) => {
    console.log("to")
    console.log(to.name)
    // to 要去的路由; from 来自哪里的路由 ; next() 放行
    // 用户登录成功时，把 token 存入 sessionStorage，如果携带 token，初始化菜单，放行
    // const curUserAccount = JSON.parse(sessionStorage.getItem("currentUser")).account;
    const curUser = JSON.parse(sessionStorage.getItem("currentUser"));
    // console.log("curUser")
    // console.log(curUser)
    // console.log(to.name !== 'login' && !curUser)
    // if curUserAccount != null => user logged
    if (to.name !== 'login' && !curUser) {

        // 如果用户不存在
        // if (!sessionStorage.getItem('user')) {
        //     // 判断用户信息是否存在
        //     return getRequest('/admin/info').then(resp => {
        //         if (resp) {
        //             // 存入用户信息，转字符串，存入 sessionStorage
        //             window.sessionStorage.setItem('user', JSON.stringify(resp))
        //             // 同步用户信息 编辑用户
        //             store.commit('INIT_ADMIN', resp)
        //             next();
        //         }
        //     })
        // }

        // if not logged redirect to log in view
        next({name: 'login'});
        // next('/?redirect=', to.path);
        next()
    } else if(curUser){
        initMenu(router, store)
        // if (to.path === '/') {
        //     next()
        // } else {//如果用户未登录，要跳转到指定的路由
        //     next('/?redirect=' + to.path)
        // }
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
