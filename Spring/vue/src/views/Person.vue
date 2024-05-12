<template>
    <div>
        <el-card style="width: 42%;">
            <el-upload
                style="text-align: center; "
                class="avatar-uploader"
                action="http://localhost:9090/file/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
                <img v-if="form.avatorUrl" :src="form.avatorUrl" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <el-form>
                <el-form-item label="用户名" label-width="80px">
                    <el-input v-model="form.username" disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="昵称" label-width="80px">
                    <el-input v-model="form.nickname"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" label-width="80px">
                    <el-input v-model="form.email"></el-input>
                </el-form-item>
                <el-form-item label="电话" label-width="80px">
                    <el-input v-model="form.phone"></el-input>
                </el-form-item>
                <el-form-item label="地址" label-width="80px">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
                <el-form-item>
                    <span style="position: relative; left: 45%;">
                        <el-button type="primary" @click="save">保存信息</el-button>
                    </span>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
export default {
    name: "Person",
    data() {
        return {
            form: {},
            username:
                localStorage.getItem("user") != null
                    ? JSON.parse(localStorage.getItem("user"))[0].username
                    : {},
        };
    },
    methods: {
        async getUser() {
            return (
                await this.request.get("/user/" + this.username)
            ).data;
        },
        save() {
            this.request.post("/user", this.form).then((res) => {
                if (res.code == 200) {
                    this.$message.success("保存成功");
                    // 更新浏览器存储的用户信息
                    this.getUser().then((res) => {
                        res[0].token = JSON.parse(localStorage.getItem("user"))[0].token;
                        localStorage.setItem("user", JSON.stringify(res));
                    });
                    // location.reload()
                    this.$bus.$emit("saveAvator",this.form.avatorUrl)
                } else {
                    this.$message.error("保存失败");
                }
            });
        },
        load() {
            this.request.get("/user/" + this.username).then((res) => {
                if (res.code == 200) {
                    this.form = res.data[0];
                }
            });
        },
        handleAvatarSuccess(res) {
            this.form.avatorUrl = res;
        },
    },
    created() {
        this.getUser().then(res=>{
            this.form=res[0]
        })
    },
};
</script>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 50%;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
}
.avatar {
    text-align: center;
    width: 178px;
    height: 178px;
    display: block;
}
</style>