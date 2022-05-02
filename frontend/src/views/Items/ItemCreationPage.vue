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
          v-model="this.title"
          placeholder="Name"
          id="ItemName"
        />
      </div>
      <div class="ItemId">
        <p>Address:</p>
        <input
          class="baseInput"
          v-model="this.address"
          placeholder="Address"
          id="Address"
        />
      </div>
      <div class="ItemId">
        <p>price:</p>
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
      <form class="checkboxItem">
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="SportAndHiking"
            value="SportAndHiking"
            v-model="category"
          />
          <label for="SportAndHiking">Sport/Hiking</label>
        </div>
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="Electronics"
            value="Electronics"
            v-model="category"
          />
          <label for="Electronics">Electronics</label>
        </div>
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="Vehicle"
            value="Vehicle"
            v-model="category"
          />
          <label for="Vehicle">Vehicle</label>
        </div>
        <div class="checkboxItem">
          <input type="checkbox" id="Tools" value="Tools" v-model="category" />
          <label for="Tools">Tools</label>
        </div>
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="Interior"
            value="Interior"
            v-model="category"
          />
          <label for="Interior">Interior</label>
        </div>
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="HobbyAndEntertainment"
            value="HobbyAndEntertainment"
            v-model="category"
          />
          <label for="HobbyAndEntertainment">Hobby/Entertainment</label>
        </div>
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="SchoolAndOffice"
            value="SchoolAndOffice"
            v-model="category"
          />
          <label for="SchoolAndOffice">School/Office</label>
        </div>
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="HomeAndGarden"
            value="HomeAndGarden"
            v-model="category"
          />
          <label for="HomeAndGarden">Home/Garden</label>
        </div>
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="Fashion"
            value="Fashion"
            v-model="category"
          />
          <label for="Fashion">Fashion</label>
        </div>
        <div class="checkboxItem">
          <input
            type="checkbox"
            id="MusicalInstrument"
            value="MusicalInstrument"
            v-model="category"
          />
          <label for="MusicalInstrument">Musical Instrument</label>
        </div>
      </form>
      <div id="descriptionField">
        <p>Description</p>
        <textarea
          v-model="this.description"
          placeholder="Description"
          id="description"
          name="description"
        ></textarea>
      </div>
      <div id="CreateButtons" class="element">
        <button class="CreateButton" v-on:click="submit">Create</button>
        <button id="Delete" class="CreateButton">Delete</button>
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
      category: [this.category],
      checked: false,
      description: this.description,
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
      this.toast.success("Listing was successfully created", {
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

.checkboxItem {
  display: grid;
  grid-template-rows: repeat(5, min-content);
  grid-template-columns: repeat(2, 1fr);
  border: 1px solid var(--button-color);
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
