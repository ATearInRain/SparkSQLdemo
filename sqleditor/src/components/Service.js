class Service {

  constructor(_http) {
    const base_url = process.env.API_ROOT || 'http://localhost:3000'
    this.http = _http
    this.resource = {
      job_url: base_url + '/api/servlet/GetSqlServlet',
      connect_url: base_url + '/api/servlet/ConnectServlet',
      get_tables_url: base_url + '/api/servlet/GetTablesServlet',
      get_dbs_url: base_url + '/api/servlet/GetDatabasesServlet'
    }
    this.defaultOption = {
      emulateJSON: true
    }
  }
  
  async connect(params) {
    const self = this
    const options = {
      emulateJSON: true
    }
    let url = self.resource.get_dbs_url
    let res = await self.http.post(url, params, options)
    const databases = res.body
    const dbs = []
    
    for (let dbindex in databases) {
      let db = {}
      db.name = databases[dbindex].database
      dbs.push(db)
    }
    return dbs
  }
  
  async getTables(params) {
    const self = this
    let url = self.resource.get_tables_url
    let res = await self.http.post(url, params, self.defaultOption)
    const tables = res.body
    const tbs = []
    
    for (let tbindex in tables) {
      tbs.push(tables[tbindex].tablename)
    }
    return tbs
  }

  async submitJob(connection, sql, mock=false) {
    if (mock) {
      console.log('data is mocked')
      return {
        columns: ['col1', 'col2', 'col3'],
        tableData: [
          ['data1_1', 'data1_2', 'data1_3'],
          ['data1_1', 'data1_2', 'data1_3'],
          ['data1_1', 'data1_2', 'data1_3'],
          ['data1_1', 'data1_2', 'data1_3'],
          ['data1_1', 'data1_2', 'data1_3'],
          ['data1_1', 'data1_2', 'data1_3'],
          ['data1_1', 'data1_2', 'data1_3'],
          ['data2_1', 'data2_2', 'data2_3']
        ]
      }
    }
    connection.sql = sql
    const url = this.resource.job_url
    const self = this
    const res = await this.http.post(url, connection, self.defaultOption)
    return res.body
  }

  killJob(jobType, jobId) {
    const self = this
    const options = {
      emulateJSON: true
    }
    let finalUrl = self.resource.kill_stream_jobs_url

    if (jobType != "stream") {
      finalUrl = self.resource.kill_batch_jobs_url
    }

    const success = []
    self.http.post(finalUrl, {groupId: jobId}, options).then(ok => {
      success.push(true)
    }, notok => {
      success.push(false)
    })
    return success
  }
}

export default Service
