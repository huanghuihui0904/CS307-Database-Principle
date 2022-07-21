<template>
<div style="width: 100%;height: 100vh;background-color: aliceblue;overflow: hidden">
  <div style="width: 600px;margin: 150px auto">
    <div style="color: cornflowerblue;font-size: 30px;font-weight: bold;text-align: center;padding: 30px 0">Welcome</div>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="20%" class="demo-ruleForm" >
<Index v-bind:val1="ruleForm"></Index>
    <el-form-item label="用户名" prop="user_name">
      <el-input prefix-icon="el-icon-user-solid" v-model="ruleForm.user_name" style="width: 80%"></el-input>
    </el-form-item>

    <el-form-item label="密码" prop="password">
      <el-input prefix-icon="el-icon-lock" v-model="ruleForm.password" style="width: 80%" show-password></el-input>
    </el-form-item>


    <el-form-item>
<!--      用login可以直接登录 但用submit不行-->


      <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>

      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
  </div>
</div>
</template>


<script>
export default {
  data() {
    return {
      ruleForm: {
        user_name: '',
        password: '',
        b:''

      },
      rules: {
        user_name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ]

      }
    };
  },
  methods: {
    login(){
        this.$router.push({path:'/',})
    },
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        const _this2 = this
        if (valid) {
          axios.post("/login/check", this.ruleForm).then( function (resp) {
            console.log(resp)
            if (resp.data !== "error") {
            _this.b='true'
              sessionStorage.setItem('isLogin',1)
              axios.post("/login/getRole", _this2.ruleForm).then( function (resp2) {
                _this.$alert('登录成功! 用户类型： '+resp2.data, 'OK', {
                  confirmButtonText: '确定',
                  callback: action => {
                    if(resp2.data=="staff"){
                      _this.$router.push('/staffHome');
                    }
                    if(resp2.data=="boss"){
                     _this.$router.push('/bossHome');

                  }

                  }
                });


              })
            } else {
              _this.$alert('登录失败!', 'OK', {
                confirmButtonText: '确定',

              });
            }


          })
        } else {
          console.log('error submit!!');
          return false;
        }
      })



    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>