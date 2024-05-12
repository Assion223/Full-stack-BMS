<template>
    <div class="wrapper">
        <div class="container">
            <div class="te">
                <h1 style="margin-bottom: 20px;">后台管理系统</h1>
            </div>
            <el-form :rules="rules" :model="user" ref="user" class="form">
                <el-form-item prop="username">
                    <div class="inputs user">
                        账号：
                        <el-input
                            type="text"
                            prefix-icon="el-icon-user"
                            v-model="user.username"
                            size="medium"
                            
                        />
                    </div>
                </el-form-item>
                <el-form-item prop="password">
                    <div class="inputs user">
                        密码：
                        <el-input
                            type="text"
                            prefix-icon="el-icon-lock"
                            show-password
                            v-model="user.password"
                            size="medium"
                            
                        />
                    </div>
                </el-form-item>
                
                <el-form-item>
                    <div class="butn">
                        <el-button
                            style="margin-right: 40px; margin-left: 30px;"
                            type="warning"
                            size="medium"
                            @click="$router.push('/registor')"
                        >注册</el-button>
                        <el-button type="primary" size="medium" @click="login('user')">登录</el-button>
                    </div>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import request from "@/utils/request";
import { defineComponent } from "@vue/composition-api";

export default {
    name: "Login",
    data() {
        return {
            user: {
                username: "admin",
                password: "admin",
            },
            rules: {
                username: [
                    {
                        required: true,
                        message: "请输入用户名",
                        trigger: "blur",
                    },
                    {
                        min: 1,
                        max: 10,
                        message: "长度在 1 到 10 个字符",
                        trigger: "blur",
                    },
                ],
                password: [
                    {
                        required: true,
                        message: "请输入密码",
                        trigger: "blur",
                    },
                    {
                        min: 1,
                        max: 18,
                        message: "长度在 1 到 18 个字符",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    methods: {
        login(user) {
            /*if(this.user.username==null || this.user.password==null){
                this.$message.error("用户名或密码不能为空")
                return 
            }*/
            this.$refs[user].validate((valid) => {
                if (valid) {
                    request.post("/user/login", this.user).then((res) => {
                        if (res.code != 200) {
                            this.$message.error(res.msg);
                        } else {
                            this.$message.success("登录成功");
                            localStorage.setItem(
                                "user",
                                JSON.stringify(res.data)
                            );
                            this.$router.push("/home");
                        }
                    });
                } else {
                    this.$message.error("用户名或密码不符合规范");
                    return false;
                }
            });
        },
    },
};
</script>
<style>
.wrapper {
    overflow: hidden;
    width: 100%;
    height: 100vh;
    background: no-repeat center url("../assets/1666758601547.png");
    background-size: cover;
    padding: 0;
    margin: 0;
    box-sizing: border-box;
    display: flex;
    justify-content: center;
    align-items: center;
}
.container {
    width: 40%;
    height: 40%;
    background-color: rgba(255, 255, 255, 0.35);
    backdrop-filter: blur(6px);
    -webkit-backdrop-filter: blur(6px);
    border: 1px solid rgba(255, 255, 255, 0.18);
    box-shadow: rgba(142, 142, 142, 0.19) 0px 6px 15px 0px;
    -webkit-box-shadow: rgba(142, 142, 142, 0.19) 0px 6px 15px 0px;
    border-radius: 12px;
    -webkit-border-radius: 12px;
    color: rgb(255, 255, 255);
    position: relative;
}
.te {
    margin-top: 10px;
    text-align: center;
}
.form {
    width: 250px;
    height: 150px;
    position: absolute;
    margin-left: -112px;
    margin-bottom: -65px;
    left: 50%;
    bottom: 50%;
}
.inputs {
    font-size: 18px;
}
input {
    padding-left: 10px;
    outline: none;
    height: 25px;
}
.butn {
    margin-top: 10px;
}
</style>
