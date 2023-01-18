/*
 * Copyright (c) 2023 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import {createRouter, createWebHistory} from "vue-router";
import Login from "@/pages/Login.vue";
import AbsentRecord from "@/layouts/admin/pages/AbsentRecord.vue";
import AbsentRegister from "@/layouts/admin/pages/AbsentRegister.vue";
import AdminManager from "@/layouts/system/pages/AdminManager.vue";
import Admin from "@/layouts/admin/Admin.vue";
import System from "@/layouts/system/System.vue";
import BuildingManager from "@/layouts/system/pages/BuildingManager.vue";
import DormitoryManager from "@/layouts/system/pages/DormitoryManager.vue";
import MoveOutRecord from "@/layouts/system/pages/MoveOutRecord.vue";
import StudentManager from "@/layouts/system/pages/StudentManager.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/login",
            name: "login",
            component: Login
        },
        {
            path: "/admin",
            name: "admin",
            component: Admin,
            redirect: "/admin/absent-record",
            children: [
                {
                    path: "absent-record",
                    name: "absent-record",
                    component: AbsentRecord
                },
                {
                    path: "absent-register",
                    name: "absent-register",
                    component: AbsentRegister
                }
            ]
        },
        {
            path: "/system",
            name: "system",
            component: System,
            redirect: "/system/admin-manager",
            children: [
                {
                    path: "admin-manager",
                    name: "admin-manager",
                    component: AdminManager
                },
                {
                    path: "building-manager",
                    name: "building-manager",
                    component: BuildingManager
                },
                {
                    path: "dormitory-manager",
                    name: "dormitory-manager",
                    component: DormitoryManager
                },
                {
                    path: "move-out-record",
                    name: "move-out-record",
                    component: MoveOutRecord
                },
                {
                    path: "student-manager",
                    name: "student-manager",
                    component: StudentManager
                }
            ]
        },
    ]
})

export default router