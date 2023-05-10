// menu info retriver utils

import axios from "axios";
// import {resetRouter} from "@/router";
// router 路由； store Vuex
export const initMenu = (router, store) => {
    console.log("store.state.routes.routes.length = ", store.state.routes.routes.length  )
    // 如果有数据，初始化路由菜单
    if (store.state.routes.routes.length > 0 || JSON.parse(sessionStorage.getItem("menu"))) {
        return;
    }
    sessionStorage.setItem("menu", true)
    const curUserRole = JSON.parse(sessionStorage.getItem("currentUser")).role;
    axios.get(`/menu?role=${curUserRole}`).then(res => res.data).then(res => {
        console.log(res)
        // 如果数据存在 格式化路由
        if (res.data) {
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
            routes.forEach(router => {
                let {
                    menuName,
                    menuPath,
                    menuComponent,
                    menuIcon,
                } = router;
                // // 如果有 children 并且类型是数组
                // if (children && children instanceof Array) {
                //     // 递归
                //     children = formatRoutes(children)
                // }
                // 单独对某一个路由格式化 component
                let fmRouter = {
                    path: "/" + menuPath,
                    name: menuName,
                    meta: {
                        title: menuName
                    },
                    icon: menuIcon,
                    component(resolve) {
                        require(['@/views/' + menuComponent + '.vue'], resolve);
                        // 判断组件以什么开头，到对应的目录去找
                        // if (menuComponent.startsWith('Home')) {
                        //     require(['@/views/' + menuComponent + '.vue'], resolve);
                        // }else if (menuComponent.startsWith('Emp')) {
                        //     require(['@/views/emp/' + menuComponent + '.vue'], resolve);
                        // }else if (menuComponent.startsWith('Per')) {
                        //     require(['@/views/per/' + menuComponent + '.vue'], resolve);
                        // }else if (menuComponent.startsWith('Sal')) {
                        //     require(['@/views/sal/' + menuComponent + '.vue'], resolve);
                        // }else if (menuComponent.startsWith('Sta')) {
                        //     require(['@/views/sta/' + menuComponent + '.vue'], resolve);
                        // }else if (menuComponent.startsWith('Sys')) {
                        //     require(['@/views/sys/' + menuComponent + '.vue'], resolve);
                        // }
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