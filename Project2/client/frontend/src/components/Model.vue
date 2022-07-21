<template>
  <div style="width: 100vw;padding-left: 10px;padding-right: 10px"  >

    <div style="margin-outside: 10px">

      <el-input  placeholder="请输入搜索的关键词" style="width: 40%; padding: 10px" ></el-input>
      <el-button type="primary" style="margin-left: 10px">Enter</el-button>
      <el-button type="primary" style="margin-left: 10px">总计</el-button>
      <el-button type="primary" >增加</el-button>
      <el-button type="primary" >返回</el-button>
      <el-button type="primary" style="margin-left: 10px"
      >搜索</el-button>
    </div>

    <!--<button  @click="getCount">cnt</button>-->
    <el-table
        :data="tableData"
        border
        :row-style="{height:'53px'}"
        :cell-style="{padding:'0px'}"
        style="width: 100%;height: 583px"

        :default-sort = "{prop: 'id', order: 'increasing'}">
      <el-table-column

          prop="id"
          label="id"

          sortable>
      </el-table-column>
      <el-table-column

          prop="number"
          label="Number"

          sortable>
      </el-table-column>
      <el-table-column
          prop="model"
          label="Model"

          sortable>
      </el-table-column>
      <el-table-column
          prop="name"
          label="Name"

          sortable>
      </el-table-column>
      <el-table-column
          prop="unit_price"
          label="Unit Price"

          sortable>
      </el-table-column>


      <el-table-column
          fixed="right"
          label="操作">
        <template slot-scope="scope">
          <el-button  type="text" >更改</el-button>
          <el-button  type="text" >删除</el-button>

        </template>
      </el-table-column>
    </el-table >

    <div style="margin: 10px 0">
      <el-pagination
          background
          layout="prev, pager, next"
          page-size='10'
          :total="total"
          @current-change="page">

      </el-pagination>

    </div>

  </div>
</template>

<script>
export default {
  name: "Model",
  data() {
    return {

      total: null,


      tableData: [

      ]
    }
  },
  methods: {



    page(currentPage) {
      const _this = this
      axios.get('/model/findAll/' + currentPage + '/10').then(function (resp) {
        _this.tableData = resp.data.content
        _this.total = resp.data.totalElements
        console.log(resp)
      })
    },



  },
  created() {
    const _this = this
    axios.get('/model/findAll/1/10').then(function (resp) {
      _this.tableData = resp.data.content

      _this.total = resp.data.totalElements
      console.log(resp)
    })
  }
}
</script>

<style scoped>

</style>