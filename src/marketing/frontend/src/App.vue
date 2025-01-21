<template>
  <div id="app">
    <h1>Blogs</h1>
    <div v-if="loading">Loading...</div>
    <div v-else>
      <Blog v-for="blog in blogs" :key="blog.id" :blog="blog" />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Blog from './components/BlogComponent.vue';

const BASE_URL = process.env.VUE_APP_BASE_URL;
const API_ENDPOINT = process.env.VUE_APP_API_ENDPOINT;
const bloglist = `${BASE_URL}${API_ENDPOINT}`;

console.log(bloglist);

export default {
  name: 'App',
  components: {
    Blog
  },
  data() {
    return {
      blogs: [],
      loading: true
    };
  },
  async mounted() {
    try {
      const response = await axios.get(bloglist);
      this.blogs = response.data.data;
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      this.loading = false;
    }
  }
}
</script>

<style>
#app {
  font-family: Arial, sans-serif;
  margin-top: 20px;
}
</style>
