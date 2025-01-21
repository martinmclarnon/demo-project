// file: src/test/kotlin/com/demo/controller/BlogControllerTest.kt

package com.demo.controller

import com.demo.dto.BlogResponse
import com.demo.dto.ListBlogRequest
import com.demo.dto.WebResponse
import com.demo.service.BlogService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BlogController::class)
class BlogControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var blogService: BlogService

    @Test
    fun `listBlogs should return a valid response`() {
        // Arrange
        val page = 0
        val size = 10
        val request = ListBlogRequest(page = page, size = size)
        val blogResponses = listOf(
            BlogResponse(id = "1", post = "First Post"),
            BlogResponse(id = "2", post = "Second Post")
        )
        val expectedResponse = WebResponse(
            code = 200,
            status = "OK",
            data = blogResponses
        )

        Mockito.`when`(blogService.list(request)).thenReturn(blogResponses)

        // Act & Assert
        mockMvc.perform(
            get("/v1/marketing/blogs")
                .param("page", page.toString())
                .param("size", size.toString())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.data[0].id").value("1"))
            .andExpect(jsonPath("$.data[0].post").value("First Post"))
            .andExpect(jsonPath("$.data[1].id").value("2"))
            .andExpect(jsonPath("$.data[1].post").value("Second Post"))
    }
}
