<template>
  <div class="main">
    <img alt="Vue logo" src="../assets/logo.png" />
    <h1>Welcome!</h1>
    <div class="container">
      <p>
        Id: <span>{{ greeting.id }}</span>
      </p>
      <p>
        Message: <span>{{ greeting.content }}!</span>
      </p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Main",
  data: function () {
    return {
      greeting: { id: "XXX", content: "Hello World" },
    };
  },
  mounted() {
    axios
      .get("http://localhost:8081/resource")
      .then((response) => (this.greeting = response.data));
  },
};
</script>
