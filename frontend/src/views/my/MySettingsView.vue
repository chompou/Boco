<template>
  <div class="settings-container">
    <h1>Settings</h1>
    <div class="input-container">
      <div id="information" class="input-grid">
        <label> Display Name </label>
        <input v-bind="displayName" />

        <label> E-Mail </label>
        <input type="email" v-bind="email" />

        <label> Address </label>
        <input />

        <label> Telephone </label>
        <input />

        <label> Description </label>
        <input style="height: 100px" />
      </div>

      <div id="password" class="input-grid" style="max-height: 0px">
        <label> Old Password </label>
        <input />

        <label> New Password </label>
        <input />

        <label> Confirm New Password </label>
        <input />
      </div>
      <div id="buttons" class="button-container">
        <button id="save-btn" class="boco-btn">Save</button>
        <button class="boco-btn">Reset</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.settings-container {
  display: flex;
  flex-direction: column;
  gap: 50px;
}

.input-container {
  display: grid;
  grid-template-columns: 40% 40%;
  justify-content: center;
  grid-template-areas:
    "info pass"
    "info button";
}

#information {
  grid-area: info;
}

#password {
  grid-area: pass;
}

#buttons {
  grid-area: button;
  justify-self: center;
  align-self: center;
}

.input-grid {
  display: grid;
  grid-template-columns: auto auto;
  column-gap: 15px;
  row-gap: 10px;
}

.button-container {
  display: flex;
  gap: 40px;
}
</style>
