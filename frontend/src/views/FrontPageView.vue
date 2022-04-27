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
}

.btn-success:disabled {
  cursor: not-allowed;
  background: rgba(0, 0, 0, 0.08);
  color: rgba(0, 0, 0, 0.3);
}

.icon {
  color: var(--main-color);
  font-size: 2vw;
  margin-left: 40px;
}

.icon:hover {
  color: gray;
}
</style>
