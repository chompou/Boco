import { mount, shallowMount } from "@vue/test-utils";
import LoginView from "@/views/LoginView";
import store from "@/store";
import router from "@/router";

describe("Loginform", () => {
  it("Wrong credentials message is visible if login fail", async () => {
    const wrapper = shallowMount(LoginView);
    await wrapper.setData({
      failedLogin: true,
    });

    expect(wrapper.find("#false-cred-id").isVisible()).toBe(true);
  });

  it("Check that Login message renders", () => {
    const loginTitle = "Please login!";
    const wrapper = mount(LoginView, {
      global: {
        plugins: [store, router],
      },
    });
    const loginMessage = wrapper.find("#login-label");
    expect(loginMessage.text()).toMatch(loginTitle);
  });
});
