<template>
  <el-header class="fater-header">
    <img
      src="@/assets/img/1.png"
      style="width: 71px; height: 55px; margin-left: -23px; margin-top: 5px"
    />
    <div class="fater-header-logo" style="margin-left: 6px">社团管理系统</div>
    <!--		<el-dropdown @command="handleUser" class="fater-header-user">-->
    <div class="info">
      <el-dropdown @command="handleUser">
        <span class="user-info">
          <el-avatar
            :size="40"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            style="margin-right: 2px"
          />
          <!--           <el-tag  type="success"  v-if="loginUser.type==0">管理员</el-tag>-->
          <!--                    <el-tag type="success" v-if="loginUser.type==2">用户</el-tag>-->
          <!--                    <el-tag type="success" v-if="loginUser.type==1">社长</el-tag>-->
          <span class="name" v-if="loginUser.type == 0">
            管理员：{{ loginUser.name }}</span
          >
          <span class="name" v-if="loginUser.type == 1">
            社长：{{ loginUser.name }}</span
          >
          <span class="name" v-if="loginUser.type == 2">
            用户：{{ loginUser.name }}</span
          >
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="info">
              <i slot="prefix" class="el-input__icon el-icon-user"></i>
              <span>个人信息</span>
            </el-dropdown-item>
            <el-dropdown-item divided command="pwd">
              <el-icon><InfoFilled /></el-icon>
              <span>修改密码</span>
            </el-dropdown-item>
            <el-dropdown-item command="exit">
              <el-icon><Unlock /></el-icon>
              <span>退出系统</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
        <!--          <el-dropdown-menu slot="dropdown">-->
        <!--        <el-dropdown-item command="info">个人信息</el-dropdown-item>-->
        <!--        <el-dropdown-item command="pwd">修改密码</el-dropdown-item>-->
        <!--        <el-dropdown-item command="exit">退出系统</el-dropdown-item>-->
        <!--      </el-dropdown-menu>-->
      </el-dropdown>
    </div>
    <!-- 2.个人信息 -->

    <!--			<span class="el-dropdown-link">个人中心</span>-->

    <!--			<el-dropdown-menu slot="dropdown">-->
    <!--				<el-dropdown-item command="info">个人信息</el-dropdown-item>-->
    <!--				<el-dropdown-item command="pwd">修改密码</el-dropdown-item>-->
    <!--				<el-dropdown-item command="exit">退出系统</el-dropdown-item>-->
    <!--			</el-dropdown-menu>-->
    <!--		</el-dropdown>-->

    <el-dialog
      title="修改信息"
      width="700px"
      :append-to-body="true"
      :visible.sync="showUpdInfoFlag"
    >
      <el-form label-width="80px" :model="userForm">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户账号">
              <el-input
                v-model="userForm.userName"
                placeholder="请输入用户账号…"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户密码">
              <el-input
                v-model="userForm.passWord"
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
                v-model="userForm.name"
                placeholder="请输入用户姓名…"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户年龄">
              <el-input
                v-model="userForm.age"
                placeholder="请输入用户年龄…"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-radio-group v-model="userForm.gender">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话">
              <el-input
                v-model="userForm.phone"
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
            v-model="userForm.address"
            placeholder="请输入联系地址…"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showUpdInfoFlag = false">取 消</el-button>
        <el-button type="primary" @click="updInfo()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="修改密码"
      width="600px"
      :append-to-body="true"
      :visible.sync="showUpdPwdFlag"
    >
      <el-form
        label-width="80px"
        ref="userPwd"
        :model="userPwd"
        :rules="userPwdFormRules"
      >
        <el-form-item label="原始密码" prop="oldPwd">
          <el-input
            type="password"
            v-model="userPwd.oldPwd"
            placeholder="请输入原始密码……"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="修改密码" prop="newPwd">
          <el-input
            type="password"
            v-model="userPwd.newPwd"
            placeholder="请输入修改密码……"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="rePwd">
          <el-input
            type="password"
            v-model="userPwd.rePwd"
            placeholder="请再次确认密码……"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showUpdPwdFlag = false">取 消</el-button>
        <el-button type="primary" @click="updPwd('userPwd')">确 定</el-button>
      </div>
    </el-dialog>
  </el-header>
