<template>
  <div class="request-overlay request-modal">
    <p class="cross" @click="$emit('close-overlay')">X</p>
    <h1>Book</h1>
    <div class="lease-container">
      <div class="datetime-input">
        <input type="datetime-local" v-model="fromTime" />
        <input type="datetime-local" v-model="toTime" />
      </div>
      <div class="lease-info">
        <h3>
          {{ displayDuration }} {{ item.priceType
          }}{{ displayDuration != 1 ? "s" : "" }}
        </h3>
        <h3>{{ displayPrice }} kr</h3>
      </div>
    </div>
    <button class="boco-btn" @click="onSubmit">Submit</button>
  </div>
</template>

<script>
import priceService from "@/services/priceService";
import apiService from "@/services/apiService";
export default {
  props: ["item"],

  data() {
    return {
      fromTime: null,
      toTime: null,
    };
  },

  methods: {
    onSubmit() {
      apiService
        .createLease({
          fromDatetime: this.fromTime,
          toDatetime: this.toTime,
          listingId: this.item.id,
        })
        .catch((error) => console.log(error))
        .then(() => this.$emit("close-overlay"));
    },
  },

  computed: {
    computedHours() {
      return priceService.parseHours(this.fromTime, this.toTime);
    },

    displayDuration() {
      return priceService.displayDuration(
        this.computedHours,
        this.item.priceType
      );
    },

    displayPrice() {
      return priceService.leasePrice(this.item, this.computedHours);
    },
  },
};
</script>

<style scoped>
.datetime-input {
  display: flex;
  flex-direction: column;
  gap: 25px;
}

.lease-container {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  gap: 50px;
}

.cross {
  border-radius: 1000%;
  position: fixed;
  top: 2%;
  right: 2%;
  font-size: 25px;
  cursor: default;
}

.request-modal {
  display: flex;
  flex-direction: column;
  gap: 30px;
  align-items: center;

  position: fixed;
  z-index: 9999;
  border: solid 2px;
  border-radius: 10px;
  top: 15%;
  left: 50%;
  padding: 30px;
  transform: translateX(-50%);
  background-color: white;
  box-shadow: 50px 50px 50px 1000vmax rgba(0, 0, 0, 0.5);
}
</style>
