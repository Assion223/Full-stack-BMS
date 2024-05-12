<template>
    <div>
        <el-row :gutter="10" style="margin: 40px 0px 60px;">
            <el-col :span="6">
                <el-card style="color: #409EFF;">
                    <div>
                        <i class="el-icon-user-solid" />用户总数
                    </div>
                    <div style="padding: 10px 0; text-align: center; font-weight:bold;">100</div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #F56C6C;">
                    <div>
                        <i class="el-icon-money"></i>销售总量
                    </div>
                    <div style="padding: 10px 0; text-align: center; font-weight:bold;">￥ 100</div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #67C23A;">
                    <div>
                        <i class="el-icon-bank-card"></i>收益总额
                    </div>
                    <div style="padding: 10px 0; text-align: center; font-weight:bold;">￥ 100</div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #909399;">
                    <div>
                        <i class="el-icon-s-shop"></i>门店总数
                    </div>
                    <div style="padding: 10px 0; text-align: center; font-weight:bold;">100</div>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-card id="main" style="width: 500px; height: 400px;padding: 10px;"></el-card>
            </el-col>

            <el-col :span="12">
                <el-card id="pie" style="width: 500px; height: 400px;padding: 10px;"></el-card>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import * as echarts from "echarts";
export default {
    name: "Home",
    methods: {},
    mounted() {
        //基础折线图
        var chartDom = document.getElementById("main");
        var myChart = echarts.init(chartDom);
        var option;
        option = {
            title: {
                text: "各季度会员人数统计",
                subtext: "趋势图",
                left: "center",
            },
            xAxis: {
                type: "category",
                data: ["第一季度", "第二季度", "第三季度", "第四季度"],
            },
            yAxis: {
                type: "value",
            },
            series: [
                {
                    data: [],
                    type: "line",
                },
                {
                    data: [],
                    type: "bar",
                },
            ],
        };
        this.request("/echarts/members").then((res) => {
            // option.xAxis.data = res.data.x;
            option.series[0].data = res.data;
            option.series[1].data = res.data;
            myChart.setOption(option);
        });

        //饼图
        var chartDoms = document.getElementById("pie");
        var myCharts = echarts.init(chartDoms);
        var options;

        options = {
            title: {
                text: "各季度会员人数统计",
                subtext: "比例图",
                left: "center",
            },
            tooltip: {
                trigger: "item",
            },
            legend: {
                orient: "vertical",
                left: "left",
            },
            series: [
                {
                    name: "会员人数",
                    type: "pie",
                    radius: "60%",
                    data: [
                        { value: 0, name: "" },
                        { value: 0, name: "" },
                        { value: 0, name: "" },
                        { value: 0, name: "" },
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: "rgba(0, 0, 0, 0.5)",
                        },
                    },
                },
            ],
        };
        this.request("/echarts/members").then((res) => {
            // option.xAxis.data = res.data.x;
            let index=res?res.data.length:0;
            for (let i = 0; i < index; i++) {
                options.series[0].data[i].value = res.data[i];
                options.series[0].data[i].name = "第" + (i + 1) + "季度";
            }
            myCharts.setOption(options);
        });
    },
};
</script>
<style>
</style>