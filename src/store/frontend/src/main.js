import { createApp } from "vue";
import App from "./App.vue";
import { createRouter, createWebHistory } from "vue-router";

import HomePage from "./views/HomePage.vue";
import BookListPage from "./views/BookListPage.vue";
import PlaceOrderPage from "./views/PlaceOrderPage.vue";

const routes = [
    { path: "/", component: HomePage },
    { path: "/books", component: BookListPage },
    { path: "/order", component: PlaceOrderPage },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

createApp(App).use(router).mount("#app");
