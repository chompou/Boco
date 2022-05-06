<template>
  <div class="about">
    <img id="us" src="@/assets/mainLogo.png" alt="us" />
    <div id="text">
      <div id="head">
        <h1>Boco</h1>
        <a href="https://gitlab.stud.idi.ntnu.no/olavdei/idatt2106_2022_04">
          <img id="gitlab" src="@/assets/gitlab.png" alt="gitlab" />
        </a>
      </div>
      <p>A leasing website for items and services!</p>
      <br />
      <p>Created by 9 computer science students of NTNU, Trondheim.</p>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.about {
  margin-top: 20px;
  display: grid;
  justify-content: center;
}

#head {
  display: flex;
  justify-content: center;
}

#us {
  width: 500px;
  height: 400px;
}

#gitlab {
  margin-left: 20px;
  margin-top: 12px;
  height: 25px;
  width: 25px;
}

#text {
  margin-top: 20px;
  text-align: center;
}
</style>
