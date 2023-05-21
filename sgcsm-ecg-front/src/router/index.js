import VueRouter from "vue-router";
import Login from "@/views/Login";
import Home from "@/components/HomePage";
import Profile from "@/views/ProfileView";

const defaultRoutes = [
    {
        path: "/login",
        name: "login",
        component: Login,
    },
    {
        path: "/",
        name: "base",
        component: Home,
        redirect: 'welcome',
        children: [
            {
                path: "/profile",
                name: "profile",
                component: Profile,
            },
        ]
    }]
const router = new VueRouter({
    mode: "history",
    // clone array
    routes: [...defaultRoutes],
});

export function resetRouter() {
    // router.matcher = new VueRouter({
    //     mode: 'history',
    //     routes: []
    // }).matcher
    router.options.routes = defaultRoutes
}

export default router;
