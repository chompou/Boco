<template>
  <div class="container mt-2">
    <h3 class="title">Notifications <font-awesome-icon icon="bell" /></h3>
    <hr />
    <h4>Unread</h4>
    <ul class="mt-2">
      <li
        v-for="notification in $store.getters.getUnreadNotifications"
        :key="notification.id"
      >
        <NotificationComponent
          height="70"
          :text="notification.message"
          :id="notification.id"
        />
      </li>
    </ul>
    <h4 class="read">Read</h4>
    <ul class="mt-3">
      <li
        v-for="notification in $store.getters.getReadNotifications"
        :key="notification.id"
      >
        <NotificationComponent
          height="70"
          leftDivBgColor="lightGray"
          :text="
            'ID: ' +
            notification.id +
            ' message: ' +
            notification.message +
            ' url: ' +
            notification.url
          "
          :id="notification.id"
        />
      </li>
    </ul>
  </div>
</template>

<script>
import NotificationComponent from "@/components/NotificationComponent";
export default {
  name: "NotificationsPageView",
  components: { NotificationComponent },
};
</script>

<style scoped>
ul {
  list-style-type: none;
}

ul li {
  margin: 8px;
}

.read {
  margin-top: 50px;
}
</style>
