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
          @keydown.esc="closeOverlay"
        />
      </div>
    </div>

    <lease-detail-component v-if="showOverlay" :lease="selectedLease" />
  </div>
</template>

<script>
import LeaseComponent from "@/components/LeaseComponent.vue";
import LeaseDetailComponent from "@/components/LeaseDetailComponent.vue";
export default {
  components: { LeaseComponent, LeaseDetailComponent },

  data() {
    return {
      showOverlay: false,
      selectedLease: null,
      owned: [
        {
          id: 1,
          title: "Lemon",
          owner: 2,
          from: "2022",
          to: "2023",
        },
        {
          id: 1,
          title: "Lemon",
          owner: 2,
          from: "2022",
          to: "2023",
        },
        {
          id: 1,
          title: "Lemon",
          owner: 2,
          from: "2022",
          to: "2023",
        },
      ],
      leased: [
        {
          id: 2,
        },
      ],
    };
  },

  created() {
    window.addEventListener("keydown", (event) => {
      if (event.key == "Escape") {
        this.showOverlay = false;
      }
    });
  },

  methods: {
    openOverlay(lease) {
      if (!this.showOverlay) {
        console.log(lease);
        this.selectedLease = lease;
        this.showOverlay = true;
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
</style>
