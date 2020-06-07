var express = require('express')
var app = express()
var bodyParser = require('body-parser')
const { NODE_ENV } = require('./global')

base_url = ''
resource = {
  job_url: base_url + '/api/servlet/GetSqlServlet',
  connect_url: base_url + '/api/servlet/ConnectServlet',
  get_tables_url: base_url + '/api/servlet/GetTablesServlet',
  get_dbs_url: base_url + '/api/servlet/GetDatabasesServlet'
}
// 允许跨域请求
app.use(function (req, res, next) {
  res.header('Access-Control-Allow-Origin', '*')
  res.header('Access-Control-Allow-Headers', '*')
  res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS, PATCH')
  res.header('Access-Control-Expose-Headers', '*')
  if (req.method === 'OPTIONS') {
    res.status(200).send()
  } else {
    next()
  }
})
// 路由初始化
app.use(bodyParser.urlencoded({ 'extended': 'true' }))
app.use(bodyParser.json())
app.use(bodyParser.json({ type: 'application/vnd.api+json' }))
app.get('/', (req, res, next) => { res.status(302).redirect('/bank/index.html') })
app.post(resource.job_url, (req, res, next) => {
  console.log(req.body)
  res.send([{
    'col1': 'data1-1',
    'col2': 'data1-2',
    'col3': 'data1-3'
  }, {
    'col1': 'data2-1',
    'col2': 'data2-2',
    'col3': 'data3-3'
  }])
})
app.post(resource.connect_url, (req, res, next) => {
  console.log(req.body)
  res.send([{database: 'db1'}])
})
app.post(resource.get_dbs_url, (req, res, next) => {
  console.log(req.body)
  res.send([{database: 'db1'}])
})
app.post(resource.get_tables_url, (req, res, next) => {
  console.log(req.body)
  res.send([{tablename: 'table1'}, {tablename: 'table2'}, {tablename: 'table3'}])
})
// 错误捕获

module.exports = app
