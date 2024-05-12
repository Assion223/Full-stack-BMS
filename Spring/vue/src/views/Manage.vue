<template>
    <el-container style="height: 100%">
        <!-- 侧边栏 -->
        <el-aside
            :width="sideWith+'px'"
            style="background-color: rgb(238, 241, 246); height: 100%; box-shadow: 2px 0 6px rgba(0, 21, 41,35%);"
        >
            <Aside :isCollapse="isCollapse"/>
        </el-aside>

        <el-container height="100%">
            <!-- 头顶 -->
            <el-header style="border-bottom:1px solid #ccc; ">
                <Header :CollapseBtnClass="CollapseBtnClass" :collapse="collapse"></Header>
            </el-header>
        
            <!-- 条件筛选 -->
            <el-main>
                <!-- 展示子路由 -->
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>

import { Collapse } from "element-ui";
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
// @ is an alias to /src

export default {
    name: "Manage",
    components: {
        Aside,
        Header
    },
    data() {
        return {
            CollapseBtnClass: "el-icon-s-fold",
            isCollapse: false,
            sideWith: 200,
        };
    },
    methods: {
        collapse() {
            this.isCollapse = !this.isCollapse;
            if (this.isCollapse) {
                this.sideWith = 64;
                this.CollapseBtnClass = "el-icon-s-fold";
            } else {
                this.sideWith = 200;
                this.CollapseBtnClass = "el-icon-s-unfold";
            }
        }
    },
    created() {
    },
};
</script>
<style>
.bColor {
    background-color: #fff !important;
}
</style>
