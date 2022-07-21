<template>
  <div >

   <router-view></router-view>

  </div>
</template>



<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
import Home from "@/views/Home";
import Staff from "@/components/Staff";

export default {
name:"App",
  components:{
    Home,
    Header,Aside
  }
};
</script>
