# editor

> A Vue.js project
## 说明书 & 设计文档
文档均位于```docs/```下

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8081
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```
For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

## 临时搭建的测试
Spark连接暂时有误，所以使用一个临时的nodejs server作为后端提供服务
### 运行临时测试
```bash
cd temp_server

# install dependencies
npm install

# start server
node server.js
```
在此之后你可以访问localhost:8081进行简单的交互体验
