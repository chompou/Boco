import { shallowMount } from "@vue/test-utils";
import LoginView from "@/views/LoginView";

describe("Loginform", () => {
  it("Wrong credentials message is visible if login fail", async () => {
    const wrapper = shallowMount(LoginView);
    await wrapper.setData({
      failedLogin: true,
    });

    expect(wrapper.find("#false-cred-id").isVisible()).toBe(true);
  });
});
