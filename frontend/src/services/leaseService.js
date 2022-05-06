export default {
  fromDate(lease) {
    return new Date(lease.fromDatetime);
  },

  toDate(lease) {
    return new Date(lease.toDatetime);
  },

  displayDate(date) {
    return (
      date.toLocaleDateString() +
      " " +
      `${date.getHours()}.${("0" + date.getMinutes()).slice(-2)}`
    );
  },

  getStatus(lease, owner) {
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
      if (owner && !lease.leaseeReview) return "Pending Review";
      if (!owner && !(lease.itemReview || lease.ownerReview))
        return "Pending Review";
      else return "Completed";
    } else if (to < now) {
      if (!lease.isApproved) return "Expired";
      if (!lease.isCompleted) return "Overdue";
      if (owner && !lease.leaseeReview) return "Pending Review";
      if (!owner && !(lease.itemReview || lease.ownerReview))
        return "Pending Review";
      else return "Completed";
    }

    return null;
  },
};
