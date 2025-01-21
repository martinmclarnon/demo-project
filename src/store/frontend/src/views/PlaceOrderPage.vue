<!-- file: src/views/PlaceOrderPage.vue -->

<template>
  <div>
    <h1>Place an Order</h1>
    <OrderForm />
  </div>
</template>

<script>
import OrderForm from "../components/OrderForm.vue";

export default {
  components: { OrderForm },
};
</script>
