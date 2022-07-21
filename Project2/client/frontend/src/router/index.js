import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

import App from "@/App";
import Home from "@/views/Home";
import Staff from "@/components/Staff";
import Login from "@/views/Login";
import Center from "@/components/Center";
import Enterprise from "@/components/Enterprise";
import Model from "@/components/Model";
import Inventory from "@/components/Inventory";
import Center1 from "@/components/Center1";
import Contract from "@/components/Contract";
import Orders from "@/components/Orders";
import Bill from "@/components/Bill";
import staffHome from "@/views/staffHome";
import bossHome from "@/views/bossHome";
const routes = [

    {  path: '/',
    redirect:'/login'},


    {
        path: '/bossHome',
        component: bossHome,
        show: true,
        district:'/',
        meta: {
            requireAuth: true
        },
        children: [
            {
                // show: true,
                path: '/boss_staff',
                component: Staff,
                name: 'staff',

            },
            {
                // show: true,
                path: '/boss_center',
                component: Center,
                name: 'center'
            },
            {
                // show: true,
                path: '/boss_enterprise',
                component: Enterprise,
                name: 'enterprise'
            },
            {
                // show: true,
                path: '/boss_model',
                component: Model,
                name: 'model'
            },
            {
                // show: true,
                path: '/boss_inventory',
                component: Inventory,
                name: 'inventory'
            },
            {
                // show: true,
                path: '/boss_contract',
                component: Contract,
                name: 'contract'
            },
            {
                // show: true,
                path: '/boss_orders',
                component: Orders,
                name: 'orders'
            },
            {
                // show: true,
                path: '/boss_bill',
                component: Bill,
                name: 'bill'
            },



        ]
    }, {
        path: '/staffHome',
        component: staffHome,
        show: true,
        district:'/',
        meta: {
            requireAuth: true
        },
        children: [
            {
                // show: true,
                path: '/staff_staff',
                component: Staff,
                name: 'staff'
            },
            {
                // show: true,
                path: '/staff_center',
                component: Center,
                name: 'center'
            },
            {
                // show: true,
                path: '/staff_enterprise',
                component: Enterprise,
                name: 'enterprise'
            },
            {
                // show: true,
                path: '/staff_model',
                component: Model,
                name: 'model'
            },
            {
                // show: true,
                path: '/staff_inventory',
                component: Inventory,
                name: 'inventory'
            },
            {
                // show: true,
                path: '/staff_contract',
                component: Contract,
                name: 'contract'
            },
            {
                // show: true,
                path: '/staff_orders',
                component: Orders,
                name: 'orders'
            }



        ]
    },
    {
        path: '/login',
        component: Login,
        show: true,
        children: [

        ]
    }




]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,

})

export default router
//登录拦截
router.beforeEach((to, from, next) => {
    var num = sessionStorage.getItem("isLogin");
    console.log(num)
    if (num !=1&&to.meta.requireAuth) {  // 判断该路由是否需要登录权限
        //如果需要就执行下面的代码


        // 通过sessionStorage 获取当前的isLogin的值 将我们保存的isLogin的值赋给num,num是顺便取的名称，可以换
        //我们在登录界面，我们使用请求，请求成功后，我们就使用sessionStorage为‘isLogin’保存一个值  1，如果请求失败，就不保存‘isLogin’的值
        //如果请求成功，num的值就是1，请求失败就是null，所以下面进行判断

        next({
            path: '/login',//返回登录界面
            // query: {redirect: to.fullPath}
        })

    } else {
        //如果不需要登录权限就直接跳转到该路由
        next();
    }
})