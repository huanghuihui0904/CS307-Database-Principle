<template>
  <el-table
      :data="tableData"
      style="width: 100%"
      height="450">
    <el-table-column
        fixed
        prop="center"
        label="仓库供应中心"
        width="200">
    </el-table-column>
    <el-table-column
        prop="total_purchase_money"
        label="商品入库总花销"
        width="150">
    </el-table-column>
    <el-table-column
        prop="profits"
        label="商品总销售额"
        width="150">
    </el-table-column>

    <el-table-column
        prop="total_profits"
        label="纯利润"
        width="150">
    </el-table-column>

  </el-table>
</template>

<script>
export default {
  name:"Bill",
  created() {
    const _this = this
    axios.get('/bill/getBill').then(function (resp) {
      _this.tableData = resp.data
      console.log(resp)
    })
  },
  data() {
    return {
      tableData: null
    }
  }
}
</script>