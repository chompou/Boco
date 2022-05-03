<template>
  <div class="lease-detail-modal">
    <div class="lease-detail-container">
      <div class="lease-info-container">
        <div class="lease-info-list">
          <h4>Item: {{ item.title }}</h4>
          <h4>Owner: {{ lease.owner }}</h4>
          <h4>Leaser: {{ lease.leaser }}</h4>
          <h4>Price: {{ displayPrice }} kr</h4>
        </div>

        <div class="lease-info-list">
          <h4>From: {{ fromTimestamp }}</h4>
          <h4>To: {{ toTimestamp }}</h4>
          <h4>Duration: {{ displayDuration }} {{ item.priceType }}(s)</h4>
          <h4>Remaining: {{ displayRemaining }}</h4>
        </div>
      </div>

      <div class="lease-status-container">
        <h2>Status: Lease expired</h2>
      </div>
    </div>
  </div>
</template>

<script>
import priceService from "@/services/priceService";
export default {
  props: [],

  data() {
    return {
      remaining: 0,
      item: {
        title: "Wrench",
        priceType: "Day",
        price: 50,
      },
      lease: {
        owner: "Jon Martin",
        leaser: "Tobias",
        fromDatetime: 1651561325,
        toDatetime: 1651568602,
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

    fromTimestamp() {
      return (
        new Date(this.lease.fromDatetime * 1e3).toLocaleDateString() +
        " " +
        new Date(this.lease.fromDatetime * 1e3).toLocaleTimeString()
      );
    },

    toTimestamp() {
      return (
        new Date(this.lease.toDatetime * 1e3).toLocaleDateString() +
        " " +
        new Date(this.lease.toDatetime * 1e3).toLocaleTimeString()
      );
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
};
</script>

<style scoped>
.lease-detail-modal {
  position: fixed;
  z-index: 9999;
  top: 20%;
  left: 50%;
  transform: translateX(-50%);

  padding: 20px;

  border: solid 1px;
  border-radius: 5px;
  background-color: white;
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
</style>
