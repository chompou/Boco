<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <div class="main">
      <h4 id="priceHeader">Price</h4>
      <transition>
        <div class="priceHolder" v-if="collapsed === false">
          <p>From:</p>
          <input
            v-model="filters.priceFrom"
            placeholder="0"
            class="price"
            type="number"
            min="0"
          />
          <p>To:</p>
          <input
            v-model="filters.priceTo"
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
              v-model="filters.priceType"
              value="Hour"
              checked
            />
            <label class="btn btn-secondary" for="option1">Hour</label>

            <input
              type="radio"
              class="btn-check"
              name="options"
              id="option2"
              v-model="filters.priceType"
              value="Day"
              autocomplete="off"
            />
            <label class="btn btn-secondary" for="option2">Day</label>

            <input
              type="radio"
              class="btn-check"
              name="options"
              id="option3"
              v-model="filters.priceType"
              value="Week"
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
              v-model="filters.search"
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
            v-model="filters.category"
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
import priceService from "@/services/priceService";

export default {
  data() {
    return {
      filters: {},
      categories: [],
    };
  },

  methods: {
    onSubmit() {
      console.log(this.filters);

      if (this.filters.priceFrom != null) {
        this.filters.priceFrom = priceService.parsePrice(
          this.filters.priceFrom,
          this.filters.priceType
        );
      }

      if (this.filters.priceTo != null) {
        this.filters.priceTo = priceService.parsePrice(
          this.filters.priceTo,
          this.filters.priceType
        );
      }

      if (this.filters.priceType == null) this.filters.priceType = "Hour";

      this.$router.push("/").then(() => {
        this.$router.replace({ name: "items", query: this.filters });
      });
    },
  },

  setup() {
    return { collapsed, toggleSidebar, sidebarWidth };
  },

  created() {
    apiService
      .getCategories()
      .then((response) => {
        this.categories = response.data;
      })
      .catch((error) => console.log(error));

    this.filters = this.$route.query;

    if (this.filters.priceFrom != null) {
      this.filters.priceFrom = priceService.displayPrice(
        this.filters.priceFrom,
        this.filters.priceType
      );
    }

    if (this.filters.priceTo != null) {
      this.filters.priceTo = priceService.displayPrice(
        this.filters.priceTo,
        this.filters.priceType
      );
    }
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
