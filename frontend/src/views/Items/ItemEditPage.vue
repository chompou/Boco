<template>
  <div id="container">
    <h1>Edit item</h1>
    <div>
      <img id="image" alt="Vue logo" :src="imageSource" />
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
      <div id="descriptionField">
        <h5>Description</h5>
        <textarea
          v-model="item.description"
          placeholder="Description"
          id="description"
          name="description"
        ></textarea>
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
            v-model="inputPrice"
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
      item: {},
      inputPrice: null,
    };
  },

  computed: {
    imageSource() {
      if (!this.item.image) return null;
      return "data:image/jpeg;base64, " + this.item.image;
    },
  },

  methods: {
    update() {
      let standardPrice = priceService.parsePrice(
        this.inputPrice,
        this.item.priceType
      );

      console.log(standardPrice);

      apiService
        .updateItem({
          ...this.item,
          price: standardPrice,
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

  created() {
    apiService
      .getItem(this.id)
      .then((response) => {
        this.item = response.data;
        this.inputPrice = priceService.displayPrice(this.item);
      })
      .catch((error) => console.log(error));
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
