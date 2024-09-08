<template>
  <el-aside class="fater-left">
      <el-menu active-text-color="#009999" :default-active="this.$router.currentRoute.path" router>
          <template v-for="(item, index) in menuList">
            <el-menu-item :key="index" :index="item.path">
                <span :class="item.icon"></span>
                <span slot="title">{{ item.name }}</span>
            </el-menu-item>
          </template>
      </el-menu>
  </el-aside>
</template>

<style>
</style>

<script>

    export default{
      props: {
        menuList: []
      },

    }
</script>
