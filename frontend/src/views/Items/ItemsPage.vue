<template>
  <div class="container">
    <h1>{{ $route.query.category }}</h1>
    <Sidebar />
    <div id="items" :style="{ 'margin-left': sidebarWidth }">
      <LargeItem v-for="item in items" :key="item" :item="item" />
    </div>
  </div>
</template>

<script>
import Sidebar from "@/components/Sidebar/SidebarComponent";
import { sidebarWidth } from "@/components/Sidebar/state";
import LargeItem from "@/components/Items/LargeItem.vue";
import apiService from "@/services/apiService";
export default {
  props: ["filter"],
  components: { LargeItem, Sidebar },
  data() {
    return {
      page: 0,
      lastUpdate: Date.now(),
      items: [],
    };
  },
  setup() {
    return { sidebarWidth };
  },

  methods: {
    fetchItems() {
      apiService
        .getItems(this.$route.query, this.page, 15)
        .then((response) => {
          this.items.push(...response.data);
        })
        .catch((error) => console.log(error));
    },

    handleScroll() {
      let bottom =
        document.documentElement.scrollTop + window.innerHeight >=
        document.documentElement.offsetHeight;
      if (bottom && Date.now() - this.lastUpdate > 1000) {
        this.page += 1;
        this.fetchItems();
        this.lastUpdate = Date.now();
      }
    },

    onBeforeEnter(el) {
      el.style.opacity = 0;
    },
  },

  created() {
    this.fetchItems();
  },

  mounted() {
    window.addEventListener("scroll", this.handleScroll);
  },

  unmounted() {
    window.removeEventListener("scroll", this.handleScroll);
  },
};
</script>

<style scoped>
#items {
  transition: 0.3s ease;
}
</style>
