
<!--<template>-->
<!--<div style="width: 100vw;padding: 10px">-->

<!--  <Staff >-->

<!--  </Staff>-->
<!--&lt;!&ndash;  <router-link to='/staff'>&ndash;&gt;-->
<!--&lt;!&ndash;    <button>点击跳转2</button>&ndash;&gt;-->
<!--&lt;!&ndash;  </router-link>&ndash;&gt;-->
<!--</div>-->

<!--</template>-->
<template>
  <div >

    <!--  头部-->
    <Header></Header>
    <!--  主体-->
    <div style="display: flex">
      <!--    侧边栏-->
      <Aside></Aside>
      <!--    内容区域-->
      <!--      <router-view style="flex: 1" v-if="isRouterAlive"></router-view>-->
      <router-view style="flex: 1" ></router-view>

    </div>

  </div>
</template>


<script>






import Aside from "@/components/Aside";
import Staff from "@/components/Staff";
import Header from "@/components/Header";
import bossAside from "@/components/bossAside";
export default {
  name: "Home",
  components:{
    Aside,Header
  }
  // ,
  // provide(){
  //   return{
  //     reload:this.reload
  //   }
  // },
  // data(){
  //   return{
  //     isRouterAlive:true
  //   }
  // },
  // methods:{
  //   reload() {
  //     this.isRouterAlive=false
  //     this.$nextTick(function (){
  //       this.isRouterAlive=true
  //     })
  //   }
  // }
  // methods: {
  //
  //
  //   getCount(){
  //     const _this = this
  //     axios.get('/staff/get' ).then(function (resp) {
  //       _this.$alert(resp.data);
  //
  //
  //     })
  //   },
  //   deleteStaff(row){
  //     const _this = this
  //     axios.delete('/staff/deleteById/' + row.id).then(function (resp) {
  //       _this.$alert('删除成功!', 'OK', {
  //         confirmButtonText: '确定',
  //         callback: action => {
  //           window.location.reload()
  //
  //         }
  //       });
  //
  //
  //     })
  //   },
  //   updateStaff(row){
  //     this.$router.push({
  //       path:'/updateStaff',
  //       query:{
  //         id:row.id
  //       }
  //     })
  //   },
  //
  //   page(currentPage) {
  //     const _this = this
  //     axios.get('/staff/findAll/' + currentPage + '/10').then(function (resp) {
  //       _this.tableData = resp.data.content
  //       _this.total = resp.data.totalElements
  //       console.log(resp)
  //     })
  //   }
  // },
  // created() {
  //   const _this = this
  //   axios.get('/staff/findAll/1/10').then(function (resp) {
  //     _this.tableData = resp.data.content
  //
  //     _this.total = resp.data.totalElements
  //     console.log(resp)
  //   })
  // },
  // data() {
  //   return {
  //     total: null,
  //     tableData: null,
  //     count:null
  //   }
  // }
}
</script>

<style scoped>

</style>