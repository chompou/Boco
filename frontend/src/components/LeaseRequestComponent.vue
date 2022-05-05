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
      if (this.computedHours <= 0) {
        return alert("Invalid lease period");
      }

      apiService
        .createLease({
          fromDatetime: new Date(this.fromTime).getTime(),
          toDatetime: new Date(this.toTime).getTime(),
          id: this.item.id,
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
      if (this.computedHours < 0) return "-";

      return priceService.displayDuration(
        this.computedHours,
        this.item.priceType
      );
    },

    displayPrice() {
      let computedPrice = priceService.leasePrice(
        this.item,
        this.computedHours
      );
      if (computedPrice < 0) return "-";

      return computedPrice;
    },
  },

  created() {
    window.addEventListener("keydown", (e) => {
      if (e.key == "Escape") {
        this.$emit("close-overlay");
      }
    });
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
