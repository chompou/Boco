<template>
  <div class="lease-detail-modal">
    <p class="cross" @click="$emit('close-overlay')">X</p>
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
          <h4>Price: {{ displayPrice }}</h4>
        </div>

        <div class="lease-info-list">
          <h4>From: {{ fromDate }}</h4>
          <h4>To: &emsp;&nbsp;{{ toDate }}</h4>
          <h4>Duration: {{ displayDuration }} {{ item.priceType }}(s)</h4>
          <h4>Remaining: {{ displayRemaining }}</h4>
        </div>
      </div>

      <div class="lease-footer">
        <h2>Status: Lease {{ status }}</h2>
        <div class="lease-button-container" v-if="buttons == 'approve'">
          <button class="boco-btn" @click="onButton">Approve</button>
          <button class="boco-btn" @click="onButton">Decline</button>
        </div>

        <div class="lease-button-container" v-if="buttons == 'cancel'">
          <button class="boco-btn" @click="onButton">Cancel</button>
        </div>

        <div class="lease-button-container" v-if="buttons == 'remove'">
          <button class="boco-btn" @click="onButton">Remove</button>
        </div>

        <div class="lease-button-container" v-if="buttons == 'complete'">
          <button class="boco-btn" @click="onButton">Complete</button>
        </div>

        <div class="lease-button-container" v-if="buttons == 'review'">
          <button class="boco-btn" @click="onButton">Review</button>
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
      remaining: new Date(this.lease.toDatetime) - Date.now(),
      item: {
        title: null,
        priceType: null,
        price: null,
      },
    };
  },

  methods: {
    onButton(event) {
      switch (event.target.innerHTML) {
        case "Approve":
          apiService
            .updateMyLease({
              ...this.lease,
              leaseId: this.lease.id,
              isApproved: true,
            })
            .catch((error) => console.log(error));
          break;
        case "Complete":
          apiService
            .updateMyLease({
              ...this.lease,
              leaseId: this.lease.id,
              isCompleted: true,
            })
            .catch((error) => console.log(error));
          break;
        case "Review":
          this.$router.push({
            name: "newRating",
            params: {
              id: this.lease.id,
              owner: this.$store.state.loggedInUser == this.lease.ownerId,
            },
          });
          break;
        default:
          apiService.deleteMyLease(this.lease.id).catch((error) => {
            if (error.response.status == 422) {
              console.log("forboden");
            } else {
              console.log(error);
            }
          });
      }

      this.$emit("close-overlay");
    },
  },

  computed: {
    buttons() {
      let owner = this.$store.state.loggedInUser == this.lease.ownerId;
      let status = leaseService.getStatus(this.lease, owner);

      if (owner) {
        switch (status) {
          case "Pending Approval":
            return "approve";
          case "Upcoming":
            return "cancel";
          case "Expired":
            return "remove";
          case "In Progress":
          case "Overdue":
            return "complete";
          case "Pending Review":
            return "review";
          case "Completed":
          default:
            return "";
        }
      } else {
        switch (status) {
          case "Pending Approval":
          case "Upcoming":
            return "cancel";
          case "Pending Review":
            return "review";
          default:
            return "";
        }
      }
    },

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
      return priceService.formattedPrice(
        priceService.leasePrice(this.item, this.computedDuration)
      );
    },

    fromDate() {
      return leaseService.displayDate(leaseService.fromDate(this.lease));
    },

    toDate() {
      return leaseService.displayDate(leaseService.toDate(this.lease));
    },

    status() {
      return leaseService.getStatus(this.lease);
    },

    displayRemaining() {
      let sec, min, hour, day;

      let clampedValue = Math.max(0, this.remaining);

      sec = Math.floor(clampedValue / 1000);
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
          this.remaining = new Date(this.lease.toDatetime) - Date.now();
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

.cross {
  border-radius: 1000%;
  position: fixed;
  top: 2%;
  right: 2%;
  font-size: 25px;
  cursor: default;
}
</style>
