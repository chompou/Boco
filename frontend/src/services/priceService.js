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

  displayPrice(item, type = null) {
    if (type != null) return scale(item, type);
    return scale(item.price, item.priceType);
  },

  formattedPrice(value) {
    return formatter.format(value);
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
