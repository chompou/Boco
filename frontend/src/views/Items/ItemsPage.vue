<template>
  <div class="container">
    <Sidebar />
    <transition>
      <div id="items" :style="{ 'margin-left': sidebarWidth }">
        <LargeItem v-for="item in items" :key="item" />
      </div>
    </transition>
  </div>
</template>

<script>
import Sidebar from "@/components/Sidebar/SidebarComponent";
import { sidebarWidth } from "@/components/Sidebar/state";
import LargeItem from "@/components/Items/LargeItem";
export default {
  components: { LargeItem, Sidebar },
  data() {
    return {
      items: [
        {
          leaseOverlay: false,
          my: false,
          id: 1234,
          title: "Wrench",
          category: ["tool", "electronic"],
          address: "Gløsveien 1",
          price: 50,
          leaseType: "/Day",
          description:
            "In North American English, wrench is the standard term. The most\n" +
            "          common shapes are called open-end wrench and box-end wrench. In\n" +
            "          American English, spanner refers to a specialized wrench with a series\n" +
            "          of pins or tabs around the circumference. (These pins or tabs fit into\n" +
            "          the holes or notches cut into the object to be turned.) In American\n" +
            "          commerce, such a wrench may be called a spanner wrench to distinguish\n" +
            "          it from the British sense of spanner.",
        },
        {
          leaseOverlay: false,
          my: false,
          id: 1234,
          title: "Wrench",
          category: ["tool", "electronic"],
          address: "Gløsveien 1",
          price: 50,
          leaseType: "/Day",
          description:
            "In North American English, wrench is the standard term. The most\n" +
            "          common shapes are called open-end wrench and box-end wrench. In\n" +
            "          American English, spanner refers to a specialized wrench with a series\n" +
            "          of pins or tabs around the circumference. (These pins or tabs fit into\n" +
            "          the holes or notches cut into the object to be turned.) In American\n" +
            "          commerce, such a wrench may be called a spanner wrench to distinguish\n" +
            "          it from the British sense of spanner.",
        },
        {
          leaseOverlay: false,
          my: false,
          id: 1234,
          title: "Wrench",
          category: ["tool", "electronic"],
          address: "Gløsveien 1",
          price: 50,
          leaseType: "/Day",
          description:
            "In North American English, wrench is the standard term. The most\n" +
            "          common shapes are called open-end wrench and box-end wrench. In\n" +
            "          American English, spanner refers to a specialized wrench with a series\n" +
            "          of pins or tabs around the circumference. (These pins or tabs fit into\n" +
            "          the holes or notches cut into the object to be turned.) In American\n" +
            "          commerce, such a wrench may be called a spanner wrench to distinguish\n" +
            "          it from the British sense of spanner.",
        },
        {
          leaseOverlay: false,
          my: false,
          id: 1234,
          title: "Wrench",
          category: ["tool", "electronic"],
          address: "Gløsveien 1",
          price: 50,
          leaseType: "/Day",
          description:
            "In North American English, wrench is the standard term. The most\n" +
            "          common shapes are called open-end wrench and box-end wrench. In\n" +
            "          American English, spanner refers to a specialized wrench with a series\n" +
            "          of pins or tabs around the circumference. (These pins or tabs fit into\n" +
            "          the holes or notches cut into the object to be turned.) In American\n" +
            "          commerce, such a wrench may be called a spanner wrench to distinguish\n" +
            "          it from the British sense of spanner.",
        },
      ],
    };
  },
  setup() {
    return { sidebarWidth };
  },
};
</script>

<style scoped>
#items {
  transition: 0.3s ease;
}
</style>
