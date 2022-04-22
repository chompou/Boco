<template>
  <div class="frontPageWrap container position-relative mt-2">
    <div class="searchFieldWrap justify-content-center">
      <label>Search: </label>
      <input id="searchTxtField" class="mt-1" />
    </div>
    <div class="position-absolute dropdown top-0 end-0">
      <button
        class="btn btn-secondary dropdown-toggle"
        type="button"
        id="dropdownMenuButton"
        data-bs-toggle="dropdown"
        aria-expanded="false"
      >
        Search by:
      </button>
      <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
        <li><a class="dropdown-item" href="#">Name</a></li>
        <li><a class="dropdown-item" href="#">Score</a></li>
        <li><a class="dropdown-item" href="#">Hest</a></li>
      </ul>
    </div>
    <div class="mt-5">
      <router-link to="items">Items</router-link>
      <font-awesome-icon icon="car" class="icon" />
      <font-awesome-icon icon="wrench" class="icon" />
      <font-awesome-icon icon="umbrella" class="icon" />
      <font-awesome-icon icon="lemon" class="icon" />
      <font-awesome-icon icon="children" class="icon" />
      <font-awesome-icon icon="toilet-paper" class="icon" />
    </div>
  </div>
</template>

<style scoped>
/*div {
  border: solid 1px black;
}*/

.icon {
  font-size: 4vw;
  margin-left: 40px;
}
</style>
