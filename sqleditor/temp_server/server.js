var port = process.env.PORT || 3000

const app = require('./app')

app.listen(port, () => {
  console.info(`[Server]\t监听端口\thostname:${port}`)
})
