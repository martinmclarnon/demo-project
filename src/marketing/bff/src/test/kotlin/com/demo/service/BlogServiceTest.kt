// file: src/test/kotlin/com/demo/service/BlogServiceTest.kt

package com.demo.service

import com.demo.dto.BlogResponse
import com.demo.dto.ListBlogRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class BlogServiceTest {

    private val blogService: BlogService = Mockito.mock(BlogService::class.java)

    @Test
    fun `list should return correct blog responses`() {
        // Arrange
        val request = ListBlogRequest(page = 0, size = 10)
        val expectedResponse = listOf(
            BlogResponse(id = "1", post = "First Post"),
            BlogResponse(id = "2", post = "Second Post")
        )
        Mockito.`when`(blogService.list(request)).thenReturn(expectedResponse)

        // Act
        val actualResponse = blogService.list(request)

        // Assert
        assertEquals(expectedResponse, actualResponse)
    }
}
