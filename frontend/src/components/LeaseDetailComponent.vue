<template>
  <div class="lease-detail-modal">
    <div class="lease-detail-container">
      <div class="lease-info-container">
        <div class="lease-info-list">
          <h4>
            Item:
            <a :href="'/items/' + lease.listingId">
              {{ item.name }}
            </a>
          </h4>
          <h4>
            Owner:
            <a :href="'/profile/' + lease.ownerId">
              {{ lease.ownerDisplayName }}
            </a>
          </h4>
          <h4>
            Leaser:
            <a :href="'/profile/' + lease.profileId">
              {{ lease.leaseeDisplayName }}
            </a>
          </h4>
          <h4>Price: {{ displayPrice }} kr</h4>
        </div>

        <div class="lease-info-list">
          <h4>From: {{ fromDate }}</h4>
          <h4>To: {{ toDate }}</h4>
          <h4>Duration: {{ displayDuration }} {{ item.priceType }}(s)</h4>
          <h4>Remaining: {{ displayRemaining }}</h4>
        </div>
      </div>

      <div class="lease-footer">
        <h2>Status: {{ status }}</h2>
        <div class="lease-button-container">
          <button class="boco-btn">Approve</button>
          <button class="boco-btn">Decline</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import priceService from "@/services/priceService";
import apiService from "@/services/apiService";
import leaseService from "@/services/leaseService";
export default {
  props: ["lease"],

  data() {
    return {
      remaining: 0,
      item: {
        title: null,
        priceType: null,
        price: null,
      },
    };
  },

  computed: {
    computedDuration() {
      return priceService.parseHours(
        this.lease.fromDatetime,
        this.lease.toDatetime
      );
    },

    displayDuration() {
      return priceService.displayDuration(
        this.computedDuration,
        this.item.priceType
      );
    },

    displayPrice() {
      return priceService.leasePrice(this.item, this.computedDuration);
    },

    fromDate() {
      return leaseService.displayDate(leaseService.fromDate(this.lease));
    },

    toDate() {
      return leaseService.displayDate(leaseService.toDate(this.lease));
    },

    displayRemaining() {
      let sec, min, hour, day;

      sec = Math.floor(this.remaining / 1000);
      min = Math.floor(sec / 60);
      hour = Math.floor(min / 60);

      day = Math.floor(hour / 24);

      return `${day}d:${hour % 24}h:${min % 60}m:${sec % 60}s`;
    },
  },

  watch: {
    remaining: {
      handler() {
        setTimeout(() => {
          this.remaining = new Date(this.lease.toDatetime * 1e3) - Date.now();
        }, 1000);
      },
      immediate: true,
    },
  },

  created() {
    apiService
      .getItem(this.lease.listingId)
      .then((response) => (this.item = response.data))
      .catch((error) => console.log(error));
  },
};
</script>

<style scoped>
.lease-detail-modal {
  position: fixed;
  z-index: 9999;
  top: 20%;
  left: 50%;
  transform: translateX(-50%);

  padding: 30px;

  border: solid 1px;
  border-radius: 5px;
  background-color: white;
  box-shadow: 50px 50px 50px 1000vmax rgba(0, 0, 0, 0.5);
}

.lease-detail-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 40px;
}

.lease-info-container {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  gap: 100px;
}

.lease-info-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.lease-footer {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  gap: 30px;
}

.lease-button-container {
  display: flex;
  gap: 10px;
}
</style>
