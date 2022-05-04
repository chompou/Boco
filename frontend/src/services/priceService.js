function normalize(value, type) {
  switch (type) {
    case "Hour":
      return value;
    case "Day":
      return value / 24;
    case "Week":
      return value / (24 * 7);
    default:
      return null;
  }
}

function scale(value, type) {
  switch (type) {
    case "Hour":
      return value;
    case "Day":
      return value * 24;
    case "Week":
      return value * (24 * 7);
    default:
      return null;
  }
}

var formatter = new Intl.NumberFormat("nb-NO", {
  style: "currency",
  currency: "NOK",
});

export default {
  parseHours(fromTime, toTime) {
    return Math.ceil((new Date(toTime) - new Date(fromTime)) / 36e5);
  },

  parsePrice(price, priceType) {
    return normalize(price, priceType);
  },

  displayPrice(item) {
    return formatter.format(scale(item.price, item.priceType));
  },

  displayDuration(hours, type) {
    return Math.ceil(normalize(hours, type));
  },

  leasePrice(item, hours) {
    return (
      this.displayPrice(item) * Math.ceil(normalize(hours, item.priceType))
    );
  },
};
