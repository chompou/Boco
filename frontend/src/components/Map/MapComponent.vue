<template>
  <GMapMap
    :center="{ lat: 51.093048, lng: 6.84212 }"
    :zoom="7"
    map-type-id="terrain"
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
      center: { lat: 51.093048, lng: 6.84212 },
      markers: [
        {
          position: {
            lat: 51.093048,
            lng: 6.84212,
          },
        },
      ],
    };
  },
};
</script>
