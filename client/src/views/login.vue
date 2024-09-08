<template>
  <div
    class="login-wrap"
    style="
      height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: rgb(44 81 175);
    "
  >
    <div
      style="
        display: flex;
        background-color: white;
        width: 54%;
        border-radius: 5px;
        overflow: hidden;
      "
    >
      <div style="flex: 1">
        <img src="@/assets/6.png" alt="" style="width: 450px; height: 400px" />
      </div>
      <div
        style="
          flex: 1;
          display: flex;
          align-items: center;
          justify-content: center;
        "
      >
        <el-form
          :model="loginForm"
          style="width: 80%"
          :rules="rules"
          ref="loginForm"
        >
          <div
            style="
              font-size: 20px;
              font-weight: bold;
              text-align: center;
              margin-bottom: 20px;
            "
          >
            欢迎登录社团管理系统
          </div>
          <el-form-item prop="userName">
            <!--必须绑定v-model输入框才能输入字符---->
            <el-input
              v-model="loginForm.userName"
              placeholder="请输入用户账号"
              autocomplete="off"
            >
              <i slot="prefix" class="el-input__icon el-icon-user"></i>
            </el-input>
          </el-form-item>

          <el-form-item prop="passWord">
            <el-input
              v-model="loginForm.passWord"
              size="medium"
              placeholder="请输入密码"
              clearable
              show-password
              autocomplete="off"
            >
              <i slot="prefix" class="el-input__icon el-icon-lock"></i>
            </el-input>
          </el-form-item>

          <!-- 验证码 -->
          <el-form-item prop="verifyCode">
            <div class="login-card-line">
              <el-input
                v-model="loginForm.verifyCode"
                placeholder="验证码"
                prefix-icon="el-icon-key"
              >
                <template slot="append">
                  <el-image
                    style="width: 100px"
                    :src="code_url"
                    @click="getVerifyCode"
                    title="看不清？点击切换"
                  ></el-image>
                </template>
              </el-input>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              style="width: 100%"
              @click="submitForm('loginForm')"
              >登 录</el-button
            >
          </el-form-item>

          <div style="display: flex">
            <div style="flex: 1">
              还没有账号？请
              <span
                style="color: #0f9876; cursor: pointer"
                @click="showAddWin()"
                >注册</span
              >
            </div>
          </div>
        </el-form>
      </div>
    </div>
    <el-dialog
      title="用户注册"
      width="700px"
      :modal="false"
      :visible.sync="showAddFlag"
    >
      <el-form
        label-width="90px"
        :model="usersForm"
        :rules="usersFormRules"
        ref="usersForm"
      >
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户账号">
              <el-input
                v-model="usersForm.userName"
                placeholder="请输入用户账号…"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户密码">
              <el-input
                v-model="usersForm.passWord"
                type="password"
                placeholder="请输入用户密码…"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户姓名">
              <el-input
                v-model="usersForm.name"
                placeholder="请输入用户姓名…"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户年龄">
              <el-input
                v-model="usersForm.age"
                placeholder="请输入用户年龄…"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-radio-group v-model="usersForm.gender">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话">
              <el-input
                v-model="usersForm.phone"
                placeholder="请输入联系电话…"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="联系地址">
          <el-input
            rows="4"
            type="textarea"
            v-model="usersForm.address"
            placeholder="请输入联系地址…"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showAddFlag = false">取 消</el-button>
        <el-button type="primary" @click="addInfo('usersForm')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<style>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background: #0f9876;
  background-image: url('../assets/img/login-bg.svg');
  background-size: 100% 100%;
}
</style>

<script>
import initMenu from '../utils/menus.js'
import { login, addUsers, getVerifyCodeImage } from '../api/index.js'

export default {
  data() {
    return {
      showAddFlag: false,
      usersForm: {
        id: '',
        userName: '',
        passWord: '',
        name: '',
        gender: '',
        age: '',
        phone: '',
        address: '',
        type: 2,
        status: 1,
      },
      loginForm: {
        userName: '',
        passWord: '',
        verifyCode: '',
      },

      code_url: '',
      usersFormRules: {
        userName: [
          {
            required: true,
            message: '用户账号必须输入',
            trigger: 'blur',
          },
        ],
        passWord: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            message: '用户密码必须输入',
            trigger: 'blur',
          },
        ],
      },
      rules: {
        userName: [
          {
            required: true,
            message: '用户账号必须输入',
            trigger: 'blur',
          },
        ],
        passWord: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            message: '用户密码必须输入',
            trigger: 'blur',
          },
        ],
        verifyCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          {
            message: '验证码必须输入',
            trigger: 'blur',
          },
        ],
      },
    }
  },
  created() {
    this.getVerifyCode()
  },
  methods: {
    // 获取验证码
    async getVerifyCode() {
      getVerifyCodeImage().then((res) => {
        this.code_url = res
        console.log(res)
      })
    },

    showAddWin() {
      this.showAddFlag = true
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          login(this.loginForm).then((res) => {
            this.$store.commit('setToken', res.data)
            sessionStorage.setItem('token', res.data)
            initMenu(this.$router, this.$store)
            this.$router.push('/index')
          })
        } else {
          return false
        }
      })
    },
    addInfo(usersForm) {
      this.$refs[usersForm].validate((valid) => {
        if (valid) {
          addUsers(this.usersForm).then((resp) => {
            if (resp.code == 0) {
              this.$confirm('注册成功', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
              })
              this.showAddFlag = false
              // this.$router.push('/');
            } else {
              this.$message({
                message: resp.msg,
                type: 'warning',
              })
            }
          })
        } else {
          return false
        }
      })
    },
  },
}
</script>
