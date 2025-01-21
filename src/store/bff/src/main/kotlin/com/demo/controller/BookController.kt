// file: src/main/kotlin/com/demo/controller/BookController.kt

package com.demo.controller

import com.demo.dto.BookResponse
import com.demo.dto.ListBookRequest
import com.demo.dto.WebResponse
import com.demo.service.BookService
import org.springframework.web.bind.annotation.*

@RestController
class BookController(val bookService: BookService) {

    @CrossOrigin(origins = ["http://localhost:10082"])
    @GetMapping(
        value = ["/v1/store/books"],
        produces = ["application/json"]
    )
    fun listBooks(@RequestParam(value = "size", defaultValue = "10") size: Int,
                  @RequestParam(value = "page", defaultValue = "0") page: Int): WebResponse<List<BookResponse>> {
        val request = ListBookRequest(page = page, size = size)
        val responses = bookService.list(request)
        return WebResponse(
            code = 200,
            status = "OK",
            data = responses
        )
    }
}