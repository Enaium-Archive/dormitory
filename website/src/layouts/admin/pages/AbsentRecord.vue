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
import {FormInst} from "naive-ui";
import http from "@/util/http";
import {normalTime} from "@/util/time";
import {IPage, ISearch} from "@/util/model";

const data = reactive({
  form: <ISearch>{},
  absent: <IPage>{}
})

const refresh = () => {
  http.post("/absent/all", {current: data.absent.current, ...data.form}).then(r => {
    data.absent = r.data.content
  })
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
    title: "Building",
    key: "building_name",
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
    title: "Admin",
    key: "admin_name",
  },
  {
    title: "Create Date",
    key: "create_date",
    render(row: any) {
      return normalTime(row.create_date)
    }
  },
]

const options = [
  {
    label: "Building",
    value: 0
  },
  {
    label: "Dormitory",
    value: 1
  }
]

const rules = {
  field: {
    required: true,
    type: "number",
    message: 'Please select field',
    trigger: "blur"
  },
  value: {
    required: true,
    message: "Please input value",
    trigger: "blur"
  }
}

const formRef = ref<FormInst | null>(null)

const search = () => {
  formRef.value?.validate((errors) => {
    if (!errors) {
      http.post("/absent/all", {current: data.absent.current, ...data.form}).then(r => {
        data.absent = r.data.content
      })
    } else {
      window.$message.error(errors[0][0].message)
    }
  })
}

const page = (page: number) => {
  data.absent.current = page
  refresh()
}

</script>

<template>
  <n-card title="Search">
    <n-form ref="formRef" label-placement="left" :model="data.form" :rules="rules" inline>
      <n-form-item label="Field" path="field">
        <n-select :options="options" v-model:value="data.form.field" style="min-width: 200px"/>
      </n-form-item>

      <n-form-item label="Value" path="value">
        <n-input v-model:value="data.form.value"/>
      </n-form-item>

      <n-form-item>
        <n-button attr-type="button" @click="search">
          Search
        </n-button>
      </n-form-item>
    </n-form>
  </n-card>
  <n-data-table :columns="columns" :data="data.absent.records"/>
  <div style="display: flex;justify-content: center">
    <n-pagination v-model:page="data.absent.current" :page-count="data.absent.pages" :on-update:page="page"/>
  </div>
</template>

<style scoped>

</style>
