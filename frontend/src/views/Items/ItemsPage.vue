<template>
  <div class="container">
    <Sidebar />
    <div class="items-header">
      <h1>{{ $route.query.category }}</h1>

      <div>
        <div
          :style="arrowStyle"
          style="display: inline-block; cursor: pointer"
          @click="onSortArrow"
        >
          <font-awesome-icon icon="arrow-down" />
        </div>
        <select id="sort-dropdown">
          <option value="id">Created</option>
          <option value="price">Price</option>
        </select>
      </div>
    </div>
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
      ascending: true,
    };
  },

  computed: {
    arrowStyle() {
      return this.ascending ? "" : "transform: rotate(180deg)";
    },

    filters() {
      let query = this.$route.query;

      return { ...query, sort: query.sort };
    },
  },

  methods: {
    onSortArrow() {
      this.ascending = !this.ascending;
      this.$router.push({ name: "items", params: this.filters });
    },

    fetchItems() {
      apiService
        .getItems(this.filters, this.page, 15)
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

  setup() {
    return { sidebarWidth };
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

#sort-dropdown {
  height: 2rem;
  padding: 5px;
  margin: 15px;
  text-align: center;
}

.items-header {
  display: flex;
  margin-left: 50%;
  margin-right: 7%;
  justify-content: space-between;
}
</style>
