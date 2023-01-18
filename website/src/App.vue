<script setup lang="ts">

import Message from "@/components/Message.vue";
import Dialog from "@/components/Dialog.vue";
import {useRouter} from "vue-router";

window.$router = useRouter()
</script>

<template>
  <n-message-provider>
    <Message/>
  </n-message-provider>

  <n-dialog-provider>
    <Dialog/>
  </n-dialog-provider>

  <router-view/>
</template>

<style scoped>

</style>
