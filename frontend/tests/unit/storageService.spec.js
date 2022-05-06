import storageService from "@/services/storageService";

let mockStorage = {};

beforeAll(() => {
  global.Storage.prototype.setItem = jest.fn((key, value) => {
    mockStorage[key] = value;
  });
  global.Storage.prototype.getItem = jest.fn((key) => mockStorage[key]);

  global.Storage.prototype.removeItem = jest.fn(
    (key) => delete mockStorage[key]
  );
});

beforeEach(() => {
  mockStorage = {};
});

afterEach(() => jest.clearAllMocks());

afterAll(() => {
  global.Storage.prototype.setItem.mockReset();
  global.Storage.prototype.getItem.mockReset();
});

describe("getting and setting storage", () => {
  test("setting and getting a user in localstorage", () => {
    const user = "testuser";

    storageService.setUser(user);
    expect(global.Storage.prototype.setItem).toHaveBeenCalled();
    expect(JSON.parse(mockStorage["user"]).value).toEqual(user);

    var storedUser = storageService.getUser();
    expect(storedUser).toEqual(user);
  });

  test("getting a user returns null when not set", () => {
    var user = storageService.getUser();
    expect(user).toEqual(null);
  });

  test("overwiting existing storage", () => {
    storageService.setUser("user1");
    storageService.setUser("user2");
    expect(JSON.parse(mockStorage["user"]).value).toEqual("user2");
    expect(storageService.getUser()).toEqual("user2");
  });

  test("setting and getting a token in localstorage", () => {
    const token =
      "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSm9lIENvZGVyIn0.5dlp7GmziL2QS06sZgK4mtaqv0_xX4oFUuTDh1zHK4U";

    storageService.setToken(token);
    expect(global.Storage.prototype.setItem).toHaveBeenCalled();
    expect(JSON.parse(mockStorage["token"]).value).toEqual(token);

    var storedToken = storageService.getToken();
    expect(storedToken).toEqual(token);
  });

  test("getting a token returns null when not set", () => {
    var user = storageService.getToken();
    expect(user).toEqual(null);
  });
});

describe("clearing storage", () => {
  test("clearing a user from localstorage", () => {
    storageService.setUser("testuser");
    storageService.clearUser();
    expect(storageService.getUser()).toEqual(null);
  });

  test("clearing a token from localstorage", () => {
    storageService.setToken("tokentokentokentoken");
    storageService.clearToken();
    expect(storageService.getToken()).toEqual(null);
  });
});

describe("storage expiration", () => {
  jest.useFakeTimers();

  test("user expires", () => {
    jest.setSystemTime(Date.now());
    storageService.setUser("testuser");

    jest.setSystemTime(Date.now() + 1000);
    expect(storageService.getUser()).toEqual("testuser");

    jest.setSystemTime(Date.now() + 1000 * 3600 * 24);
    var storedUser = storageService.getUser();
    expect(storedUser).toEqual(null);
  });

  test("token expires", () => {
    jest.setSystemTime(Date.now());
    storageService.setToken("tokentokentoken");

    jest.setSystemTime(Date.now() + 1000);
    expect(storageService.getToken()).toEqual("tokentokentoken");

    jest.setSystemTime(Date.now() + 1000 * 3600 * 24);
    var storedUser = storageService.getToken();
    expect(storedUser).toEqual(null);
  });
});
