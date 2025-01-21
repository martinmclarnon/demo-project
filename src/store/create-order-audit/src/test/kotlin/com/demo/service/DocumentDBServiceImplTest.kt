// file: src/test/kotlin/com/demo/service/DocumentDBServiceImplTest.kt

package com.demo.service

import com.demo.dto.CreateOrderRequest
import com.demo.persistence.entity.Audit
import com.demo.persistence.repository.AuditRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

class DocumentDBServiceImplTest {

    private val auditRepository: AuditRepository = Mockito.mock(AuditRepository::class.java)
    private val service = DocumentDBServiceImpl(auditRepository)

    @Test
    fun `insertDocument should save audit record and return true`() {
        // Arrange
        val uuid = "test-uuid"
        val request = CreateOrderRequest(
            bookId = "12345",
            userId = "user1",
            quantity = "2",
            orderDate = "2024-11-26"
        )
        val auditCaptor = ArgumentCaptor.forClass(Audit::class.java)

        // Act
        val result = service.insertDocument(uuid, request)

        // Assert
        Mockito.verify(auditRepository).save(auditCaptor.capture())
        val savedAudit = auditCaptor.value
        assertTrue(savedAudit.rawPayload.containsKey("bookId"))
        assertTrue(savedAudit.rawPayload.containsKey("userId"))
        assertTrue(savedAudit.rawPayload["bookId"] == "12345")
        assertTrue(result)
    }

    @Test
    fun `insertDocument should handle exception and return false`() {
        // Arrange
        val uuid = "test-uuid"
        val request = CreateOrderRequest(
            bookId = "12345",
            userId = "user1",
            quantity = "2",
            orderDate = "2024-11-26"
        )
        Mockito.`when`(auditRepository.save(Mockito.any(Audit::class.java))).thenThrow(RuntimeException("DB Error"))

        // Act
        val result = service.insertDocument(uuid, request)

        // Assert
        assertTrue(!result)
    }
}
