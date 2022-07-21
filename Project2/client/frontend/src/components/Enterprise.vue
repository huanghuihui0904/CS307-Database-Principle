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

          prop="name"
          label="Name"

          sortable>
      </el-table-column>
      <el-table-column
          prop="country"
          label="Country"

          sortable>
      </el-table-column>
      <el-table-column
          prop="city"
          label="City"

          sortable>
      </el-table-column>
      <el-table-column
          prop="supply_center"
          label="Supply_center"

          sortable>
      </el-table-column>
      <el-table-column
          prop="industry"
          label="Industry"

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
      <!--新增Staff的弹窗-->

    </div>

  </div>

</template>

<script>
export default {
  name:"Enterprise",
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
      axios.get('/enterprise/findAll/' + currentPage + '/10').then(function (resp) {
        _this.tableData = resp.data.content
        _this.total = resp.data.totalElements
        console.log(resp)
      })
    },



  },
  created() {
    const _this = this
    axios.get('/enterprise/findAll/1/10').then(function (resp) {
      _this.tableData = resp.data.content

      _this.total = resp.data.totalElements
      console.log(resp)
    })
  }
}
</script>