<template>
  <div>

    <el-container style="height: 500px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu router >
          <el-submenu >
            <template #title><i class="el-icon-setting"></i>{{$router.options.routes[2].name}}</template>
            <el-menu-item v-for="(item2,index2) in $router.options.routes[2].children" :index="item2.path"
                          :class="$route.path==item2.path?'is-active':''"v-if="item2.show">{{item2.name}}
            </el-menu-item>
          </el-submenu>
        </el-menu>

      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>

    </el-container>
  </div>
</template>

<script>
export default {
  name: "Index_center"
}
</script>

<style scoped>

</style>