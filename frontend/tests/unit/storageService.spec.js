import storageService from "@/services/storageService";

let mockStorage = {}

beforeAll(() => {
    global.Storage.prototype.setItem = jest.fn((key, value) => {
      mockStorage[key] = value
    })
    global.Storage.prototype.getItem = jest.fn((key) => mockStorage[key])
  })

beforeEach(() => {
    mockStorage = {}
})

afterAll(() => {
    global.Storage.prototype.setItem.mockReset()
    global.Storage.prototype.getItem.mockReset()
})

describe("setUser() and getUser()", () => {
    test("setting and getting a user in localstorage", () => {
        storageService.setUser("testuser")
        expect(global.Storage.prototype.setItem).toHaveBeenCalled();
        expect(JSON.parse(mockStorage['user']).value).toEqual("testuser");
        
        var user = storageService.getUser()
        expect(user).toEqual("testuser")
    })
})

describe("setToken() and getToken()", () => {
    test("")
})

describe("clearUser", () => {

});