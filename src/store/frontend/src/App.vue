<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/books">List Books</router-link> |
      <router-link to="/order">Place Order</router-link>
    </nav>
    <router-view />
  </div>
</template>

<style>
nav {
  margin-bottom: 20px;
}

nav a {
  text-decoration: none;
  color: #007bff;
}

nav a:hover {
  text-decoration: underline;
}

#app {
  font-family: Arial, sans-serif;
  margin: 20px;
}
</style>
