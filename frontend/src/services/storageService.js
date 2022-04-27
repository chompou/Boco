function setStorage(key, value, ttl) {
  const item = { value: value, expiry: Date.now() + ttl };
  localStorage.setItem(key, JSON.stringify(item));
}

function getStorage(key) {
  let itemString = localStorage.getItem(key);
  if (!itemString) {
    return null;
  }

  let item = JSON.parse(itemString);
  if (item.expiry < Date.now()) {
    clearStorage(key);
    return null;
  }

  return item.value;
}

function clearStorage(key) {
  localStorage.removeItem(key);
}

export default {
  setUser(user) {
    setStorage("user", user, 1000 * 3600);
  },

  getUser() {
    return getStorage("user");
  },

  clearUser() {
    clearStorage("user");
  },

  setToken(token) {
    setStorage("token", token, 1000 * 3600);
  },

  getToken() {
    return getStorage("token");
  },

  clearToken() {
    clearStorage("token");
  },
};
