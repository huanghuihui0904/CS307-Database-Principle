<template>
  <div style="width: 100vw;padding-left: 10px;padding-right: 10px"  >

    <div style="margin-outside: 10px">
      <el-input  v-model="content"  placeholder="请输入搜索的关键词" style="width: 40%; padding: 10px" ></el-input>

      <el-button type="primary" @click="test" style="margin-left: 10px">Enter</el-button>
      <el-button type="primary" @click="getCount"  style="margin-left: 10px">总计</el-button>
      <el-button type="primary" @click="add">增加</el-button>
      <el-button type="primary" @click="back">返回</el-button>
      <el-button type="primary" style="margin-left: 10px"
                 @click="select">搜索</el-button>

    </div>

    <el-table

        :data="tableData"
        border
        :row-style="{height:'53px'}"
        :cell-style="{padding:'0px'}"
        style="width: 100%;height: 583px"
        :default-sort = "{prop: 'id', order: 'increasing'}"
    >
      <el-table-column

          prop="id"
          label="id"

          sortable>
      </el-table-column>
      <el-table-column

          prop="name"
          label="name"

          sortable>
      </el-table-column>


      <el-table-column
          fixed="right"
          label="操作">
        <template slot-scope="scope">
          <el-button @click="update(scope.row)" type="text" >更改</el-button>
          <el-button @click="deleteCenter(scope.row)" type="text" >删除</el-button>

        </template>
      </el-table-column>
    </el-table>

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
          title="新增Center"
          :visible.sync="dialogVisible"
          width="50%">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

          <el-form-item label="id" prop="id" label-width="20%">
            <el-input v-model="ruleForm.id" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="name" prop="name" label-width="20%">
            <el-input v-model="ruleForm.name" style="width: 80%"></el-input>
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
          title="更新Center"
          :visible.sync="dialogVisible2"
          width="50%">
        <el-form :model="ruleForm2" :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm2">

          <el-form-item label="id" prop="id" label-width="20%">
            <el-input v-model="ruleForm2.id" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="姓名" prop="name" label-width="20%">
            <el-input v-model="ruleForm2.name" style="width: 80%"></el-input>
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
  name:"Center",

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
        ],
        name: [
          {required: true, message: '请输入name', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ]

      },
      gridData: [],
      dialogTableVisible: false,
      restaurants: [],
      state: '',



      tableData: [

      ]
    }
  },
  methods: {
test() {



  axios.post('/center/findbyName/' + this.content).then(function (resp) {

    console.log("111");

  })

    axios.post('/center/findId/' + this.content).then(function (resp) {
      console.log("eeeor");
    })

},

    update(row) {
      const _this = this
      axios.get('/center/findById/' + row.id).then(function (resp) {
        _this.ruleForm2 = resp.data
      })
      this.dialogVisible2 = true
      this.ruleForm2 = {}
    },
    create() {
      const _this = this
      axios.get('/center/findById/' + this.$route.query.id).then(function (resp) {
        _this.ruleForm = resp.data
      })

    },
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post("/center/save", this.ruleForm).then(function (resp) {

            if (resp.data == "success") {
              _this.$alert('添加成功!', 'OK', {
                confirmButtonText: '确定',
                callback: action => {

                  window.location.reload()
                  // this.loading= true
                }
              });

            } else {
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
          axios.put("/center/update", this.ruleForm2).then(function (resp) {

            if (resp.data == "success") {
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
    add() {
      this.dialogVisible = true
      // this.form={}
      this.ruleForm = {}
      // this.gridData=[]
    },
    findbyName(){
      const _this = this
      console.log(this.content);
      // axios.get('/center/findById/' +this.$refs.content).then(function (resp) {
      //   // _this.content = resp.data
      // })
    },


    getCount() {
      const _this = this
      axios.get('/center/get').then(function (resp) {
        console.log(resp)
        _this.$alert(resp.data);


      })
    },
    deleteCenter(row) {
      const _this = this
      axios.delete('/center/deleteById/' + row.id).then(function (resp) {
        _this.$alert('删除成功!', 'OK', {
          confirmButtonText: '确定',
          callback: action => {
            window.location.reload()

          }
        });


      })
    },
    select() {
      // this.dialogTableVisible=true

      //

      // let flag;
      // // flag=false;
      const _this = this
      // axios.post('/center/findbyName/' + this.content).then(function (resp) {
      //   flag=resp.data;
      //   console.log(flag);
      //
      // })
      // if (!flag) {
      //   axios.post('/center/findId/' + this.content).then(function (resp) {
      //     console.log("eeeor");
      //   })
      // }else {
      //   console.log(flag);
      // }

      axios.get('/center1/findAll/1/10').then(function (resp) {

        _this.tableData = resp.data.content

        _this.total = resp.data.totalElements
        console.log(resp)


      })



    },
    back(){
      const _this = this
      axios.post('/center/find/1/10').then(function (resp) {
        _this.tableData = resp.data.content

        _this.total = resp.data.totalElements
        console.log(resp)
      })

  },



  page(currentPage) {
    const _this = this
    axios.get('/center/findAll/' + currentPage + '/10').then(function (resp) {
      _this.tableData = <resp className="data content"></resp>
      _this.total = resp.data.totalElements
      console.log(resp)
    })
  }
},
created() {
  const _this = this
  axios.get('/center/findAll/1/10').then(function (resp) {
    _this.tableData = resp.data.content

    _this.total = resp.data.totalElements
    console.log(resp)
  })
}
}
</script>