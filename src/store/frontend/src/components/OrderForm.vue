<!-- file: src/components/OrderFrom.vue -->

<template>
  <div>
    <form @submit.prevent="submitOrder">
      <label for="bookId">Book ID:</label>
      <input type="text" id="bookId" v-model="orderPayload.bookId" required />

      <label for="userId">User ID:</label>
      <input type="text" id="userId" v-model="orderPayload.userId" required />

      <label for="quantity">Quantity:</label>
      <input type="number" id="quantity" v-model="orderPayload.quantity" required />

      <label for="orderDate">Order Date:</label>
      <input type="date" id="orderDate" v-model="orderPayload.orderDate" required />

      <button type="submit">Submit</button>
    </form>

    <p v-if="responseMessage">{{ responseMessage }}</p>
  </div>
</template>

<script>

export default {
  name: "OrderForm",
  data() {
    return {
      orderPayload: {
        bookId: "123",
        userId: "abc-456",
        quantity: "1",
        orderDate: new Date().toISOString().split("T")[0], // Default to today's date
      },
      responseMessage: "",
    };
  },
  methods: {
    async submitOrder() {
      const apiEndpoint = "http://localhost:10083/order-create-command-api/v1/order"; // TODO: Replace with API base URL from environment file
      try {
        const response = await fetch(apiEndpoint, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(this.orderPayload),
        });

        if (response.ok) {
          this.responseMessage = "Order submitted successfully!";
        } else {
          const errorData = await response.json();
          this.responseMessage = `Error: ${errorData.message || "Failed to submit order"}`;
        }
      } catch (error) {
        this.responseMessage = `Error: ${error.message}`;
      }
    },
  },
};
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

label {
  font-weight: bold;
}

input {
  padding: 5px;
  font-size: 14px;
}

button {
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
}

p {
  margin-top: 20px;
  font-weight: bold;
}
</style>
