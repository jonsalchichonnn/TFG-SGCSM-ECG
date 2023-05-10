import VueRouter from "vue-router";
import Login from "@/views/Login";
import Home from "@/components/HomePage";
// import Welcome from "@/views/Welcome";

const router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: "/login",
            name: "login",
            component: Login,
        },
        {
            path: "/",
            name: "base",
            component: Home,
            redirect:'welcome',
            children: [
            //     {
            //         path: '/',
            //         name: 'welcome',
            //         meta: {
            //             title: 'Welcome'
            //         },
            //         component: Welcome
            //     },
             ]
        },
    ],
});

export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}

export default router;
