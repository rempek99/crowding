<template>
  <div class="login">
    <p>Provide Credentials:</p>
    <input v-model="login" placeholder="Login" />
    <input v-model="password" placeholder="Password" type="password" />
    <button @click="printData()">Click</button>
  </div>
</template>

<script>
export default {
  name: "Login",
  data: function () {
    return {
      login: null,
      password: null,
    };
  },
  methods: {
    printData: function () {
      console.log(this.login);
      console.log(this.password);
    },
  },
};
</script>