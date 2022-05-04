<template>
  <div class="lease-view-container">
    <div class="lease-column-container">
      <h2>Owned</h2>
      <div class="filter-check-container">
        <label>Past <input type="radio" name="personal" /></label>
        <label>Current <input type="radio" name="personal" /></label>
        <label>Upcoming <input type="radio" name="personal" /></label>
      </div>

      <div class="lease-list-container">
        <lease-component
          v-for="lease in owned"
          :key="lease"
          :lease="lease"
          @click="openOverlay(lease)"
        />
      </div>
    </div>

    <div class="lease-column-container">
      <h2>Leased</h2>
      <div class="filter-check-container">
        <label>Past <input type="radio" name="personal" /></label>
        <label>Current <input type="radio" name="personal" /></label>
        <label>Upcoming <input type="radio" name="personal" /></label>
      </div>

      <div class="lease-list-container">
        <lease-component
          v-for="lease in leased"
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
        @close-overlay="showOverlay = false"
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
      showOverlay: false,
      selectedLease: null,
      owned: [],
      leased: [],
    };
  },

  created() {
    window.addEventListener("keydown", (event) => {
      if (event.key == "Escape") {
        this.showOverlay = false;
      }
    });

    apiService
      .getMyLeases(true)
      .then((response) => (this.owned = response.data))
      .catch((error) => console.log(error));

    apiService
      .getMyLeases(false)
      .then((response) => (this.leased = response.data))
      .catch((error) => console.log(error));
  },

  methods: {
    openOverlay(lease) {
      if (!this.showOverlay) {
        this.selectedLease = lease;
        this.showOverlay = true;
      }
    },

    getStatus(lease) {
      if (!lease.isApproved) {
        return "Pending Approval";
      }
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
