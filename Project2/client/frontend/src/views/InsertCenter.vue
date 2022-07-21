<template>

  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

    <el-form-item label="id" prop="id">
      <el-input v-model="ruleForm.id"></el-input>
    </el-form-item>

    <el-form-item label="name" prop="name">
      <el-input v-model="ruleForm.name"></el-input>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>

</template>


<script>
export default {
  name:"InsertCenter",
  data() {
    return {
      ruleForm: {
        id: '',
        name: ''
      },
      rules: {
        id: [
          {required: true, message: '请输入id', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入name', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ]

      }
    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post("/center/save", this.ruleForm).then(function (resp) {

            if (resp.data=== "success") {
              _this.$alert('添加成功!', 'OK', {
                confirmButtonText: '确定',
                callback: action => {

                  _this.$router.push('/center')
                }
              });

            }else {
              _this.$alert('登录失败!', 'OK', {
                confirmButtonText: '确定',

              })
            }


          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>