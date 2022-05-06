<template>
  <div class="frontPageWrap container position-relative mt-2">
    <div class="mt-5">
      <router-link :to="{ name: 'items', query: { category: 'Tools' } }">
        <font-awesome-icon icon="wrench" class="icon countText" title="TOOLS" />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Electronics' } }">
        <font-awesome-icon
          icon="plug"
          class="icon countText"
          title="ELECTRONICS"
        />
      </router-link>
      <router-link
        :to="{ name: 'items', query: { category: 'Hobby/Entertainment' } }"
      >
        <font-awesome-icon
          icon="palette"
          class="icon countText"
          title="HOBBY/ENTERTAINMENT"
        />
      </router-link>
      <router-link
        :to="{ name: 'items', query: { category: 'Musical Instruments' } }"
      >
        <font-awesome-icon
          icon="guitar"
          class="icon countText"
          title="MUSICAL INSTRUMENT"
        />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Vehicle' } }">
        <font-awesome-icon icon="car" class="icon countText" title="VEHICLE" />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Sport/Hiking' } }">
        <font-awesome-icon
          icon="basketball"
          class="icon countText"
          title="SPORT/HIKING"
        />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Interior' } }">
        <font-awesome-icon
          icon="couch"
          class="icon countText"
          title="INTERIOR"
        />
      </router-link>
      <router-link
        :to="{ name: 'items', query: { category: 'School/Office' } }"
      >
        <font-awesome-icon
          icon="graduation-cap"
          class="icon countText"
          title="SCHOOL/OFFICE"
        />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Home/Garden' } }">
        <font-awesome-icon
          icon="house"
          class="icon countText"
          title="HOME/GARDEN"
        />
      </router-link>
      <router-link :to="{ name: 'items', query: { category: 'Fashion' } }">
        <font-awesome-icon
          icon="shirt"
          class="icon countText"
          title="FASHION"
        />
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
      .getItems({}, 0, 12)
      .then((response) => {
        this.items = response.data.listingResponses;
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
  border-radius: 20px;
  background: #ececec;
  padding: 10px;
  color: var(--main-color);
  font-size: 2vw;
  margin-left: 40px;
  height: 40px;
  width: 40px;
}

.icon:hover {
  color: gray;
}

.countText {
  -webkit-animation-name: color2;
  -webkit-animation-duration: 10s;
  -webkit-animation-iteration-count: infinite;
  -webkit-animation-timing-function: linear;
  -webkit-animation-delay: 0s;
}
@-webkit-keyframes color2 {
  0% {
  }
  5%,
  15% {
    box-shadow: -1px -3px 5px #7f7e7e;
  }
  40% {
    box-shadow: none;
  }
}
</style>
