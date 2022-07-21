<template>
  <div style="width: 100vw;padding-left: 10px;padding-right: 10px"  >

    <div style="margin-outside: 10px">
      <el-input v-model="content" placeholder="请输入搜索的关键词" style="width: 40%; padding: 10px" ></el-input>

      <el-button type="primary" style="margin-left: 10px">Enter</el-button>
      <el-button type="primary" @click="getCount"  style="margin-left: 10px">总计</el-button>
      <el-button type="primary" @click="add">增加</el-button>
      <el-button type="primary" >返回</el-button>
      <el-button type="primary" style="margin-left: 10px"
                 @click="select">搜索</el-button>
    </div>

    <!--<button  @click="getCount">cnt</button>-->
    <el-table
        :data="tableData"
        border
        :row-style="{height:'53px'}"
        :cell-style="{padding:'0px'}"
        style="width: 100%;height: 583px"

        :default-sort = "{prop: 'id', order: 'increasing'}">
      <el-table-column

          prop="id"
          label="id"

          sortable>
      </el-table-column>
      <el-table-column

          prop="name"
          label="Name"

          sortable>
      </el-table-column>
      <el-table-column
          prop="age"
          label="Age"

          sortable>
      </el-table-column>
      <el-table-column
          prop="gender"
          label="Gender"

          sortable>
      </el-table-column>
      <el-table-column
          prop="number"
          label="Number"

          sortable>
      </el-table-column>
      <el-table-column
          prop="supply_center"
          label="Supply Center"
          width="180px"

          sortable>
      </el-table-column>
      <el-table-column
          prop="mobile_number"
          label="Mobile Number"
          width="180px"

          sortable>
      </el-table-column>
      <el-table-column
          prop="type"
          label="Type"

          sortable>
      </el-table-column>

      <el-table-column
          fixed="right"
          label="操作">
        <template slot-scope="scope">
          <el-button @click="update(scope.row)" type="text" >更改</el-button>
          <el-button @click="deleteStaff(scope.row)" type="text" >删除</el-button>


        </template>
      </el-table-column>
    </el-table >

    <div style="margin: 10px 0">
      <el-pagination
          background
          layout="prev, pager, next"
          page-size='10'
          :total="total"
          @current-change="page">

      </el-pagination>
      <!--新增Staff的弹窗-->
      <el-dialog
          title="新增Staff"
          :visible.sync="dialogVisible"
          width="50%">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

          <el-form-item label="id" prop="id" label-width="20%">
            <el-input v-model="ruleForm.id" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="姓名" prop="name" label-width="20%">
            <el-input v-model="ruleForm.name" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="年龄" prop="age" label-width="20%" >
            <el-input v-model="ruleForm.age" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="gender" label-width="20%">
            <el-radio v-model="ruleForm.gender" label="Famale">Famele</el-radio>
            <el-radio v-model="ruleForm.gender" label="Male">Male</el-radio>
            <!--                  <el-input v-model="ruleForm.gender" style="width: 80%"></el-input>-->
            <!--        <el-form-item label="gender" prop="gender" label-width="20%">-->
            <!--          <el-input v-model="ruleForm.gender" style="width: 80%"></el-input>-->
          </el-form-item>
          <el-form-item label="number" prop="number" label-width="20%">
            <el-input v-model="ruleForm.number" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="supply_center" label-width="20%" prop="supply_center">
            <el-input v-model="ruleForm.supply_center" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="mobile_number" prop="mobile_number" label-width="20%">
            <el-input v-model="ruleForm.mobile_number" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="type" prop="type" label-width="20%">
            <el-radio v-model="ruleForm.type" label="Director">Director</el-radio>
            <el-radio v-model="ruleForm.type" label="Contracts Manager">Contracts Manager</el-radio>
            <el-radio v-model="ruleForm.type" label="Supply Staff">Supply Staff </el-radio>
            <el-radio v-model="ruleForm.type" label="Salesman">Salesman</el-radio>
          </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button @click="resetForm('ruleForm');dialogVisible = false">重置</el-button>
    <el-button type="primary" @click="dialogVisible = false;submitForm('ruleForm')">确 定</el-button>

  </span>
      </el-dialog>


      <!--    更新Staff的弹窗-->
      <el-dialog
          title="更新Staff"
          :visible.sync="dialogVisible2"
          width="50%">
        <el-form :model="ruleForm2" :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm2">

          <el-form-item label="id" prop="id" label-width="20%">
            <el-input v-model="ruleForm2.id" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="姓名" prop="name" label-width="20%">
            <el-input v-model="ruleForm2.name" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="年龄" prop="age" label-width="20%" >
            <el-input v-model="ruleForm2.age" style="width: 80%"></el-input>
          </el-form-item>
          <!--        <el-form-item label="gender" label-width="20%">-->
          <!--          <el-radio v-model="ruleForm2.gender" label="Famale">Famele</el-radio>-->
          <!--          <el-radio v-model="ruleForm2.gender" label="Male">Male</el-radio>-->
          <!--                  <el-input v-model="ruleForm2.gender" style="width: 80%"></el-input>-->
          <el-form-item label="gender" prop="gender" label-width="20%">
            <el-input v-model="ruleForm2.gender" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="number" prop="number" label-width="20%">
            <el-input v-model="ruleForm2.number" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="supply_center" label-width="20%" prop="supply_center">
            <el-input v-model="ruleForm2.supply_center" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="mobile_number" prop="mobile_number" label-width="20%">
            <el-input v-model="ruleForm2.mobile_number" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="type" prop="type" label-width="20%">
            <el-input v-model="ruleForm2.type" style="width: 80%"></el-input>
          </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button @click="resetForm('ruleForm2');dialogVisible2 = false">重置</el-button>
    <el-button type="primary" @click="dialogVisible2 = false;updateForm('ruleForm2')">确 定</el-button>

  </span>
      </el-dialog>

    </div>

  </div>

