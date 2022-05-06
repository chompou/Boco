import { shallowMount } from "@vue/test-utils";
import LoginView from "@/views/LoginView";
import store from "@/store";
import router from "@/router";

afterEach(() => jest.clearAllMocks());

describe("Login form", () => {
  it("Wrong credentials message is visible if login fail", async () => {
    const wrapper = shallowMount(LoginView);
    await wrapper.setData({
      failedLogin: true,
    });

    expect(wrapper.find("#false-cred-id").isVisible()).toBe(true);
  });

  it("Check that Login message renders", () => {
    const loginTitle = "Please login!";
    const wrapper = shallowMount(LoginView, {
      global: {
        plugins: [store, router],
      },
    });
    const loginMessage = wrapper.find("#login-label");
    expect(loginMessage.text()).toMatch(loginTitle);
  });

  test("Check that input is working", async () => {
    const wrapper = shallowMount(LoginView, {
      global: {
        plugins: [store, router],
      },
    });
    const username = "user";
    const password = "pass";
    const usernameInput = wrapper.find("#username");
    const passwordInput = wrapper.find("#password");

    await usernameInput.setValue("user");
    await passwordInput.setValue("pass");

    expect(usernameInput.element.value).toEqual(username);
    expect(passwordInput.element.value).toEqual(password);
  });
});
