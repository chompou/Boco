<template>
  <div id="container">
    <h1>Create a new Item</h1>
    <div>
      <div class="file-upload-form">
        Upload image:
        <input type="file" @change="previewImage" accept="image/*" />
      </div>
      <div class="image-preview" v-if="imageData.length > 0">
        <img class="preview" :src="imageData" />
      </div>
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
export default {
  data() {
    return {
      imageData: "",
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
      // Reference to the DOM input element
      var input = event.target;
      // Ensure that you have a file before attempting to read it
      if (input.files && input.files[0]) {
        // create a new FileReader to read this image and convert to base64 format
        var reader = new FileReader();
        // Define a callback function to run, when FileReader finishes its job
        reader.onload = (e) => {
          // Note: arrow function used here, so that "this.imageData" refers to the imageData of Vue component
          // Read image as base64 and set to imageData
          this.imageData = e.target.result;
        }; // Start the reader job - read file as a data url (base64 format)
        reader.readAsDataURL(input.files[0]);
      }
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

.preview {
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

.CreateButton {
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

.CreateButton:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

#Delete {
  background: #ff6565;
}
</style>
