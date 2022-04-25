import App from "@/App";
import { mount } from "@vue/test-utils";

//Test does not work because of unresolved variabele or type $store in App.vue
describe("Navbar", () => {
  test("If user is not logged in, do not show logout button", () => {
    const wrapper = mount(App);
    expect(wrapper.find("button").isVisible()).toBe(false);
  });
});
