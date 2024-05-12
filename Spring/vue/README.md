# vue

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

#line-height=height时文字会居中
collapse-transition,collapse

文件导入最好用 window.location.href = 
window.open()会打开页面

vertical-align: middle;图片和文字居中对齐

@RequestMapping配置所有链接
@RequestBody是controller层用来接收参数的，接收请求体的参数
@RequestParam:接收请求头（配置同param）

  insert、delete、update返回的类型是int类型，将接口改为int或void即可
    select返回的类型Map或List
@PathVariable注解作用:接收可变参数添加在路径上
@RequestParam: 用于获取URL中“?”后携带的参数的值

@param：加在service上 如果我们在动态 SQL 中用到了 参数作为判断条件，那么也是一定要加 @Param 注解的/多个也要加/取别名

@JsonIgnore使得封装回去的被这个修饰的无法导出（默认忽略）
@JsonIgnore使得传入的会被会被忽略（无法接收）

400就是参数传入不对

修改返回值统一用Result
el-dropdown-item不能绑定事件