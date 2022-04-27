<template>
  <div id="container">
    <h1>Create a new Item</h1>
    <div class="col-md-5">
      <form>
        <div class="form-group">
          <label for="my-file">Select Image</label>
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
        <p id="ItemNameHeader">Title:</p>
        <input
          class="baseInput"
          v-model="title"
          placeholder="Name"
          id="ItemName"
        />
      </div>
      <div class="ItemId">
        <p>Address:</p>
        <input
          class="baseInput"
          v-model="address"
          placeholder="Address"
          id="Address"
        />
      </div>
      <div class="ItemId">
        <p>price:</p>
        <input
          v-model="price"
          placeholder="100"
          class="price"
          type="number"
          min="0"
        />
        <label id="valuta">kr/</label>
        <select v-model="leaseType">
          <option disabled value="">Hour</option>
          <option>Hour</option>
          <option>Day</option>
          <option>Week</option>
        </select>
      </div>
      <div id="category">
        <div>
          <input type="checkbox" id="categoryCheckbox" v-model="checked" />
          <label for="categoryCheckbox" id="categoryCheckboxLabel"
            >Categories</label
          >
        </div>
        <div id="checkboxItems" v-if="checked">
          <div class="ItemId2">
            <input
              type="checkbox"
              id="vehicle"
              value="vehicle"
              v-model="category"
            />
            <label for="vehicle">Vehicle</label>
          </div>
          <div class="ItemId2">
            <input type="checkbox" id="tool" value="tool" v-model="category" />
            <label for="tool">Tool</label>
          </div>
          <div class="ItemId2">
            <input
              type="checkbox"
              id="electronic"
              value="electronic"
              v-model="category"
            />
            <label for="electronic">Electronic</label>
          </div>
        </div>
      </div>
      <div id="descriptionField">
        <p>Description</p>
        <textarea
          v-model="description"
          placeholder="Description"
          id="description"
          name="description"
        ></textarea>
      </div>
      <div id="CreateButtons" class="element">
        <button class="CreateButton" v-on:click="create">Create</button>
        <button id="Delete" class="CreateButton">Delete</button>
      </div>
    </div>
  </div>
</template>

<script>
import apiService from "@/services/apiService";

export default {
  data() {
    return {
      preview: null,
      image: null,
      title: "",
      address: "",
      price: "",
      leaseType: "",
      category: [],
      checked: false,
      description: "",
    };
  },
  methods: {
    previewImage: function (event) {
      let input = event.target;
      if (input.files) {
        let reader = new FileReader();
        reader.onload = (e) => {
          this.preview = e.target.result;
        };
        this.image = input.files[0];
        reader.readAsDataURL(input.files[0]);
      }
    },
    create() {
      console.log(this.leasePrice);
      apiService
        .createItem({
          image: this.image,
          title: this.title,
          address: this.address,
          price: this.price,
          priceType: this.leasePrice,
          description: this.description,
        })
        .catch((error) => {
          console.log(error);
        });
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

.ItemId {
  margin: 20px;
}

.ItemId2 {
  margin: 10px;
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

#category {
  margin-top: 30px;
  margin-left: 20px;
}

#categoryCheckboxLabel {
  border: 1px solid #39495c;
  font-size: 20px;
  padding: 5px;
}

#categoryCheckboxLabel:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

#categoryCheckbox {
  display: none;
}

#checkboxItems {
  text-align: left;
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

#Delete {
  background: #ff6565;
}
</style>
