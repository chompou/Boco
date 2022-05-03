export default {
  fromDate(lease) {
    return new Date(lease.fromDatetime * 1e3);
  },

  toDate(lease) {
    return new Date(lease.toDatetime * 1e3);
  },

  displayDate(date) {
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  },
};