</template>

<script>
export default {
  name:"Staff",
  data() {
    return {

      total: null,

      count:null,
      search:"",
      content:"",

      dialogVisible:false,
      dialogVisible2:false,
      // form:{},
      ruleForm: {

      },
      ruleForm2:{

      },
      table: {

      },
      rules: {
        id: [
          {required: true, message: '请输入id', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ]
        ,
        name: [
          {required: true, message: '请输入name', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ], age: [
          {required: true, message: '请输入age', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],

      },
      griddata:[],
      dialogTableVisible: false,
      restaurants: [],
      state: '',

      tableData: [

      ]
    }
  },
  methods: {
    // updateStaff(row){
    //   this.$router.push({
    //     path:'/updateStaff',
    //     query:{
    //       id:row.id
    //     }
    //   })
    // },

    update(row){
      const _this=this
      axios.get('/staff/findById/'+row.id).then(function (resp){
        _this.ruleForm2=resp.data
      })
      this.dialogVisible2=true
      this.ruleForm2={}
    },
    create() {
      const _this=this
      axios.get('/staff/findById/'+this.$route.query.id).then(function (resp){
        _this.ruleForm=resp.data
      })

    },
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post("/staff/save", this.ruleForm).then(function (resp) {

            if (resp.data=== "success") {
              _this.$alert('添加成功!', 'OK', {
                confirmButtonText: '确定',
                callback: action => {
                  window.location.reload()

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
    },
    updateForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.put("/staff/update", this.ruleForm2).then(function (resp) {

            if (resp.data=== "success") {
              // _this.$alert('修改成功!', 'OK', {
              //   confirmButtonText: '确定',
              //   callback: action => {
              //     window.location.reload()
              //     // _this.$router.push('/staff')
              //
              //   }
              // });
              _this.$alert('修改成功!', 'OK', {
                confirmButtonText: '确定',
                callback: action => {
                  window.location.reload()

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
    add(){
      this.dialogVisible=true
      // this.form={}
      this.ruleForm={}

    },



    getCount(){
      const _this = this
      axios.get('/staff/get' ).then(function (resp) {
        console.log(resp)
        _this.$alert(resp.data);


      })
    },
    deleteStaff(row){
      const _this = this
      axios.delete('/staff/deleteById/' + row.id).then(function (resp) {
        _this.$alert('删除成功!', 'OK', {
          confirmButtonText: '确定',
          callback: action => {
            window.location.reload()

          }
        });


      })
    },
    select(filename1,filename2){
      this.dialogTableVisible=true

      const _this = this
      var max=$("list").val();
      console.log(max);
      griddata.innerHTML

      axios.get('/staff/select',{
        column1:this.search,
        content1:this.content
      }).then(function (resp) {

      })



    },


    page(currentPage) {
      const _this = this
      axios.get('/staff/findAll/' + currentPage + '/10').then(function (resp) {
        _this.tableData = resp.data.content
        _this.total = resp.data.totalElements
        console.log(resp)
      })
    },



  },
  created() {
    const _this = this
    axios.get('/staff/findAll/1/10').then(function (resp) {
      _this.tableData = resp.data.content

      _this.total = resp.data.totalElements
      console.log(resp)
    })
  }
}
</script>