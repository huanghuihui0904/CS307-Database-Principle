<template>

  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

    <el-form-item label="id" prop="id">
      <el-input v-model="ruleForm.id"></el-input>
    </el-form-item>

    <el-form-item label="姓名" prop="name">
      <el-input v-model="ruleForm.name"></el-input>
    </el-form-item>

    <el-form-item label="年龄" prop="age">
      <el-input v-model="ruleForm.age"></el-input>
    </el-form-item>
    <el-form-item label="gender" prop="gender">
      <el-input v-model="ruleForm.gender"></el-input>
    </el-form-item>
    <el-form-item label="number" prop="number">
      <el-input v-model="ruleForm.number"></el-input>
    </el-form-item>
    <el-form-item label="supply_center" prop="supply_center">
      <el-input v-model="ruleForm.supply_center"></el-input>
    </el-form-item>
    <el-form-item label="mobile_number" prop="mobile_number">
      <el-input v-model="ruleForm.mobile_number"></el-input>
    </el-form-item>
    <el-form-item label="type" prop="type">
      <el-input v-model="ruleForm.type"></el-input>
    </el-form-item>


    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>

</template>


<script>
export default {
  data() {
    return {
      ruleForm: {
        id: '',
        name: '',
        age: '',
        gender:'',
        number:'',
        supply_center:'',
        mobile_number:'',
        type:''
      },
      rules: {
        id: [
          {required: true, message: '请输入id', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入name', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ], age: [
          {required: true, message: '请输入age', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],

      }
    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.put("/staff/update", this.ruleForm).then(function (resp) {

            if (resp.data== "success") {
              _this.$alert('修改成功!', 'OK', {
                confirmButtonText: '确定',
                callback: action => {

                  _this.$router.push('/staff')
                }
              });

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
  },
  created() {
    const _this=this
    axios.get('/staff/findById/'+this.$route.query.id).then(function (resp){
      _this.ruleForm=resp.data
    })

  }
}
</script>