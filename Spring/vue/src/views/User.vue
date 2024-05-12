<template>
    <div>
        <div style="padding: 10px 0;">
            <el-input
                style="width: 250px;"
                placeholder="请输入要查询的用户名称"
                suffix-icon="el-icon-search"
                v-model="username"
            ></el-input>
            <el-input
                style="width: 250px;"
                placeholder="请输入要查询的用户名称邮箱"
                suffix-icon="el-icon-message"
                v-model="email"
                class="ml-5"
            ></el-input>
            <el-input
                style="width: 250px;"
                placeholder="请输入要查询的用户名称地址"
                suffix-icon="el-icon-position"
                v-model="address"
                class="ml-5"
            ></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>
        <div style="margin: 10px 0;">
            <el-button type="primary" @click="dialogV('formName')">
                新增
                <i class="el-icon-circle-plus-outline"></i>
            </el-button>
            <el-button type="primary" @click="delBatchOne">
                批量删除
                <i class="el-icon-remove-outline"></i>
            </el-button>
            <el-button type="primary">
                <el-upload
                    class="upload-demo"
                    :show-file-list="false"
                    accept="xlsx"
                    :on-success="handFileSuccess"
                    action="http://localhost:9090/user/import"
                >
                    导入
                    <i class="el-icon-bottom"></i>
                </el-upload>
            </el-button>
            <el-button type="primary" @click="exp">
                导出
                <i class="el-icon-top"></i>
            </el-button>
        </div>
        <!-- 表单 -->
        <el-table
            :data="tableData"
            :row-class-name="rowClassName"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="140"></el-table-column>
            <el-table-column prop="username" label="用户名" width="120"></el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
            <el-table-column prop="phone" label="电话" width="120"></el-table-column>
            <el-table-column prop="address" label="地址" width="120"></el-table-column>
            <el-table-column>
                <template slot-scope="scope">
                    <el-button type="warning" @click="handEdit(scope.row)">
                        编辑
                        <i class="el-icon-edit"></i>
                    </el-button>
                    <el-button type="danger" @click="handDelOne(scope.row.id)">
                        删除
                        <i class="el-icon-remove-outline"></i>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        ></el-pagination>
        <!-- //新增填表 -->
        <el-dialog title="用户信息" :visible.sync="dialogVisible" width="30%">
            <el-form :model="form" ref="formName">
                <el-form-item label="用户名" label-width="80px">
                    <el-input v-model="form.username"></el-input>
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
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="cancles">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 删除填表 -->
        <el-dialog title="是否确定删除" :visible.sync="deleteDialogVisible" width="30%" center>
            <span slot="footer" class="dialog-footer">
                <el-button @click="deleteDialogVisible = false" size="big">取 消</el-button>
                <el-button type="primary" @click="handDelTwo" size="big">确认删除</el-button>
            </span>
        </el-dialog>
        <!-- 整体 -->
        <el-dialog title="是否确定这些删除" :visible.sync="deleteIdsDialogVisible" width="30%" center>
            <span slot="footer" class="dialog-footer">
                <el-button @click="deleteIdsDialogVisible = false" size="big">取 消</el-button>
                <el-button type="primary" @click="delBatchTwo" size="big">确认删除</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import request from "@/utils/request";
export default {
    name: "User",
    data() {
        return {
            tableData: [],
            total: 0,
            pageSize: 5,
            pageNum: 1,
            username: "",
            email: "",
            address: "",
            dialogVisible: false,
            deleteDialogVisible: false,
            deleteIdsDialogVisible: false,
            form: {},
            handDelTwoId: 0,
            multipleSelection: [],
        };
    },
    methods: {
        rowClassName({ row, rowIndex }) {
            return "bColor";
        },
        handleSizeChange(size) {
            this.pageSize = size;
            this.load();
        },
        handleCurrentChange(num) {
            this.pageNum = num;
            this.load();
        },
        load() {
            request
                .get(`/user/page`, {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        username: this.username,
                        email: this.email,
                        address: this.address,
                    },
                })
                .then((res) => {
                    this.tableData = res.data;
                    this.total = res.total;
                });
            // fetch(
            //     `http://localhost:9090/user/page?pageNum=${this.pageNum}&pageSize=${this.pageSize}&username=${this.username}`
            // )
            //     .then((res) => res.json())
            //     .then((res) => {
            //         this.tableData = res.data;
            //         this.total = res.total;
            //     });
        },
        reset() {
            this.username = "";
            this.email = "";
            this.address = "";
            this.load();
        },
        dialogV() {
            this.dialogVisible = true;
        },
        save() {
            request.post("/user", this.form).then((res) => {
                if (res.code == 200) {
                    this.$message.success("保存成功");
                    this.dialogVisible = false;
                    this.form = {};
                    this.load();
                } else {
                    this.$message.error("保存失败");
                }
            });
        },
        cancles() {
            this.dialogVisible = false;
            this.form = {};
        },
        handEdit(row) {
            this.form = row;
            this.dialogVisible = true;
        },
        handDelOne(id) {
            this.deleteDialogVisible = true;
            this.handDelTwoId = id;
        },
        handDelTwo() {
            request.delete("/user/" + this.handDelTwoId).then((res) => {
                if (res) {
                    this.$message.success("删除成功");
                    this.deleteDialogVisible = false;
                    this.load();
                } else {
                    this.$message.error("删除失败");
                }
            });
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delBatchOne() {
            this.deleteIdsDialogVisible = true;
        },
        delBatchTwo() {
            let ids = this.multipleSelection.map((item) => item.id);
            request.post("/user/del/batch", ids).then((res) => {
                if (res) {
                    this.$message.success("删除成功");
                    this.deleteIdsDialogVisible = false;
                    this.load();
                } else {
                    this.$message.error("删除失败");
                }
            });
        },
        exp() {
            window.location.href = "http://localhost:9090/user/export";
        },
        handFileSuccess() {
            this.$message.success("上传成功");
            this.load();
        },
    },
    created() {
        this.load();
    },
};
</script>
