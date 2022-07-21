<template>
  <div>
    <title-bar :title="title" @goBack="goback"></title-bar>
    <search-input
        :plhText="searchPlhText"
        @input-changed="searchInputChange"
    ></search-input>
    <div v-for="(prd,index) in productListRst" :key="index">
      <prd-item :prd="prd" @toPrdDetail="toPrdDetail"></prd-item>
    </div>
  </div>
</template>
<script>
// import TitleBar from "@/components/TitleBar";
import Search from "@/views/Search";
import productList from "@/views/productList";
export default {
  name: "",
  components: {
    // TitleBar,
    Search,
    productList
  },
  data() {
    return {
      title: "产品列表",
      searchPlhText: "请输入产品名称",
      productList: {}, // 产品列表
      productListRst: {}, // 搜索筛选之后的产品列表
    }
  },
  created() {
    // 初始化页面参数，按照生命周期，子组件需要的参数父组件需要在created生命周期取值
    this.initParams();
  },
  methods: {
    // 返回方法
    goback() {
      // this.$emit("GoBack");
    },
    // 初始化页面参数,获取产品列表
    initParams() {
      this.productList = [
        {
          imgPath: 'apple-1001.png',
          name: 'Apple iPad Air 平板电脑(2020新款)',
          price: '4799.00',
          sale: '5',
          ranking: '10000+评价 平板热卖第5名',
          prdShopName: 'Apple官方旗舰店'
        },
        {
          imgPath: 'apple-1002.png',
          name: 'Apple iPhone 11手机',
          price: '4999.00',
          sale: '5',
          ranking: '375万+评价',
          prdShopName: 'Apple官方旗舰店'
        },
        {
          imgPath: 'apple-1003.jpg',
          name: 'Apple AirPods 配充电盒 Apple蓝牙耳机',
          price: '1246.00',
          sale: '5',
          ranking: '200万+评价',
          prdShopName: 'Apple官方旗舰店'
        },
      ];
      this.productListRst = this.productList;
    },
    // 每次search框变化则进行筛选,对数据进行逻辑处理
    searchInputChange(value) {
      // 若未输入值，则展示所有数据
      if(null === value || undefined === value){
        this.productListRst = this.productList;
      } else {
        this.productListRst = []; // 结果列表置空
        let regStr =  '';
        // 初始化正则表达式
        for(let i=0; i<value.length; i++){
          regStr = regStr + '(' + value[i] + ')([\\s]*)'; //跨字匹配
        }
        let reg = new RegExp(regStr);
        console.log(reg);
        for(let i=0; i<this.productList.length; i++){
          let name = this.productList[i].name; //按照名字匹配
          let regMatch = name.match(reg);
          if(null !== regMatch) {// 将匹配的数据放入结果列表中
            this.productListRst.push(this.productList[i]);
          }
        }
      }
    },
    // 去往产品详情页
    toPrdDetail(){
      this.$router.push({path: '/detail'})
    }
  }
};
</script>
<style scoped>
#page-title {
  width: 100%;
  background-color: #fff;
  display: flex;
  justify-content: center;
}
.backImg {
  width: 20px;
}
</style>
