<template>
  <el-container>
    <el-aside width="200px">
      <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
            <el-submenu v-for="(db, db_index) in databases" :index="db_index.toString()" :key="db.name" style="text-align: left;">
              <template slot="title">
                <span>
                  <i>
                    <svg t="1591534559322" style="padding:0 3px 3px 3px;" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2286" width="1.2rem" height="1.2rem"><path d="M870.4 57.6C780.8 19.2 652.8 0 512 0 371.2 0 243.2 19.2 153.6 57.6 51.2 102.4 0 153.6 0 211.2l0 595.2c0 57.6 51.2 115.2 153.6 153.6C243.2 1004.8 371.2 1024 512 1024c140.8 0 268.8-19.2 358.4-57.6 96-38.4 153.6-96 153.6-153.6L1024 211.2C1024 153.6 972.8 102.4 870.4 57.6L870.4 57.6zM812.8 320C729.6 352 614.4 364.8 512 364.8 403.2 364.8 294.4 352 211.2 320 115.2 294.4 70.4 256 70.4 211.2c0-38.4 51.2-76.8 140.8-108.8C294.4 76.8 403.2 64 512 64c102.4 0 217.6 19.2 300.8 44.8 89.6 32 140.8 70.4 140.8 108.8C953.6 256 908.8 294.4 812.8 320L812.8 320zM819.2 505.6C736 531.2 620.8 550.4 512 550.4c-108.8 0-217.6-19.2-307.2-44.8C115.2 473.6 64 435.2 64 396.8L64 326.4C128 352 172.8 384 243.2 396.8 326.4 416 416 428.8 512 428.8c96 0 185.6-12.8 268.8-32C851.2 384 896 352 960 326.4l0 76.8C960 435.2 908.8 473.6 819.2 505.6L819.2 505.6zM819.2 710.4c-83.2 25.6-198.4 44.8-307.2 44.8-108.8 0-217.6-19.2-307.2-44.8C115.2 684.8 64 646.4 64 601.6L64 505.6c64 32 108.8 57.6 179.2 76.8C326.4 601.6 416 614.4 512 614.4c96 0 185.6-12.8 268.8-32C851.2 563.2 896 537.6 960 505.6l0 96C960 646.4 908.8 684.8 819.2 710.4L819.2 710.4zM512 960c-108.8 0-217.6-19.2-307.2-44.8C115.2 889.6 64 851.2 64 812.8l0-96c64 32 108.8 57.6 179.2 76.8 76.8 19.2 172.8 32 262.4 32 96 0 185.6-12.8 268.8-32 76.8-19.2 121.6-44.8 185.6-76.8l0 96c0 38.4-51.2 76.8-140.8 108.8C736 947.2 614.4 960 512 960L512 960zM512 960" p-id="2287" fill="#e6e6e6"></path></svg>
                  </i>
                  {{ db.name }}
                </span>
              </template>
              <el-menu-item-group>
                <el-menu-item v-for="(table, t_index) in db.tables" :index="db.name + t_index" @click="getTableDetails(db_index, t_index)" :key="db.name + t_index">
                  <i class="el-icon-tickets"></i>
                  {{ table }}
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
      </el-menu>
      <div class="aside-hover" v-if="databases.length <= 0">
        <el-button type="text" @click="dialogFormVisible = true">请先输入连接信息</el-button>
      </div>
    </el-aside>
    <el-container>
      <el-header>
        执行数据库
        <el-select v-model="current_db" placeholder="选择数据库" @change="dbChanged">
            <el-option
              v-for="(db, index) in databases"
              :key="db.name"
              :label="db.name"
              :value="index">
            </el-option>
          </el-select>
          执行表
          <el-select v-model="current_table" placeholder="选择表">
            <div v-if="databases.length > 0 && current_db_index >= 0">
              <el-option
                v-for="(table, index) in databases[current_db_index].tables"
                :key="table"
                :label="table"
                :value="table">
              </el-option>
            </div>
          </el-select>
      </el-header>
      <el-main>
        <div id="editor">
          <editor v-model="content"
                  @init="editorInit"
                  lang="sql"
                  theme="twilight"
                  width="100%"
                  height="100%"
                  :options="editor_options"
                  style="font-size: 1em;"
          ></editor>
        </div>
      </el-main>
      <el-footer>
        <el-tabs type="border-card" style="height: 100%;">
          <el-tab-pane label="结果">
            <p v-if="result.loading">{{ result.temp_message }}</p>
            <vtable v-if="result.tableData.length > 0" :table-data="result.tableData" :columns="result.columns"></vtable>
          </el-tab-pane>
        </el-tabs>
        <el-button type="primary" class="btn-submit" @click="submitRunSQL">提交运行</el-button>
      </el-footer>
    </el-container>
    <el-dialog title="连接" :visible.sync="dialogFormVisible">
      <el-form :model="connection" label-position="top" label-width="60px" style="text-align: center;">
        <el-form-item label="主机">
          <el-input v-model="connection.ip" placeholder="host" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="connection.username" placeholder="User name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="connection.password" placeholder="Password" type="password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="数据库名">
          <el-input v-model="connection.Spark_db" placeholder="Database" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" style="text-align: center;">
        <el-button type="primary" @click="connectDB">连 接</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
  import Service from "@/components/Service.js"
  import Table from "@/components/Table.vue"
  export default {
    name: 'Editor',
    components: {
      'editor': require('vue2-ace-editor'),
      'vtable': Table
    },
    data () {
      return {
        debug: false,
        dialogFormVisible: false,
        connection: {
          ip: '',
          username: '',
          password: '',
          Spark_db: ''
        },
        connection_key: '',
        current_db: '',
        current_db_index: -1,
        current_table: '',
        editor_options: {
          enableBasicAutocompletion: true,
          enableSnippets: true,
          enableLiveAutocompletion: true
        },
        content: "-- write your sql here",
        databases: [],
        result: {
          columns: [],
          // result set
          tableData: [],
          temp_message: "Executing SQL...",
          loading: false,
        },
      }
    },
    methods: {
      connectDB: async function() {
        // console.log('DB connected')
        // this.dialogFormVisible = !this.dialogFormVisible
        // this.databases = [{
        //   name: 'db1',
        //   tables: ['db1-table1', 'db1-table2']
        // }, {
        //   name: 'db2',
        //   tables: ['db2-table1', 'db2-table2', 'db2-table3']
        // }]
        const http = this.$http
        const service = new Service(http)
        const data = await service.connect(this.$data.connection)
        if (this.debug) {
          console.log('Received data(connect):')
          console.log(data)
        }
        this.databases = data
        if (this.databases !== 0) {
          this.connection_key = 'key'
        }
        this.dialogFormVisible = false
      },
      editorInit: function () {
        require('brace/ext/language_tools')
        require('brace/mode/sql')
        require('brace/theme/twilight')
      },
      submitRunSQL: async function () {
        const http = this.$http
        const service = new Service(http)
        const self = this
        if (this.connection_key === '') {
          this.dialogFormVisible = true
          this.$message.error({
            message: "请先进行连接",
            customClass: "el-message"
          });
          return
        }
        if (this.current_db === '') {
          this.$message.error({
            message: "请选择要执行的数据库",
            customClass: "el-message"
          });
        }
        this.result.loading = true
        const connection = this.connection
        connection.Spark_db = this.databases[this.current_db_index].name
        const data = await service.submitJob(connection, self.content)
        if (this.debug) {
          console.log('Received data(run sql):')
          console.log(data)
          return
        }
        if (!data || data.length === 0 || data.length === undefined) {
          this.result.temp_message = data || 'Unknown error'
          return
        }
        const keys = Object.keys(data[1])
        this.result.loading = false
        this.result.columns = keys
        this.result.tableData = data
      },
      handleOpen: async function(key, keyPath) {
        // console.log('You clicked db ', this.databases[key].name)
        // console.log(`db: ${this.current_db} table: ${this.current_table}`)

        if (Object.keys(this.databases[key]).indexOf('tables') === -1) {
          const http = this.$http
          const service = new Service(http)
          let connection = this.connection
          connection.Spark_db = this.databases[key].name
          const data = await service.getTables(connection)
          if (this.debug) {
            console.log('Received data(get table):')
            console.log(data)
          }
          this.databases[key].tables = data
          this.current_db_index = key
        }
      },
      handleClose: function(key, keyPath) {
      },
      getTableDetails: function(db_index, table_index) {
        // console.log("You clicked table ", this.$data.databases[db_index].tables[table_index])
        this.current_db = this.databases[db_index].name
        this.current_db_index = db_index
        this.current_table = ''
      },
      dbChanged: async function(db_index) {
        if (Object.keys(this.databases[db_index]).indexOf('tables') === -1) {
          const http = this.$http
          const service = new Service(http)
          let connection = this.connection
          connection.Spark_db = this.databases[db_index].name
          const data = await service.getTables(connection)
          if (this.debug) {
            console.log('Received data(get table):')
            console.log(data)
          }
          this.databases[db_index].tables = await service.getTables(connection)
        }
        this.current_db_index = db_index
        this.current_table = ''
      }
    },
  }
</script>

<style>
  .el-header {
    background-color: #545c64;
    color: #ffd04b;
    text-align: center;
    line-height: 6rem;
    min-height: 6rem;
  }
  .el-footer {
    background-color: #FFF;
    color: #333;
    text-align: center;
    min-height: 20rem;
    padding: 2px;
  }
  .el-aside {
    background-color: #545c64;
    color: #333;
    text-align: center;
    line-height: 56rem;
    min-height: 56rem;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 30rem;
    padding: 0;
  }
  
  body > .el-container {
    margin-bottom: 0px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
  
  .el-dialog {
    width: 30rem;
  }
  .el-input {
    width: 85%;
  }
  
  #editor {
    width: 100%;
    height: 100%;
  }
  
  .el-message {
    z-index: 99999 !important;
  }
  
  .btn-submit {
    position: absolute;
    bottom: 19rem;
    right: 1rem;
  }
  .aside-hover {
    text-align: center;
    height: 100%;
    width: 100%;
    background-color: rgba(211, 220, 230, 0.2);
  }
</style>
