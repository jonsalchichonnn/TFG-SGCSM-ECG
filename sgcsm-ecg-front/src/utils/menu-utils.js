// menu info retriver utils

import axios from "axios";
import Login from "@/views/Login";
import Home from "@/components/HomePage";
import Profile from "@/views/ProfileView";
// router 路由； store Vuex
export const initMenu = (router, store) => {
    console.log("store.state.routes.routes.length = ", store.state.routes.routes.length)
    // console.log("JSON.parse(sessionStorage.getItem(\"menu\") = ", JSON.parse(sessionStorage.getItem("menu")))
    // 如果有数据，初始化路由菜单
    if (store.state.routes.routes.length > 0) { //|| JSON.parse(sessionStorage.getItem("menu"))
        return;
    }
    sessionStorage.setItem("menu", true)
    const curUserRole = JSON.parse(sessionStorage.getItem("currentUser")).role;
    console.log("curUserRole = ", curUserRole)
    axios.get(`/menu?role=${curUserRole}`).then(res => res.data).then(res => {
        console.log(res)
        // 如果数据存在 格式化路由
        if (res.data) {
            resetRoutes(router)
            // 格式化好路由
            let fmtRoutes = formatRoutes(router, res.data)

            console.log("dsp fuera formatRoute")
            console.log(router.options.routes)
            //reset antes de añadir?
            // 添加到 router
            // resetRouter()
            router.addRoutes(fmtRoutes)
            // // 将数据存入 Vuex

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
    console.log("dentro format DSP AÑADIR")
    console.log(fmtRoutes)
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