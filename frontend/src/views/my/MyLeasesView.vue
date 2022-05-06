<template>
  <div class="lease-view-container">
    <div class="lease-column-container">
      <h2>Owned</h2>
      <div class="filter-check-container">
        <label v-for="filter in filters" :key="filter">
          {{ filter }}
          <input
            type="radio"
            name="owned"
            :value="filter"
            v-model="ownedFilter"
            @change="onFilterChange"
        /></label>
      </div>

      <div class="lease-list-container">
        <lease-component
          v-for="lease in displayOwned"
          :key="lease"
          :lease="lease"
          @click="openOverlay(lease)"
        />
      </div>
    </div>

    <div class="lease-column-container">
      <h2>Leased</h2>
      <div class="filter-check-container">
        <label v-for="filter in filters" :key="filter">
          {{ filter }}
          <input
            type="radio"
            name="leased"
            :value="filter"
            v-model="leasedFilter"
            @change="onFilterChange"
        /></label>
      </div>

      <div class="lease-list-container">
        <lease-component
          v-for="lease in displayLeased"
          :key="lease"
          :lease="lease"
          @click="openOverlay(lease)"
        />
      </div>
    </div>
    <transition name="overlay">
      <lease-detail-component
        v-if="showOverlay"
        :lease="selectedLease"
        @close-overlay="closeOverlay"
      />
    </transition>
  </div>
</template>

<script>
import LeaseComponent from "@/components/LeaseComponent.vue";
import LeaseDetailComponent from "@/components/LeaseDetailComponent.vue";
import apiService from "@/services/apiService";
export default {
  components: { LeaseComponent, LeaseDetailComponent },

  data() {
    return {
      filters: ["All", "Past", "Current", "Upcoming"],
      showOverlay: false,
      selectedLease: null,
      ownedFilter: null,
      leasedFilter: null,
      owned: [],
      leased: [],
    };
  },

  computed: {
    displayOwned() {
      return this.filterLeases(this.owned, this.ownedFilter);
    },

    displayLeased() {
      return this.filterLeases(this.leased, this.leasedFilter);
    },
  },

  created() {
    window.addEventListener("keydown", (event) => {
      if (event.key == "Escape") {
        this.closeOverlay();
      }
    });

    let sortBy = (lease) => new Date(lease.fromDatetime);

    apiService
      .getMyLeases(true)
      .then((response) => (this.owned = response.data.sort(sortBy)))
      .catch((error) => console.log(error));

    apiService
      .getMyLeases(false)
      .then((response) => (this.leased = response.data.sort(sortBy)))
      .catch((error) => console.log(error));
  },

  methods: {
    openOverlay(lease) {
      if (!this.showOverlay) {
        this.selectedLease = lease;
        this.showOverlay = true;
      }
    },

    closeOverlay() {
      this.showOverlay = false;
    },

    filterLeases(leases, filter) {
      return leases.filter((lease) => {
        switch (filter) {
          case "Upcoming":
            return Date.now() < new Date(lease.fromDatetime);

          case "Current":
            return (
              new Date(lease.fromDatetime) < Date.now() &&
              Date.now() < new Date(lease.toDatetime)
            );

          case "Past":
            return new Date(lease.toDatetime) < Date.now();
          default:
            console.log("default sorting");
            return true;
        }
      });
    },
  },
};
</script>

<style scoped>
.lease-view-container {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

.lease-column-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.lease-list-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.filter-check-container {
  display: flex;
  flex-direction: row;
  gap: 15px;
  justify-content: space-around;
}

.overlay-enter-active,
.overlay-leave-active {
  transition: all 0.2s ease;
}

.overlay-enter-from,
.overlay-leave-to {
  opacity: 0;
  bottom: 50%;
}
</style>
