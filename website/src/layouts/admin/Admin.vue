<!--
  - Copyright (c) 2023 Enaium
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy
  - of this software and associated documentation files (the "Software"), to deal
  - in the Software without restriction, including without limitation the rights
  - to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  - copies of the Software, and to permit persons to whom the Software is
  - furnished to do so, subject to the following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all
  - copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  - IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  - FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  - AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  - LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  - OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  - SOFTWARE.
  -->

<script setup lang="ts">

import Menu from "@/layouts/admin/components/Menu.vue";
import Banner from "@/components/Banner.vue";</script>

<template>
  <div style="height: 100vh; position: relative">
    <n-layout position="absolute">
      <n-layout-header style="height: 64px;" bordered>
        <Banner title="Dormitory Admin"/>
      </n-layout-header>
      <n-layout has-sider position="absolute" style="top: 64px">
        <n-layout-sider bordered content-style="padding: 24px;">
          <Menu/>
        </n-layout-sider>
        <n-layout content-style="padding: 24px;">
          <router-view/>
        </n-layout>
      </n-layout>
    </n-layout>
  </div>
</template>

<style scoped>

</style>
