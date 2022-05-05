export default {
  fromDate(lease) {
    return new Date(lease.fromDatetime);
  },

  toDate(lease) {
    return new Date(lease.toDatetime);
  },

  displayDate(date) {
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
  },

  getStatus(lease) {
    let now, from, to;
    now = Date.now();
    from = this.fromDate(lease);
    to = this.toDate(lease);

    if (now < from) {
      if (!lease.isApproved) return "Pending Approval";
      else return "Upcoming";
    } else if (now < to) {
      if (!lease.ifApproved) return "Expired";
      if (!lease.isCompleted) return "In Progress";
      else return "Completed";
    } else if (to < now) {
      if (!lease.isApproved) return "Expired";
      if (!lease.isCompleted) return "Overdue";
      else return "Completed";
    }
    return null;
  },
};