</template>

<style>
.info .user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  margin-left: 1200px;
  right: 35px;
  color: #fff;
  font-size: 14px;
}
.info .user-info .name {
  margin-left: 10px;
}
.info {
  :global(.el-dropdown-menu__item) {
    line-height: 36px !important;
    padding: 6px 22px;
  }
}
</style>

<script>
import {
  exit,
  getLoginUser,
  checkUserPwd,
  updLoginUserInfo,
  updLoginUserPwd,
} from '../api'

export default {
  data() {
    var checkOldPwd = async (rule, value, callback) => {
      if (value) {
        await checkUserPwd(this.$store.state.token, value).then((resp) => {
          if (resp.code != 0) {
            callback(new Error('原始密码输入错误'))
          }
        })
      } else {
        callback(new Error('原始密码必须输入'))
      }
      callback()
    }
    var checkNewPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('修改密码必须输入'))
      }

      callback()
    }
    var checkRePwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('确认密码必须输入'))
      }

      if (value != this.userPwd.newPwd) {
        callback(new Error('两次输入密码不一致'))
      }

      callback()
    }
    return {
      loginUser: {},
      showUpdInfoFlag: false,
      showUpdPwdFlag: false,
      userPwd: {
        oldPwd: '',
        newPwd: '',
        rePwd: '',
      },
      userPwdFormRules: {
        oldPwd: [
          {
            validator: checkOldPwd,
            trigger: 'blur',
          },
        ],
        newPwd: [
          {
            validator: checkNewPwd,
            trigger: 'blur',
          },
        ],
        rePwd: [
          {
            validator: checkRePwd,
            trigger: 'blur',
          },
        ],
      },
      userForm: {
        id: '',
        userName: '',
        passWord: '',
        name: '',
        gender: '',
        age: '',
        phone: '',
        address: '',
      },
    }
  },
  methods: {
    handleUser(comm) {
      if (comm == 'info') {
        this.showUpdInfoWin()
      }

      if (comm == 'pwd') {
        this.showUpdPwdWin()
      }

      if (comm == 'exit') {
        this.$confirm('确认要退出吗？', '系统提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          exit(this.$store.state.token).then(() => {
            this.$store.commit('clearToken')
            this.$store.commit('clearMenus')
            sessionStorage.clear()
            this.$router.push('/')
          })
        })
      }
    },
    initUserPwd() {
      this.userPwd = {
        oldPwd: '',
        newPwd: '',
        rePwd: '',
      }
    },
    initUserForm() {
      this.userForm = {
        id: '',
        userName: '',
      }
    },
    showUpdInfoWin() {
      getLoginUser(this.$store.state.token).then((resp) => {
        this.initUserForm()
        this.userForm = resp.data
        this.showUpdInfoFlag = true
      })
    },
    showUpdPwdWin() {
      this.initUserPwd()
      this.showUpdPwdFlag = true
    },
    updInfo() {
      this.userForm.token = this.$store.state.token
      updLoginUserInfo(this.userForm).then((resp) => {
        this.$message({
          message: resp.msg,
          type: 'success',
        })

        this.showUpdInfoFlag = false
        this.initUserForm()
      })
    },
    updPwd(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updLoginUserPwd(this.$store.state.token, this.userPwd.newPwd).then(
            (resp) => {
              this.$message({
                message: resp.msg,
                type: 'success',
              })

              this.showUpdPwdFlag = false
              this.initUserPwd()
            }
          )
        } else {
          return false
        }
      })
    },
  },
  mounted() {
    getLoginUser(this.$store.state.token).then((resp) => {
      this.loginUser = resp.data
    })
  },
}
</script>
