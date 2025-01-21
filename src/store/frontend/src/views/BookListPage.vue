<!-- file: src/views/BookListPage.vue -->

<template>
  <div>
    <h1>Book List</h1>
    <div v-if="loading">Loading...</div>
    <div v-else>
      <Book v-for="book in books" :key="book.id" :book="book" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Book from "../components/BookComponent.vue";

const BASE_URL = process.env.VUE_APP_BASE_URL;
const API_ENDPOINT = process.env.VUE_APP_API_ENDPOINT;
const booklist = `${BASE_URL}${API_ENDPOINT}`;

export default {
  components: { Book },
  data() {
    return {
      books: [],
      loading: true,
    };
  },
  async mounted() {
    try {
      const response = await axios.get(booklist);
      this.books = response.data.data;
    } catch (error) {
      console.error("Error fetching books:", error);
    } finally {
      this.loading = false;
    }
  },
};
</script>
