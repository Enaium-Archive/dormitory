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

interface Publish {
  id: number
  building: number
  name: string
  type: number
  phone: string
}

const data = reactive({
  search: <ISearch>{},
  dormitory: <IPage>{},
  publish: <Publish>{},
  showPublish: false,
  buildings: [] as any
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
    title: "Name",
    key: "name",
  },
  {
    title: "Type",
    key: "type",
  },
  {
    title: "Available",
    key: "available",
  },
  {
    title: "phone",
    key: "telephone",
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
                http.get(`/dormitory/get/${row.id}`).then(r => {
                  const dormitory = r.data.content
                  data.publish.id = dormitory.id
                  data.publish.building = dormitory.buildingId
                  data.publish.name = dormitory.name
                  data.publish.type = dormitory.type
                  data.publish.phone = dormitory.telephone
                  data.showPublish = true
                })
              }
            }, "Edit"),
            h(NButton, {
              type: "error",
              onClick: () => {
                window.$dialog.warning({
                  title: 'Warning',
                  content: 'Do you want to delete it?',
                  positiveText: 'Yes',
                  negativeText: 'No',
                  onPositiveClick: () => {
                    http.get(`/dormitory/delete/${row.id}`).then(r => {
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
    label: "Name",
    value: 0
  },
  {
    label: "Phone",
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
  building: {
    required: true,
    type: "number",
    message: 'Please select',
    trigger: 'blur'
  },
  name: {
    required: true,
    message: 'Please input value',
    trigger: 'blur'
  },
  type: {
    required: true,
    type: "number",
    message: 'Please input value',
    trigger: "blur"
  },
  phone: {
    required: true,
    message: 'Please input value',
    trigger: "blur"
  }
}

const refresh = () => {
  http.post("/dormitory/all", {current: data.dormitory.current, ...data.search}).then(r => {
    data.dormitory = r.data.content
  })
}

const page = (page: number) => {
  data.dormitory.current = page
  refresh()
}

onMounted(() => {
  http.post("/building/all").then(r => {
    r.data.content.records.forEach((it: any) => {
      data.buildings.push({
        label: it.name,
        value: it.id,
      })
    })
  })
  refresh()
})


const searchRef = ref<FormInst | null>(null)
const publishRef = ref<FormInst | null>(null)

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
      http.post("/dormitory/publish", data.publish).then(r => {
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
      <n-form-item label="Building" path="building">
        <n-select :options="data.buildings" v-model:value="data.publish.building"/>
      </n-form-item>
      <n-form-item label="Name" path="name">
        <n-input v-model:value="data.publish.name"/>
      </n-form-item>
      <n-form-item label="Type" path="type">
        <n-input-number v-model:value="data.publish.type"/>
      </n-form-item>
      <n-form-item label="Phone" path="phone">
        <n-input v-model:value="data.publish.phone"/>
      </n-form-item>
      <n-form-item>
        <n-button attr-type="button" @click="publish">
          Publish
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
  <n-data-table :data="data.dormitory.records" :columns="columns"/>
  <div style="display: flex;justify-content: center">
    <n-pagination v-model:page="data.dormitory.current" :page-count="data.dormitory.pages" :on-update:page="page"/>
  </div>
</template>

<style scoped>

</style>
