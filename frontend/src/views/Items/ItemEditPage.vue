<template>
  <div id="container">
    <h1>Edit item</h1>
    <div>
      <img id="image" alt="Vue logo" :src="imgSource" />
    </div>
    <div id="inputFields">
      <div class="ItemId">
        <h5 id="ItemNameHeader">Title:</h5>
        <input
          class="baseInput"
          v-model="item.name"
          placeholder="Name"
          id="ItemName"
          disabled
        />
      </div>
      <div class="ItemId">
        <h5>Address:</h5>
        <input
          class="baseInput"
          v-model="item.address"
          placeholder="Address"
          id="Address"
        />
      </div>
      <div class="ItemId">
        <h5>Price:</h5>
        <div id="pricePicker">
          <input
            v-model="item.price"
            placeholder="100"
            class="price"
            type="number"
            min="0"
          />
          <label id="valuta">kr/</label>
          <select v-model="item.priceType">
            <option>Hour</option>
            <option>Day</option>
            <option>Week</option>
          </select>
        </div>
      </div>
      <div class="ItemId">
        <h5>Categories (Multi-select):</h5>
        <form class="checkBoxForm">
          <input
            type="checkbox"
            id="tools"
            value="Tools"
            v-model="this.category"
          />
          <label for="tools">Tools</label>

          <input
            type="checkbox"
            id="sport"
            value="Sport/Hiking"
            v-model="this.category"
          />
          <label for="sport">Sport/Hiking</label>

          <input
            type="checkbox"
            id="electronics"
            value="Electronics"
            v-model="this.category"
          />
          <label for="electronics">Electronics</label>

          <input
            type="checkbox"
            id="interior"
            value="Interior"
            v-model="this.category"
          />
          <label for="interior">Interior</label>

          <input
            type="checkbox"
            id="hobby"
            value="Hobby/Entertainment"
            v-model="this.category"
          />
          <label for="hobby">Hobby/Entertainment</label>

          <input
            type="checkbox"
            id="school"
            value="School/Office"
            v-model="this.category"
          />
          <label for="school">School/Office</label>

          <input
            type="checkbox"
            id="musical"
            value="Musical Instruments"
            v-model="this.category"
          />
          <label for="musical">Musical Instruments</label>

          <input
            type="checkbox"
            id="home"
            value="Home/Garden"
            v-model="this.category"
          />
          <label for="home">Home/Garden</label>

          <input
            type="checkbox"
            id="vehicle"
            value="Vehicle"
            v-model="this.category"
          />
          <label for="vehicle">Vehicle</label>

          <input
            type="checkbox"
            id="fashion"
            value="Fashion"
            v-model="this.category"
          />
          <label for="fashion">Fashion</label>
        </form>
      </div>
      <div id="descriptionField">
        <h5>Description</h5>
        <textarea
          v-model="this.item.description"
          placeholder="Description"
          id="description"
          name="description"
        ></textarea>
      </div>
      <div id="CreateButtons" class="element">
        <button class="CreateButton" v-on:click="update">Update</button>
        <button id="Delete" class="CreateButton" v-on:click="dismiss">
          Dismiss
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import apiService from "@/services/apiService";
import { useToast } from "vue-toastification";
import priceService from "@/services/priceService";

export default {
  setup() {
    const toast = useToast();
    return { toast };
  },
  props: ["id"],
  data() {
    return {
      item: null,
      imgSource: null,
      category: [this.category],
    };
  },
  methods: {
    update() {
      let standardPrice = priceService.parsePrice(
        this.item.price,
        this.item.priceType
      );

      apiService
        .updateItem({
          listingId: this.id,
          address: this.item.address,
          price: standardPrice,
          priceType: this.item.priceType,
          description: this.item.description,
          categoryType: [this.category],
        })
        .catch((error) => {
          console.log(error);
        });
      setTimeout(() => {
        this.$router.push({ name: "item", params: { id: this.id } });
      }, 100);
      this.toast.success("Item listing was updated", {
        timeout: 2000,
      });
    },
    dismiss() {
      this.$router.push({ name: "item", params: { id: this.id } });
      this.toast.error("Changes were discarded", {
        timeout: 2000,
      });
    },
  },
  computed: {
    price() {
      let actuallyPrice = this.item.price;
      if (this.item.priceType === "Week") {
        actuallyPrice = this.item.price * 7 * 24;
      }
      if (this.item.priceType === "Day") {
        actuallyPrice = this.item.price * 24;
      }
      return actuallyPrice;
    },
  },
  created() {
    apiService
      .getItem(this.id)
      .then((response) => {
        this.item = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
    setTimeout(() => {
      let image = this.item.image;
      this.imgSource = "data:image/jpeg;base64, " + image;
    }, 100);
  },
};
</script>

<style scoped>
#container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.checkBoxForm {
  display: inline-grid;
  grid-template-rows: repeat(5, min-content);
  grid-template-columns: repeat(4, min-content);
  grid-row-gap: 10px;
  grid-column-gap: 40px;
  margin: 10px;
}

#image {
  width: 300px;
  height: 300px;
}

.ItemId {
  margin: 20px;
}

.baseInput {
  width: 400px;
  height: 40px;
}

.price {
  font-size: 16px;
  width: 100px;
  height: 34px;
}

#valuta {
  font-size: 20px;
}

select {
  font-size: 16px;
  width: 100px;
  height: 40px;
}

#inputFields {
  text-align: left;
  margin-bottom: 20px;
}

#descriptionField {
  margin-top: 30px;
  margin-left: 20px;
  text-align: left;
}

#description {
  width: 400px;
  display: block;
  height: 100px;
  font-size: 15px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.CreateButton {
  width: 100px;
}

#pricePicker {
  display: flex;
  align-items: center;
}

.CreateButton {
  color: white;
  align-items: center;
  background-color: var(--button-color);
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

#CreateButtons {
  margin-top: 20px;
  display: flex;
  justify-content: space-evenly;
}

.CreateButton:hover {
  background-color: var(--button-hover);
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

label {
  display: flex;
  margin: auto;
  padding: 0;
  border-color: var(--button-color);
  border-radius: 0;
}

#Delete {
  background: #ff6565;
}

#Delete:hover {
  background: #b74646;
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}
</style>
