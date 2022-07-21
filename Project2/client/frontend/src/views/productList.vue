<template>
  <div class="search-box">
    <div class="search-input">

      <input
          type="text"
          :placeholder="plhText"
          maxlength="10"
          @change="inputChange"
          v-model="inputValue"
      />
    </div>
  </div>
</template>
<script>
export default {
  name: "searchInput", // 搜索输入框
  props: {
    // input框占位文字
    plhText: {
      type: String,
      default: "请输入搜索内容"
    }
  },
  data() {
    return {
      inputValue: "" //输入框的值
    };
  },
  methods: {
    // 每次输入框变化刷新列表
    inputChange() {
      // 使用emit给父组件传值
      this.$emit('input-changed', this.inputValue);
    }
  }
};
</script>
