// file: src/test/kotlin/com/demo/service/BookService.kt

package com.demo.service

import com.demo.dto.ListBookRequest
import com.demo.dto.BookResponse

interface BookService {

    fun list(listBookRequest: ListBookRequest): List<BookResponse>
}