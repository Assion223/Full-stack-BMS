<!-- 加上router即会跳转 -->
<template>
    <el-menu
        :default-openeds="['1', '2']"
        style="min-height:100vh; overflow-x: hidden;"
        background-color="rgb(48,65,86)"
        text-color="#fff"
        :default-active="$route.path"
        active-text-color="#ffd04b"
        :collapase-transition="true"
        :collapse="isCollapse"
        router
    >
        <div style="height: 60px; line-height: 60px; text-align: center;">
            <img
                src="../assets/logo.png"
                alt
                style="width: 20px; vertical-align: middle; margin-right: 6px;"
            />
            <b style="color: white;" v-show="!isCollapse">后台管理系统</b>
        </div>

        <el-menu-item index="/home">
            <i class="el-icon-house"></i>
            <span slot="title">主页</span>
        </el-menu-item>
        <el-submenu index="2">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">系统管理</span>
            </template>
            <el-menu-item-group>
                <el-menu-item index="/user">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">用户管理</span>
                </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
                <el-menu-item index="/files">
                    <i class="el-icon-document"></i>
                    <span slot="title">文件管理</span>
                </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
                <el-menu-item index="/role">
                    <i class="el-icon-user-solid"></i>
                    <span slot="title">角色管理</span>
                </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
                <el-menu-item index="/menu">
                    <i class="el-icon-menu"></i>
                    <span slot="title">菜单管理</span>
                </el-menu-item>
            </el-menu-item-group>
        </el-submenu>
    </el-menu>
</template>
<script>
export default {
    name: "Aside",
    props: {
        isCollapse: Boolean,
    },
};
</script>
<style>
.el-menu--collapse span {
    visibility: hidden;
}
</style>