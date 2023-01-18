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
import {onMounted, reactive, ref} from "vue";
import {FormInst, NButton} from "naive-ui";
import {IPage, ISearch} from "@/util/model";
import http from "@/util/http";
import {normalTime} from "@/util/time";

const data = reactive({
  search: <ISearch>{},
  moveOut: <IPage>{}
})

const refresh = () => {
  http.post("/moveOut/all", {current: data.moveOut.current, ...data.search}).then(r => {
    data.moveOut = r.data.content
  })
}

const page = (page: number) => {
  data.moveOut.current = page
  refresh()
}

onMounted(() => {
  refresh()
})

const columns = [
  {
    title: "ID",
    key: "id"
  },
  {
    title: "Student",
    key: "student_name",
  },
  {
    title: "Dormitory",
    key: "dormitory_name",
  },
  {
    title: "Reason",
    key: "reason",
  },
  {
    title: "Create Date",
    key: "create_time",
    render(row: any) {
      return normalTime(row.create_time)
    }
  }
]

const options = [
  {
    label: "Student",
    value: 0
  },
  {
    label: "Dormitory",
    value: 1
  }
]

const searchRules = {
  field: {
    required: true,
    type: "number",
    message: 'Please select field',
    trigger: "blur"
  },
  value: {
    required: true,
    message: 'Please input value',
    trigger: 'blur'
  }
}
const searchRef = ref<FormInst | null>(null)

const search = () => {
  searchRef.value?.validate((errors) => {
    if (!errors) {
      refresh()
    } else {
      window.$message.error(errors[0][0].message)
    }
  })
}
</script>

<template>
  <n-card title="Search">
    <div style="display: flex;justify-content: space-between">
      <n-form ref="searchRef" label-placement="left" :model="data.search" :rules="searchRules" inline>
        <n-form-item label="Field" path="field">
          <n-select :options="options" v-model:value="data.search.field" style="min-width: 200px"/>
        </n-form-item>

        <n-form-item label="Value" path="value">
          <n-input v-model:value="data.search.value"/>
        </n-form-item>

        <n-form-item>
          <n-button attr-type="button" @click="search">
            Search
          </n-button>
        </n-form-item>
      </n-form>
      <n-button type="primary">
        Add
      </n-button>
    </div>
  </n-card>
  <n-data-table :data="data.moveOut.records" :columns="columns"/>
  <div style="display: flex;justify-content: center">
    <n-pagination v-model:page="data.moveOut.current" :page-count="data.moveOut.pages" :on-update:page="page"/>
  </div>
</template>

<style scoped>

</style>
