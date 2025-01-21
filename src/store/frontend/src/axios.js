import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:10081/store-books-bff', // TODO: Replace with API base URL from environment file
});



export default apiClient;