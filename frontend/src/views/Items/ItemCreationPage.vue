<template>
  <div id="container">
    <h1>Create a new Item</h1>
    <div class="col-md-5">
      <form>
        <div class="form-group">
          <h5 for="my-file">Select Image</h5>
          <input
            type="file"
            accept="image/*"
            @change="previewImage"
            class="form-control-file"
            id="my-file"
          />

          <div class="border p-2 mt-3">
            <p>Preview Here:</p>
            <template v-if="preview">
              <img alt="image" :src="preview" class="img-fluid" />
              <p class="mb-0">file name: {{ image.name }}</p>
              <p class="mb-0">size: {{ image.size / 1024 }}KB</p>
            </template>
          </div>
        </div>
      </form>
    </div>
    <div id="inputFields">
      <div class="ItemId">
        <h5 id="ItemNameHeader">Title:</h5>
        <input
          class="baseInput"
          v-model="this.title"
          placeholder="Name"
          id="ItemName"
        />
      </div>
      <div class="ItemId">
        <h5>Address:</h5>
        <input
          class="baseInput"
          v-model="this.address"
          placeholder="Address"
          id="Address"
        />
      </div>
      <div class="ItemId">
        <h5>Price:</h5>
        <div id="pricePicker">
          <input
            v-model="price"
            placeholder="100"
            class="price"
            type="number"
            min="0"
          />
          <label id="valuta">kr/</label>
          <select v-model="this.leaseType">
            <option>Hour</option>
            <option>Day</option>
            <option>Week</option>
          </select>
        </div>
      </div>
      <div class="ItemId">
        <h5>Categories (Multi-select):</h5>
        <form class="checkBoxForm">
          <input type="checkbox" id="tools" value="Tools" v-model="category" />
          <label for="tools">Tools</label>

          <input
            type="checkbox"
            id="sport"
            value="Sport/Hiking"
            v-model="category"
          />
          <label for="sport">Sport/Hiking</label>

          <input
            type="checkbox"
            id="electronics"
            value="Electronics"
            v-model="category"
          />
          <label for="electronics">Electronics</label>

          <input
            type="checkbox"
            id="interior"
            value="Interior"
            v-model="category"
          />
          <label for="interior">Interior</label>

          <input
            type="checkbox"
            id="hobby"
            value="Hobby/Entertainment"
            v-model="category"
          />
          <label for="hobby">Hobby/Entertainment</label>

          <input
            type="checkbox"
            id="school"
            value="School/Office"
            v-model="category"
          />
          <label for="school">School/Office</label>

          <input
            type="checkbox"
            id="musical"
            value="Musical Instruments"
            v-model="category"
          />
          <label for="musical">Musical Instruments</label>

          <input
            type="checkbox"
            id="home"
            value="Home/Garden"
            v-model="category"
          />
          <label for="home">Home/Garden</label>

          <input
            type="checkbox"
            id="vehicle"
            value="Vehicle"
            v-model="category"
          />
          <label for="vehicle">Vehicle</label>

          <input
            type="checkbox"
            id="fashion"
            value="Fashion"
            v-model="category"
          />
          <label for="fashion">Fashion</label>
        </form>
      </div>
      <div id="descriptionField">
        <h5>Description</h5>
        <textarea
          v-model="this.description"
          placeholder="Description"
          id="description"
          name="description"
        ></textarea>
      </div>
      <div id="CreateButtons" class="element">
        <button class="CreateButton" v-on:click="submit">Create</button>
        <button id="Delete" class="CreateButton" v-on:click="dismiss">
          Delete
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { useToast } from "vue-toastification";
import apiService from "@/services/apiService";

export default {
  setup() {
    const toast = useToast();
    return { toast };
  },
  data() {
    return {
      formData: new FormData(),
      preview: null,
      image: null,
      title: this.title,
      address: this.address,
      price: 0,
      leaseType: "Hour",
      category: [],
      checked: false,
      description: this.description,
    };
  },
  methods: {
    dismiss() {
      this.toast.error("Listing was discarded", {
        timeout: 2000,
      });
    },
    previewImage: function (event) {
      let input = event.target;
      if (input.files) {
        let reader = new FileReader();
        reader.onload = (e) => {
          this.preview = e.target.result;
        };
        this.image = input.files[0];
        this.formData.append("file", this.image);
        reader.readAsDataURL(input.files[0]);
      }
    },
    submit() {
      console.log(this.image);
      console.log(this.leaseType);
      this.formData.append(
        "properties",
        new Blob(
          [
            JSON.stringify({
              name: this.title,
              address: this.address,
              description: this.description,
              price: this.leasePrice,
              priceType: this.leaseType,
              categoryNames: this.category,
            }),
          ],
          {
            type: "application/json",
          }
        )
      );
      apiService.createItem(this.formData).catch((error) => {
        console.log(error);
      });
      this.toast.success("Item was successfully created", {
        timeout: 2000,
      });
      this.$router.push("/my/items");
    },
  },
  computed: {
    leasePrice() {
      let priceInHours = this.price;
      if (this.leaseType === "Week") {
        priceInHours = this.price / (7 * 24);
      }
      if (this.leaseType === "Day") {
        priceInHours = this.price / 24;
      }
      return priceInHours;
    },
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

.col-md-5 {
  width: 400px;
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

.img-fluid {
  width: 300px;
  height: 300px;
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

#reset {
  width: 130px;
}

#Delete:hover {
  background: #b74646;
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}
</style>
