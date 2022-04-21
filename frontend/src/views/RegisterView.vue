<template>
  <h2>Register new user</h2>
  <div class="mb-3">
    <label>Username</label>
    <input
      type="text"
      placeholder="Enter username"
      name="username"
      id="username"
      required
    />
    <br />
    <label>Email</label>
    <input
      type="email"
      placeholder="Enter email"
      name="email"
      id="email"
      required
    />
    <br />
    <label>Phone number</label>
    <input
      type="text"
      placeholder="Enter phone number"
      name="phoneNumber"
      id="phoneNumber"
      required
    />
    <br />
    <label>Password</label>
    <input
      type="password"
      placeholder="Enter password"
      name="password"
      id="password"
      required
    />
    <br />
    <label>Verify Password</label>
    <input
      type="password"
      placeholder="Enter password"
      name="password"
      id="verifyPassword"
      required
    />
    <br />
    <button type="submit" class="registrationbtn">Submit</button>
  </div>
</template>
