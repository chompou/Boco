<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <div class="main">
      <h4 id="priceHeader">Price</h4>
      <transition>
        <div class="priceHolder" v-if="collapsed === false">
          <p>From:</p>
          <input
            v-model="priceFrom"
            placeholder="0"
            class="price"
            type="number"
            min="0"
          />
          <p>To:</p>
          <input
            v-model="priceTo"
            placeholder="0"
            class="price"
            type="number"
            min="0"
          />
        </div>
      </transition>
      <div>
        <h4 class="Header">Lease type</h4>
        <transition>
          <div class="wrapper" v-if="collapsed === false">
            <input
              type="radio"
              class="btn-check"
              name="options"
              id="option1"
              autocomplete="off"
              v-model="leaseType"
              checked
            />
            <label class="btn btn-secondary" for="option1">Hour</label>

            <input
              type="radio"
              class="btn-check"
              name="options"
              id="option2"
              v-model="leaseType"
              autocomplete="off"
            />
            <label class="btn btn-secondary" for="option2">Day</label>

            <input
              type="radio"
              class="btn-check"
              name="options"
              id="option3"
              v-model="leaseType"
              autocomplete="off"
            />
            <label class="btn btn-secondary" for="option3">Week</label>
          </div>
        </transition>
      </div>
      <div>
        <h4 class="Header">Search</h4>
        <transition>
          <div v-if="collapsed === false">
            <input
              class="baseInput"
              v-model="search"
              placeholder="Search"
              id="ItemName"
            />
          </div>
        </transition>
      </div>
      <div>
        <h4 class="Header">Category</h4>

        <transition>
          <select
            v-if="collapsed === false"
            name="Category"
            v-bind="selectedCategory"
          >
            <option
              v-for="category in categories"
              :key="category"
              :value="category.name"
            >
              {{ category.name }}
            </option>
          </select>
        </transition>
        <transition>
          <div class="Header" v-if="collapsed === false">
            <input
              type="submit"
              id="submit"
              value="Apply Filters"
              @click="onSubmit"
            />
          </div>
        </transition>
      </div>
    </div>
    <transition>
      <span
        class="collapse-icon"
        :class="{ 'rotate-180': collapsed }"
        @click="toggleSidebar"
      >
        <font-awesome-icon icon="angles-left" />
      </span>
    </transition>
  </div>
</template>

<script>
import apiService from "@/services/apiService";
import { collapsed, toggleSidebar, sidebarWidth } from "./state";

export default {
  data() {
    return {
      filters: {
        search: "",
        sort: "id",
        priceFrom: -1,
        priceTo: -1,
        category: "",
      },
      priceFrom: 0,
      priceTo: 0,
      leaseType: null,
      search: "",
      categories: [{ id: 0, name: "All" }],
    };
  },
  setup() {
    return { collapsed, toggleSidebar, sidebarWidth };
  },
  methods: {
    filter() {
      if (this.leaseType === "Day") {
        this.filters.priceFrom = this.priceFrom / 24;
        this.filters.priceTo = this.priceTo / 24;
      }
      if (this.leaseType === "Week") {
        this.filters.priceFrom = this.priceFrom / (24 * 7);
        this.filters.priceTo = this.priceTo / (24 * 7);
      }
    },
  },
  created() {
    apiService
      .getCategories()
      .then((response) => {
        this.categories.push(...response.data);
        console.log(this.categories);
      })
      .catch((error) => console.log(error));

    this.priceFrom = this.$route.query.priceFrom;
    this.priceTo = this.$route.query.priceTo;
    this.search = this.$route.query.search;
  },
};
</script>

<style scoped>
.sidebar {
  background-color: var(--main-color);
  color: white;

  float: left;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  bottom: 0;
  padding: 0.5em;

  transition: 0.3s ease;

  display: flex;
  flex-direction: column;
}

.sidebar h1 {
  height: 2.5em;
}

.collapse-icon {
  position: relative;
  bottom: 0;
  padding: 0.75em;
  font-size: 30px;

  color: rgba(255, 255, 255, 0.7);

  transition: 0.2s linear;
}

.rotate-180 {
  transform: rotate(180deg);
  transition: 0.2s linear;
}

#priceHeader {
  margin-top: 80px;
}

.Header {
  margin-top: 20px;
}

.priceHolder {
  display: flex;
}

p {
  margin-left: 15px;
  margin-right: 5px;
  margin-bottom: 0px;
}

h4 {
  font-size: 20px;
}

.price {
  width: 80px;
}

.baseInput {
  width: 230px;
}

.v-enter-active {
  transition: opacity 0.7s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
