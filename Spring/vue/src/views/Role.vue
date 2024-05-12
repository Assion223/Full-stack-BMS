<template>
    <div>
        <div style="padding: 10px 0;">
            <el-input
                style="width: 250px;"
                placeholder="请输入要查询的名称"
                suffix-icon="el-icon-search"
                v-model="name"
            ></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>
        <div style="margin: 10px 0;">
            <el-button type="primary">
                <el-upload
                    class="upload-demo"
                    :show-file-list="false"
                    :on-success="handFileSuccess"
                    action="http://localhost:9090/file/upload"
                >
                    新增
                    <i class="el-icon-top"></i>
                </el-upload>
            </el-button>
            <el-button type="danger" @click="delBatchOne">
                批量删除
                <i class="el-icon-remove-outline"></i>
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
            <el-table-column prop="name" label="名称" width="220"></el-table-column>
            <el-table-column prop="type" label="唯一标识" width="120"></el-table-column>
            <el-table-column prop="size" label="描述" width="120"></el-table-column>
            <el-table-column label="操作" width="380">
                <template slot-scope="scope">
                    <el-row>
                        <el-button type="info" @click="handDelOne(scope.row.id)">
                            分配菜单
                            <i class="el-icon-menu"></i>
                        </el-button>
                        <el-button type="success" @click="handDelOne(scope.row.id)">
                            编辑
                            <i class="el-icon-edit"></i>
                        </el-button>
                        <el-button type="danger" @click="handDelOne(scope.row.id)">
                            删除
                            <i class="el-icon-remove-outline"></i>
                        </el-button>
                    </el-row>
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
export default {
    name: "Files",
    data() {
        return {
            tableData: [],
            total: 0,
            pageSize: 5,
            pageNum: 1,
            name: "",
            deleteIdsDialogVisible: false,
            multipleSelection: [],
            handDelTwoId: "",
            deleteDialogVisible: false,
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
            this.request("/role"
                // params: {
                //     pageNum: this.pageNum,
                //     pageSize: this.pageSize,
                //     name: this.name,
                // },
            ).then((res) => {
                // this.tableData = res.data;
                // this.total = res.data.length;
            });
        },
        reset() {
            this.name = "";
            this.load();
        },
        handFileSuccess() {
            this.$message.success("文件上传成功");
            this.load();
        },
        delBatchOne() {
            this.deleteIdsDialogVisible = true;
        },
        delBatchTwo() {
            let ids = this.multipleSelection.map((item) => item.id);
            this.request.post("/file/del/batch", ids).then((res) => {
                if (res.code == 200) {
                    this.$message.success("删除成功");
                    this.deleteIdsDialogVisible = false;
                    this.load();
                } else {
                    this.$message.error("删除失败");
                }
            });
        },
        changeEnable(row) {
            this.request.post("/file/update", row).then((res) => {
                if (res.code === "200") {
                    this.$message.success("操作成功");
                }
            });
        },
        handDelOne(id) {
            this.deleteDialogVisible = true;
            this.handDelTwoId = id;
        },
        handDelTwo() {
            this.request.delete("/file/" + this.handDelTwoId).then((res) => {
                if (res.code == 200) {
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
        download(url) {
            window.open(url);
        },
    },
    created() {
        this.load();
    },
};
</script>

<style>
</style>