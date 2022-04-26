import App from "@/App";
import { mount } from "@vue/test-utils";
import router from "@/router";

describe("Navbar", () => {
  test("If user is not logged in, do not show logout button", async () => {
    const mockStore = {
      state: {
        user: [
          {
            loggedIn: false,
          },
        ],
      },
      commit: jest.fn(),
    };

    const wrapper = mount(App, {
      global: {
        mocks: {
          $store: mockStore,
        },
        plugins: [router],
      },
    });

    expect(wrapper.find("button").isVisible()).toBe(false);
  });

  test("If logged in, show logout button", async () => {
    const mockStore = {
      state: {
        user: [
          {
            loggedIn: true,
          },
        ],
      },
      commit: jest.fn(),
    };

    const wrapper = mount(App, {
      global: {
        mocks: {
          $store: mockStore,
        },
        plugins: [router],
      },
    });

    expect(wrapper.find("button").isVisible()).toBe(true);
  });
});
