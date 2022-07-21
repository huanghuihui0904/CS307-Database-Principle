<template>
  <div style="height: 45px;line-height: 45px;border-bottom: 1px solid #ccc;display: flex">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: darkgreen">后台管理</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown :hide-on-click="false">
  <span class="el-dropdown-link"  >
    管理员<i class="el-icon-arrow-down el-icon--right" ></i>
  </span>
      <el-dropdown-menu slot="dropdown" @click="$router.push('./login')">
        <el-dropdown-item @click="$router.push('./login')">
          <el-button @click="$router.push('./login')">退出系统</el-button>

        </el-dropdown-item>

        <el-dropdown-item style="width: 100px">
          <el-button style="width: 100px">个人信息</el-button>
        </el-dropdown-item>
<!--        <el-dropdown-item>螺蛳粉</el-dropdown-item>-->
<!--        <el-dropdown-item disabled>双皮奶</el-dropdown-item>-->
<!--        <el-dropdown-item divided>蚵仔煎</el-dropdown-item>-->
      </el-dropdown-menu>
    </el-dropdown>
    </div>


  </div>

</template>

<script>
export default {
  name: "Header",

}
</script>

<style scoped>

</style>