// menu info retriever utils

import axios from "axios";
import Login from "@/views/Login";
import Home from "@/components/HomePage";
import Profile from "@/views/ProfileView";
// router store Vuex
export const initMenu = (router, store) => {
    console.log("store.state.routes.routes.length = ", store.state.routes.routes.length)
    // if no data, initialize routes
    if (store.state.routes.routes.length > 0) {
        return;
    }
    sessionStorage.setItem("menu", true)
    const curUserRole = JSON.parse(sessionStorage.getItem("currentUser")).role;
    axios.get(`/menu?role=${curUserRole}`).then(res => res.data).then(res => {
        if (res.data) {
            resetRoutes(router)
            let fmtRoutes = formatRoutes(router, res.data)

            router.addRoutes(fmtRoutes)

            // store data into Vuex
            store.commit('initRoutes', fmtRoutes)
        }
    })
}

export const formatRoutes = (router, routes) => {
    let fmtRoutes = router.options.routes
    console.log("dentro format ANTES AÑADIR")
    console.log(fmtRoutes)
    fmtRoutes.forEach(routeItem => {
        if (routeItem.name === "base") {
            routes.forEach(r => {
                let {
                    menuName,
                    menuPath,
                    menuComponent,
                    menuIcon,
                } = r;
                // if (children && children instanceof Array) {
                //     // 递归
                //     children = formatRoutes(children)
                // }
                // get specific component
                let fmRouter = {
                    path: "/" + menuPath,
                    name: menuName,
                    meta: {
                        title: menuName
                    },
                    icon: menuIcon,
                    component(resolve) {
                        require(['@/views/' + menuComponent + '.vue'], resolve);
                    }
                };
                routeItem.children.push(fmRouter);
            })
        }
    });
    return fmtRoutes
}

export const resetRoutes = (router) => {
    router.options.routes =
        [
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
                children: [{
                    path: "/profile",
                    name: "profile",
                    component: Profile,
                },]
            }]
}