<template>
  <div class="frontPageWrap container position-relative mt-2">
    <select id="categories" name="Category" v-bind="orderBy">
      <option v-for="order in orderOptions" :key="order" :value="order.name">
        {{ order.name }}
      </option>
    </select>
    <div id="content">
      <form class="form-inline">
        <div class="input-group">
          <input
            type="text"
            id="search"
            class="form-control search-form"
            placeholder="Search..."
          />
          <span class="input-group-btn" style="width: 39px">
            <button
              id="search-this"
              type="button"
              class="pull-right btn btn-default search-btn"
            >
              Search
            </button>
          </span>
        </div>
      </form>
    </div>
    <div class="mt-5">
      <router-link :to="{ name: 'items', query: { category: 'Tools' } }">
        <font-awesome-icon icon="wrench" class="icon" title="TOOLS" />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Electronics' } }">
        <font-awesome-icon icon="plug" class="icon" title="ELECTRONICS" />
      </router-link>
      <router-link
        :to="{ name: 'items', query: { category: 'Hobby/Entertainment' } }"
      >
        <font-awesome-icon
          icon="palette"
          class="icon"
          title="HOBBY/ENTERTAINMENT"
        />
      </router-link>
      <router-link
        :to="{ name: 'items', query: { category: 'Musical Instruments' } }"
      >
        <font-awesome-icon
          icon="guitar"
          class="icon"
          title="MUSICAL INSTRUMENT"
        />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Vehicle' } }">
        <font-awesome-icon icon="car" class="icon" title="VEHICLE" />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Sport/Hiking' } }">
        <font-awesome-icon
          icon="basketball"
          class="icon"
          title="SPORT/HIKING"
        />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Interior' } }">
        <font-awesome-icon icon="couch" class="icon" title="INTERIOR" />
      </router-link>
      <router-link
        :to="{ name: 'items', query: { category: 'School/Office' } }"
      >
        <font-awesome-icon
          icon="graduation-cap"
          class="icon"
          title="SCHOOL/OFFICE"
        />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Home/Garden' } }">
        <font-awesome-icon icon="house" class="icon" title="HOME/GARDEN" />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Fashion' } }">
        <font-awesome-icon icon="shirt" class="icon" title="FASHION" />
      </router-link>
    </div>
    <div class="items">
      <SmallItem v-for="item in items" :key="item" :item="item" />
    </div>
  </div>
</template>

<script>
import SmallItem from "@/components/Items/SmallItem.vue";
import apiService from "@/services/apiService";
export default {
  components: { SmallItem },
  data() {
    return {
      items: [],
      orderOptions: [
        { name: "No filter" },
        { name: "Price high to low" },
        { name: "Price low to high" },
      ],
    };
  },

  created() {
    apiService
      .getItems({}, 0, 15)
      .then((response) => {
        this.items = response.data;
        console.log(this.items);
      })
      .catch((error) => console.log(error));
  },
};
</script>

<style scoped>
.items {
  justify-content: center;
  margin-top: 30px;
  display: -ms-flexbox;
  display: -webkit-flex;
  -webkit-flex-wrap: wrap;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
}

.btn-success {
  align-items: center;
  background-color: var(--main-color);
  border: 0;
  border-radius: 100px;
  box-sizing: border-box;
  cursor: pointer;
  display: inline-flex;
  font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto,
    "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell,
    "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    "Lucida Grande", Helvetica, Arial, sans-serif;
  font-size: 16px;
  font-weight: 600;
  justify-content: center;
  line-height: 20px;
  max-width: 480px;
  min-height: 40px;
  min-width: 0;
  overflow: hidden;
  padding: 0 20px;
  text-align: center;
  touch-action: manipulation;
  transition: background-color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s,
    box-shadow 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s,
    color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s;
  user-select: none;
  -webkit-user-select: none;
  vertical-align: middle;
}

.btn-success:hover,
.btn-success:focus {
  background-color: var(--button-hover);
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

.btn-success:disabled {
  cursor: not-allowed;
  background: rgba(0, 0, 0, 0.08);
  color: rgba(0, 0, 0, 0.3);
}

.mt-5 {
  justify-content: center;
  display: flex;
}

.icon {
  color: var(--main-color);
  font-size: 2vw;
  margin-left: 40px;
  height: 40px;
  width: 40px;
}

#categories {
  float: right;
}

.icon:hover {
  color: gray;
}

#content {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.search-form {
  border: 1px solid #39495c;
  border-radius: 30px 0 0 30px;
}

.input-group {
  width: 200px;
}

.input-group-btn {
  max-width: 38px;
}

#search {
  border: 1px solid #39495c;
}

.search-btn {
  cursor: pointer;
  border-radius: 0 30px 30px 0;
  background-color: var(--main-color);
  color: #ffffff;
}
</style>
