<template>
  <div>
    <el-descriptions class="margin-top" :column="1" :size="size" border>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Director Count
        </template>
        {{ getDirectorCount }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Contracts Manager Count
        </template>
      {{getContracts_ManagerCount}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Salesmen Count
        </template>
       {{getSalesmenCount}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          Supply Staff Count
        </template>
        {{getSupply_StaffCount}}
      </el-descriptions-item>



    </el-descriptions>

  </div>


</template>

<script>
export default {
  name: "Result",
  created() {
    const _this = this
    axios.get('/get/getSalesmenCount').then(function (resp) {
      _this.getSalesmenCount = resp.data
      console.log(resp)
    }),
        axios.get('/get/getDirectorCount').then(function (resp) {
          _this.getDirectorCount = resp.data
          console.log(resp)
        }),

        axios.get('/get/getContracts_ManagerCount').then(function (resp) {
          _this.getContracts_ManagerCount = resp.data
          console.log(resp)
        }),

        axios.get('/get/getSupply_StaffCount').then(function (resp) {
          _this.getSupply_StaffCount = resp.data
          console.log(resp)
        })

  },
  data() {
    return {
      getSalesmenCount: null,
      getDirectorCount:null,
      getContracts_ManagerCount:null,
      getSupply_StaffCount:null

    }
  }
}
</script>


<style scoped>

</style>