// file: src/test/kotlin/com/demo/service/BookServiceImplTest.kt

package com.demo.service

import com.demo.dto.BookResponse
import com.demo.dto.ListBookRequest
import com.demo.persistence.entity.Book
import com.demo.persistence.repository.BookRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

class BookServiceTest {

    private val bookRepository: BookRepository = Mockito.mock(BookRepository::class.java)
    private val bookService: BookService = BookServiceImpl(bookRepository)

    @Test
    fun `list should return correct book responses`() {
        // Arrange
        val request = ListBookRequest(page = 0, size = 10)
        val books = listOf(
            Book(
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
            Book(
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
        val page: Page<Book> = PageImpl(books)
        Mockito.`when`(bookRepository.findAll(PageRequest.of(request.page, request.size))).thenReturn(page)

        // Act
        val actualResponse = bookService.list(request)

        // Assert
        assertEquals(2, actualResponse.size)
        assertEquals("1", actualResponse[0].id)
        assertEquals("Book One", actualResponse[0].title)
        assertEquals("1234567890", actualResponse[0].isbn)
    }
}
