import priceService from "@/services/priceService";

describe("CalculateHours()", () => {
  test("calculating hour difference between two timestamps", () => {
    let hours;

    hours = priceService.calculateHours("2022-05-17T15:00", "2022-05-17T17:00");
    expect(hours).toBe(2);

    hours = priceService.calculateHours("2022-04-20T16:00", "2022-04-20T16:45");
    expect(hours).toBe(1);

    hours = priceService.calculateHours("2022-03-01T13:10", "2022-03-01T15:50");
    expect(hours).toBe(3);

    hours = priceService.calculateHours("2022-03-02T12:00", "2022-03-29T15:00");
    expect(hours).toBe(650);
  });
});

describe("CalculatePrice", () => {
  test("calculating interval price from item payload", () => {
    let item = { price: null, priceType: null };

    (item.price = 50), (item.priceType = "Hour");
    expect(priceService.calculatePrice(item, 4)).toBe(200);

    (item.price = 1200), (item.priceType = "Day");
    expect(priceService.calculatePrice(item, 4)).toBe(200);
  });
});
