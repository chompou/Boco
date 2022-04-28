import { shallowMount } from "@vue/test-utils";
import App from "@/App";

describe("NavigationBar", () => {
  it("should show the logout button, if the user is logged in", async () => {
    const wrapper = shallowMount(App);
    await wrapper.find(this.loggedIn()).setValue(true);

    expect(wrapper.find("log-out-button").isVisible()).toBe(true);
  });
});
