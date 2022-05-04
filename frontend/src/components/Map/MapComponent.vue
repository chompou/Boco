<template>
  <GMapMap
    :center="{ lat: 63.4305149, lng: 10.3950528 }"
    :zoom="9"
    style="width: 350px; height: 200px"
  >
    <GMapMarker
      :key="index"
      v-for="(m, index) in markers"
      :position="m.position"
      :clickable="true"
    />
  </GMapMap>
</template>

<script>
export default {
  data() {
    return {
      markers: [
        {
          position: {
            lat: 63.4305149,
            lng: 10.3950528,
          },
        },
      ],
    };
  },
};
</script>
