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

        :default-sort = "{prop: 'center', order: 'increasing'}">
      <el-table-column

          prop="center"
          label="Center"

          sortable>
      </el-table-column>
      <el-table-column

          prop="product_model"
          label="Product Model"

          sortable>
      </el-table-column>
      <el-table-column
          prop="quantity"
          label="Quantity"

          sortable>
      </el-table-column>
      <el-table-column
          prop="total_purchase_money"
          label="Total Purchase Money"

          sortable>
      </el-table-column>
      <el-table-column
          prop="sales_quantity"
          label="Sales Volume"

          sortable>
      </el-table-column>
      <el-table-column
          prop="profits"
          label="Profits"

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
  name: "Inventory",
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
      axios.get('/inventory/findAll/' + currentPage + '/10').then(function (resp) {
        _this.tableData = resp.data.content
        _this.total = resp.data.totalElements
        console.log(resp)
      })
    },



  },
  created() {
    const _this = this
    axios.get('/inventory/findAll/1/10').then(function (resp) {
      _this.tableData = resp.data.content

      _this.total = resp.data.totalElements
      console.log(resp)
    })
  }
}
</script>

<style scoped>

</style>