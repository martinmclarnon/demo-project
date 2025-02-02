// file: src/main/kotlin/com/demo/persistence/repository/BookRepository.kt

package com.demo.persistence.repository

import com.demo.persistence.entity.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, String> {
}