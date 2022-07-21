<template>
  <div>

    <el-container style="{ height: clientHeight-194 + 'px' }">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu router >
          <el-submenu >
            <template #title><i class="el-icon-setting"></i>{{$router.options.routes[1].name}}</template>
            <el-menu-item v-for="(item2,index2) in $router.options.routes[1].children" :index="item2.path"
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
  name: "Index_boss",
  data() {
    return {
      clientHeight: document.body.clientHeight
    }
  },
  mounted() {
    const that = this
    window.onresize = () => {
      return (() => {
        window.screenHeight = document.body.clientHeight
        that.clientHeight = window.screenHeight
      })()
    }
  },
  watch: {
    clientHeight(val) {
      // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
      if (!this.timer) {
        // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
        this.clientHeight = val
        this.timer = true
        let that = this
        setTimeout(function() {
          // 打印screenWidth变化的值
          console.log(that.clientHeight)
          that.timer = false
        }, 400)
      }
    }
  }
}
</script>

<style scoped>

</style>