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
            multiple="multiple"
            @change="previewMultiImage"
            class="form-control-file"
            id="my-file"
          />

          <div class="border p-2 mt-3">
            <p>Preview Here:</p>
            <template v-if="preview_list.length">
              <div v-for="(item, index) in preview_list" :key="index">
                <img :src="item" class="img-fluid" />
                <p class="mb-0">file name: {{ image_list[index].name }}</p>
                <p>size: {{ image_list[index].size / 1024 }}KB</p>
              </div>
            </template>
          </div>
        </div>
      </form>
    </div>
    <div class="w-100"></div>
    <div class="col-12 mt-3 text-center">
      <button id="resetButton" @click="reset">Clear All</button>
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
        <label id="valuta">kr</label>
        <select v-model="leaseType">
          <option disabled value="">/Hour</option>
          <option>/Hour</option>
          <option>/Day</option>
          <option>/Week</option>
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
        <button class="CreateButton">Create</button>
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
      preview_list: [],
      image_list: [],
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
    previewMultiImage: function (event) {
      let input = event.target;
      let count = input.files.length;
      let index = 0;
      if (input.files) {
        while (count--) {
          let reader = new FileReader();
          reader.onload = (e) => {
            this.preview_list.push(e.target.result);
          };
          this.image_list.push(input.files[index]);
          reader.readAsDataURL(input.files[index]);
          index++;
        }
      }
    },
    reset: function () {
      console.log(this.image_list);
      this.image = null;
      this.preview = null;
      this.image_list = [];
      this.preview_list = [];
    },
  },
  created() {
    apiService
      .createItem({
        title: this.title,
        address: this.address,
        price: this.price,
        priceType: this.leaseType,
        description: this.description,
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style scoped>
#container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#setImage {
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

#imageButton {
  width: 150px;
}

.CreateButton,
#resetButton {
  border: 1px solid #39495c;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  padding: 5px;
  background: white;
  margin: 20px;
}

.CreateButton:hover,
#resetButton:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

#Delete {
  background: #ff6565;
}
</style>
