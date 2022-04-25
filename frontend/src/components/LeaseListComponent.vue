<template>
  <div class="container">
    <div class="filter-check-container">
      <label>Past <input type="radio" :name="personal" /></label>
      <label>Current <input type="radio" :name="personal" /></label>
      <label>Upcoming <input type="radio" :name="personal" /></label>
    </div>

    <div class="lease-list">
      <div class="lease-container" v-for="lease in leases" :key="lease">
        <div>
          <h4>{{ lease.title }}</h4>
          <h6>{{ lease.owner }}</h6>
          <h6>{{ lease.from }} - {{ lease.to }}</h6>
        </div>
        <rating-component />
      </div>
    </div>
  </div>
</template>

<script>
import RatingComponent from "@/components/RatingComponent.vue";
export default {
  components: { RatingComponent },

  props: ["lease", "personal"],

  data() {
    return {
      leases: [
        {
          id: 1,
          title: "MyItemasfadfadfadfadfa",
          owner: "Jon Martin",
          from: "2022-05-15",
          to: "2022-05-20",
        },
        {
          id: 2,
          title: "MyItem",
          owner: "Jon Martin",
          from: "2022-05-15",
          to: "2022-05-20",
        },
        {
          id: 3,
          title: "MyItem",
          owner: "Jon Martin",
          from: "2022-05-15",
          to: "2022-05-20",
        },
      ],
    };
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.filter-check-container {
  display: flex;
  flex-direction: row;
  gap: 10px;
  justify-content: space-around;
}

.lease-list {
  display: flex;
  flex-direction: column;
  gap: 25px;
  max-height: 60vh;
}

.lease-container {
  display: flex;
  border: 3px solid;
  border-radius: 10px;
  padding: 15px;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  text-align: left;
  gap: 50px;
}
</style>
