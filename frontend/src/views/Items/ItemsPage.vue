<template>
  <h1>Hei</h1>
  <Sidebar />
</template>

<script>
import Sidebar from "@/components/Sidebar/SidebarComponent";
import { sidebarWidth } from "@/components/Sidebar/state";
export default {
  components: { Sidebar },
  setup() {
    return { sidebarWidth };
  },
};
</script>
