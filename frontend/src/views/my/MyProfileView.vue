<template>
  <div class="profileBar_container">
    <profile-bar-component />
  </div>
  <router-view></router-view>
</template>
<script>
import ProfileBarComponent from "@/components/ProfileBarComponent";
export default {
  components: { ProfileBarComponent },
};
</script>
