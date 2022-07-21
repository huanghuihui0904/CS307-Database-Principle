<template>
  <div >
    <el-menu

        style="width: 200px;height: calc(100vh - 50px)"
        default-active="0"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose">
      <el-submenu index="1">
        <template slot="title"  >
          <i class="el-icon-menu"></i>
          <span slot="title">Tables</span>
        </template>

        <el-menu-item @click="$router.push('./boss_center')" index="1">
          <i class="el-icon-apple"></i>
          <span slot="title">Center</span>
        </el-menu-item >
        <el-menu-item @click="$router.push('./boss_staff')" index="2">
          <i class="el-icon-table-lamp" ></i>
          <span slot="title">Staff</span>
        </el-menu-item>
        <el-menu-item @click="$router.push('./boss_enterprise')" index="3" >
          <i class="el-icon-tableware"></i>
          <span slot="title">Enterprise</span>
        </el-menu-item>
        <el-menu-item @click="$router.push('./boss_model')"  index="4">
          <i class="el-icon-phone"></i>
          <span slot="title">Model</span>
        </el-menu-item>
        <el-menu-item index="5" @click="$router.push('./boss_inventory')">
          <i class="el-icon-ice-cream"></i>
          <span slot="title">Inventory</span>
        </el-menu-item>
        <el-menu-item index="6" @click="$router.push('./boss_contract')">
          <i class="el-icon-coin"></i>
          <span slot="title">Contract</span>
        </el-menu-item>
        <el-menu-item index="7" @click="$router.push('./boss_orders')">
          <i class="el-icon-camera"></i>
          <span slot="title">Orders</span>
        </el-menu-item>
        <el-menu-item index="8" @click="$router.push('./boss_bill')">
          <i class="el-icon-apple"></i>
          <span slot="title">Bill</span>
        </el-menu-item>

      </el-submenu>

    </el-menu>
  </div>
</template>

<script>
export default {
  name: "bossAsside"
}
</script>

<style scoped>

</style>