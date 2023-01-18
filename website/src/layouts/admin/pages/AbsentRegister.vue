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

const data = reactive({
  form: {
    building: '',
    dormitory: '',
    student: '',
    reason: '',
    date: new Date().getTime()
  },
  buildings: [] as any,
  dormitories: [] as any,
  students: [] as any
})

onMounted(() => {
  http.post("/building/all").then(r => {
    r.data.content.records.forEach((it: any) => {
      data.buildings.push({
        label: it.name,
        value: it.id
      })
    })
  })
  http.post("/dormitory/all").then(r => {
    r.data.content.records.forEach((it: any) => {
      data.dormitories.push({
        label: it.name,
        value: it.id
      })
    })
  })
  http.post("/student/all").then(r => {
    r.data.content.records.forEach((it: any) => {
      data.students.push({
        label: it.name,
        value: it.id
      })
    })
  })
})

const rules = {
  building: {
    required: true,
    type: "number",
    message: "Please select building",
    trigger: "blur"
  },
  dormitory: {
    required: true,
    type: "number",
    message: "Please select dormitory",
    trigger: "blur"
  },
  student: {
    required: true,
    type: "number",
    message: "Please select student",
    trigger: "blur"
  },
  reason: {
    required: true,
    message: "Please input reason",
    trigger: "blur"
  },
  date: {
    type: "number",
    required: true,
    message: "Please pick date",
    trigger: "blur"
  }
}

const formRef = ref<FormInst | null>(null)

const submit = () => {
  formRef.value?.validate((errors) => {
    if (!errors) {
      http.post("/absent/insert", data.form)
    } else {
      window.$message.error(errors[0][0].message)
    }
  })
}
</script>

<template>
  <div style="display: flex;justify-content: center">
    <n-card title="Absent Record" style="max-width: 400px">
      <n-form ref="formRef" :model="data.form" :rules="rules" label-placement="left" label-width="auto">
        <n-form-item label="Building" path="building">
          <n-select :options="data.buildings" v-model:value="data.form.building"/>
        </n-form-item>
        <n-form-item label="Dormitory" path="dormitory">
          <n-select :options="data.dormitories" v-model:value="data.form.dormitory"/>
        </n-form-item>
        <n-form-item label="Student" path="student">
          <n-select :options="data.students" v-model:value="data.form.student"/>
        </n-form-item>
        <n-form-item label="Reason" path="reason">
          <n-input v-model:value="data.form.reason"/>
        </n-form-item>
        <n-form-item label="Date" path="date">
          <n-date-picker v-model:value="data.form.date" type="datetime" clearable/>
        </n-form-item>
        <n-form-item>
          <n-button attr-type="button" @click="submit">
            Submit
          </n-button>
        </n-form-item>
      </n-form>
    </n-card>
  </div>
</template>

<style scoped>

</style>
