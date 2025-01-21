// file: src/test/kotlin/com/demo/controller/BookControllerTest.kt

package com.demo.controller

import com.demo.dto.BookResponse
import com.demo.dto.ListBookRequest
import com.demo.dto.WebResponse
import com.demo.service.BookService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(BookController::class)
class BookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @Test
    fun `listBooks should return a valid response`() {
        // Arrange
        val page = 0
        val size = 10
        val request = ListBookRequest(page = page, size = size)
        val bookResponses = listOf(
            BookResponse(
                id = "1",
                title = "Book One",
                isbn = "1234567890",
                author = "Author One",
                publisher = "Publisher One",
                publishedDate = Date(),
                numberOfPages = 200,
                languageWrittenIn = "English",
                review = "Great book"
            ),
            BookResponse(
                id = "2",
                title = "Book Two",
                isbn = "0987654321",
                author = "Author Two",
                publisher = "Publisher Two",
                publishedDate = Date(),
                numberOfPages = 300,
                languageWrittenIn = "Spanish",
                review = "Informative"
            )
        )
        val expectedResponse = WebResponse(
            code = 200,
            status = "OK",
            data = bookResponses
        )

        Mockito.`when`(bookService.list(request)).thenReturn(bookResponses)

        // Act & Assert
        mockMvc.perform(
            get("/v1/store/books")
                .param("page", page.toString())
                .param("size", size.toString())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.data[0].id").value("1"))
            .andExpect(jsonPath("$.data[0].title").value("Book One"))
            .andExpect(jsonPath("$.data[1].id").value("2"))
            .andExpect(jsonPath("$.data[1].title").value("Book Two"))
    }
}
