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
import {h, onMounted, reactive, ref} from "vue";
import {FormInst, NButton, NButtonGroup} from "naive-ui";
import {IPage, ISearch} from "@/util/model";
import http from "@/util/http";
import {normalTime} from "@/util/time";

interface Publish {
  id: number
  dormitory: number
  number: string
  name: string
  gender: boolean
}

interface MoveOut {
  reason: string
  student: number
}

const data = reactive({
  search: <ISearch>{},
  publish: <Publish>{},
  student: <IPage>{},
  moveOut: <MoveOut>{},
  showPublish: false,
  showMoveOut: false,
  dormitories: [] as any
})

const refresh = () => {
  http.post("/student/all", {current: data.student.current, ...data.search}).then(r => {
    data.student = r.data.content
  })
}

const page = (page: number) => {
  data.student.current = page
  refresh()
}

onMounted(() => {
  http.post("/dormitory/all").then(r => {
    r.data.content.records.forEach((it: any) => {
      data.dormitories.push({
        label: it.name,
        value: it.id,
      })
    })
  })
  refresh()
})

const columns = [
  {
    title: "ID",
    key: "id"
  },
  {
    title: "Dormitory",
    key: "dormitory_name",
  },
  {
    title: "Number",
    key: "number",
  },
  {
    title: "Name",
    key: "name",
  },
  {
    title: "Gender",
    key: "gender",
    render(row: any) {
      return row.gender === true ? "Male" : "Female"
    }
  },
  {
    title: "State",
    key: "state",
  },
  {
    title: "Create Date",
    key: "create_time",
    render(row: any) {
      return normalTime(row.create_time)
    }
  },
  {
    title: "Operate",
    key: "operate",
    fixed: 'right',
    render(row: any) {
      return h(
          NButtonGroup, [
            h(NButton, {
              type: "primary",
              onClick: () => {
                http.get(`/student/get/${row.id}`).then(r => {
                  const building = r.data.content
                  data.publish.id = building.id
                  data.publish.name = building.name
                  data.publish.gender = building.gender !== 0
                  data.publish.dormitory = building.dormitoryId
                  data.publish.number = building.number
                  data.showPublish = true
                })
              }
            }, "Edit"),
            h(NButton, {
              type: "warning",
              onClick: () => {
                data.showMoveOut = true
                data.moveOut.student = row.id
              }
            }, "Move Out"),
            h(NButton, {
              type: "error",
              onClick: () => {
                window.$dialog.warning({
                  title: 'Warning',
                  content: 'Do you want to delete it?',
                  positiveText: 'Yes',
                  negativeText: 'No',
                  onPositiveClick: () => {
                    http.get(`/student/delete/${row.id}`).then(r => {
                      if (r.data.code === 200) {
                        window.$message.success("Success")
                      }
                    })
                  }
                })
              }
            }, "Delete")
          ]
      )
    }
  },
]

const options = [
  {
    label: "Number",
    value: 0
  },
  {
    label: "Name",
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

const publishRules = {
  dormitory: {
    required: true,
    type: "number",
    message: 'Please select',
    trigger: 'blur'
  },
  number: {
    required: true,
    message: 'Please input value',
    trigger: 'blur'
  },
  name: {
    required: true,
    message: 'Please input value',
    trigger: "blur"
  },
  gender: {
    required: true,
    type: "boolean",
    message: 'Please select',
    trigger: "blur"
  }
}

const moveOutRules = {
  reason: {
    required: true,
    message: 'Please input value',
    trigger: 'blur'
  }
}

const searchRef = ref<FormInst | null>(null)
const publishRef = ref<FormInst | null>(null)
const moveOutRef = ref<FormInst | null>(null)

const search = () => {
  searchRef.value?.validate((errors) => {
    if (!errors) {
      refresh()
    } else {
      window.$message.error(errors[0][0].message)
    }
  })
}

const publish = () => {
  publishRef.value?.validate((errors) => {
    if (!errors) {
      http.post("/student/publish", data.publish).then(r => {
        if (r.data.code === 200) {
          window.$message.success("Success")
        }
      })
    } else {
      window.$message.error(errors[0][0].message)
    }
  })
}

const moveOut = () => {
  moveOutRef.value?.validate((errors) => {
    if (!errors) {
      http.post("/moveOut/out", data.moveOut).then(r => {
        if (r.data.code === 200) {
          window.$message.success("Success")
        }
      })
    } else {
      window.$message.error(errors[0][0].message)
    }
  })
}
</script>

<template>
  <n-modal
      v-model:show="data.showPublish"
      preset="dialog"
      title="Add Building"
      @after-leave="data.publish = {}"
  >
    <n-form ref="publishRef" :model="data.publish" :rules="publishRules" label-placement="left" label-width="auto">
      <n-form-item label="Dormitory" path="dormitory">
        <n-select :options="data.dormitories" v-model:value="data.publish.dormitory"/>
      </n-form-item>

      <n-form-item label="Number" path="number">
        <n-input v-model:value="data.publish.number"/>
      </n-form-item>

      <n-form-item label="Name" path="name">
        <n-input v-model:value="data.publish.name"/>
      </n-form-item>

      <n-form-item label="Gender" path="gender">
        <n-select :options="[{label:'Male',value:true},{label:'Female',value:false}]"
                  v-model:value="data.publish.gender"/>
      </n-form-item>

      <n-form-item>
        <n-button attr-type="button" @click="publish">
          Publish
        </n-button>
      </n-form-item>
    </n-form>
  </n-modal>

  <n-modal
      v-model:show="data.showMoveOut"
      preset="dialog"
      title="Move Out"
      @after-leave="data.moveOut = {}"
  >
    <n-form
        ref="moveOutRef"
        :model="data.moveOut"
        :rules="moveOutRules"
        label-placement="left"
        label-width="auto">
      <n-form-item label="Reason" path="reason">
        <n-input v-model:value="data.moveOut.reason"/>
      </n-form-item>
      <n-form-item>
        <n-button attr-type="button" @click="moveOut">
          Move Out
        </n-button>
      </n-form-item>
    </n-form>
  </n-modal>

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
      <n-button type="primary" @click="data.showPublish = true">
        Add
      </n-button>
    </div>
  </n-card>
  <n-data-table :data="data.student.records" :columns="columns"/>
  <div style="display: flex;justify-content: center">
    <n-pagination v-model:page="data.student.current" :page-count="data.student.pages" :on-update:page="page"/>
  </div>
</template>

<style scoped>

</style>
