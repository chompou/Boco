<template id="container">
  <ol-map
    :loadTilesWhileAnimating="true"
    :loadTilesWhileInteracting="true"
    style="height: 200px; width: 350px"
  >
    <ol-view
      ref="view"
      :center="center"
      :rotation="rotation"
      :zoom="zoom"
      :projection="projection"
    />

    <ol-tile-layer>
      <ol-source-osm />
    </ol-tile-layer>

    <ol-overlay :position="center">
      <template v-slot="slotProps">
        <div id="marker">
          <p>{{ slotProps.position }}</p>
        </div>
      </template>
    </ol-overlay>
  </ol-map>
</template>

<script>
import { ref } from "vue";
export default {
  setup() {
    const center = ref([10.411427127485, 63.42117489514054]);
    const projection = ref("EPSG:4326");
    const zoom = ref(12);
    const rotation = ref(0);
    return {
      center,
      projection,
      zoom,
      rotation,
    };
  },
};
</script>

<style scoped>
#marker {
  width: 20px;
  height: 20px;
  border: 1px solid #000000;
  border-radius: 10px;
  background-color: #f8303f;
}

p {
  visibility: hidden;
}
</style>
