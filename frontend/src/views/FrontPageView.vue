<template>
  <div class="frontPageWrap container position-relative mt-2">
    <form @submit.prevent>
      <div class="row justify-content-center">
        <div class="col-1">
          <input
            type="text"
            class="form-control"
            id="frontPageSearchField"
            placeholder="Search"
          />
        </div>
        <div class="col-3">
          <button type="submit" class="btn btn-success">Search</button>
        </div>
      </div>
    </form>
    <div class="position-absolute dropdown top-0 end-0">
      <button
        class="btn btn-success dropdown-toggle"
        type="button"
        id="dropdownMenuButton"
        data-bs-toggle="dropdown"
        aria-expanded="false"
      >
        Search by:
      </button>
      <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
        <li><a class="dropdown-item" href="#">Name</a></li>
        <li><a class="dropdown-item" href="#">Score</a></li>
        <li><a class="dropdown-item" href="#">Date added</a></li>
      </ul>
    </div>
    <div class="mt-5">
      <router-link to="items">
        <font-awesome-icon icon="car" class="icon" />
      </router-link>
      <router-link to="items">
        <font-awesome-icon icon="wrench" class="icon" />
      </router-link>
      <router-link to="items">
        <font-awesome-icon icon="umbrella" class="icon" />
      </router-link>
      <router-link to="items">
        <font-awesome-icon icon="lemon" class="icon" />
      </router-link>
      <router-link to="items">
        <font-awesome-icon icon="children" class="icon" />
      </router-link>
      <router-link to="items">
        <font-awesome-icon icon="toilet-paper" class="icon" />
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
    };
  },

  created() {
    apiService
      .getItems({}, 0, 15)
      .then((response) => {
        this.items = response.data;
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

.icon {
  font-size: 2vw;
  margin-left: 40px;
}
</style>
