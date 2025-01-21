<!-- file: src/components/BlogComponent.vue -->

<template>
  <div class="blog">
      <p><strong>Post:</strong> {{ blog.post }}</p>
  </div>
</template>

<script>
export default {
  props: {
    blog: Object
  }
}
</script>

<style scoped>
.blog {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}
</style>
