import Vue from 'vue'
import Vuex from 'vuex'
// import tab from './tab'
import routes from "@/store/routes";
import tab from "@/store/tab";
import currentUser from "@/store/currentuser";

Vue.use(Vuex)

export default new Vuex.Store({
    modules:{
        tab,
        routes,
        currentUser
    }
})