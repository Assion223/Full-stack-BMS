<template>
    <div style="display: flex;font-size: 12px; line-height:60px; ">
        <div style="flex: 1;  ">
            <span
                :class="CollapseBtnClass"
                style="cursor: pointer; font-size: 18px; "
                @click="collapse"
            ></span>

            <!-- 面包屑 -->
            <el-breadcrumb separator="/" style=" display: inline-block; margin-left: 10px;">
                <el-breadcrumb-item style="cursor: pointer;" @click.native="toHome">
                    <span style="color: #409EFF;">首页</span>
                </el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-dropdown style="width: 150px; cursor: pointer; text-align: right;" trigger="click">
            <img
                alt
                style="vertical-align: middle;border-radius: 50%; width: 30px;height: 30px;"
                :src="user.avatorUrl"
            />
            <span>
                {{ user.nickname }}
                <i
                    class="el-icon-arrow-down"
                    style="margin-left: 5px; margin-right: 15px;"
                ></i>
            </span>

            <el-dropdown-menu
                slot="dropdown"
                style="font-size: 18px; width: 120px; text-align: center;"
            >
                <el-dropdown-item>
                    <span @click="goPerson">个人信息</span>
                </el-dropdown-item>
                <el-dropdown-item>
                    <span
                        style="text-decoration: none;color: rgb(96, 98, 102);"
                        @click="logout"
                    >退出登录</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>
<script>
export default {
    name: "Header",
    props: {
        collapse: Function,
        CollapseBtnClass: String,
    },
    data() {
        return {
            user:
                localStorage.getItem("user") != null
                    ? JSON.parse(localStorage.getItem("user"))[0]
                    : {},
        };
    },

    beforeMount() {
        this.currentPathName = localStorage.getItem("currentPathName");
    },
    computed: {
        currentPathName: {
            get() {
                return this.$store.state.currentPathName;
            },
            set(val) {},
        },
    },
    methods: {
        toHome() {
            this.$router.push("/home").catch((err) => {});
        },
        saveAvator(e) {
            this.user.avatorUrl = e;
        },
        ret() {
            this.$router.push("/login").catch((err) => {});
        },
        logout() {
            this.$router.push("/login").catch((err) => {});
            localStorage.removeItem("user");
            this.$message.success("退出成功");
        },
        goPerson() {
            this.$router.push("/person").catch((err) => {});
        },
    },
    mounted() {
        //页面元素渲染后再触发
        this.$bus.$on("saveAvator", this.saveAvator);
    },
};
</script>