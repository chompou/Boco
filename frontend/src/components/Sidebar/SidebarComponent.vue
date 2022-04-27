<template>
  <div class="sidebar" :style="{ width: sidebarWidth }">
    <div class="main">
      <h4 id="priceHeader">Price</h4>
      <transition>
        <div class="priceHolder" v-if="collapsed === false">
          <p>From:</p>
          <input
            v-model="priceFrom"
            placeholder="100"
            class="price"
            type="number"
            min="0"
          />
          <p>To:</p>
          <input
            v-model="priceTo"
            placeholder="100"
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
      <transition>
        <div class="Header" v-if="collapsed === false">
          <input type="submit" id="submit" value="Search" />
        </div>
      </transition>
    </div>
    <span
      class="collapse-icon"
      :class="{ 'rotate-180': collapsed }"
      @click="toggleSidebar"
    >
      <h2 class="fas fa-angle-double-left">⬅</h2>
    </span>
  </div>
</template>

<script>
import { collapsed, toggleSidebar, sidebarWidth } from "./state";

export default {
  data() {
    return {
      priceFrom: 0,
      priceTo: 0,
      leaseType: "Hour",
      search: "",
    };
  },
  props: {},
  setup() {
    return { collapsed, toggleSidebar, sidebarWidth };
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
  position: absolute;
  bottom: 0;
  padding: 0.75em;

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
  margin-left: 30px;
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
