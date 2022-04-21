<template>
  <div class="frontPageWrap container">
    <div class="justify-content-center">
      <label>Search: </label>
      <input id="searchTxtField" class="mt-3" />
      <div class="dropdown mt-2">
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
    </div>
  </div>
</template>

<style scoped>
#searchTxtField {
  width: 300px;
}
</style>
