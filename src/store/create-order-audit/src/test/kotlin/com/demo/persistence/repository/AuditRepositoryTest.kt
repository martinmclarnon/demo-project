// file: src/test/kotlin/com/demo/persistence/repository/AuditRepositoryTest.kt

package com.demo.persistence.repository

import com.demo.persistence.entity.Audit
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class AuditRepositoryTest {

    private val auditRepository: AuditRepository = mockk()

    @Test
    fun `save should persist audit record`() {
        // Arrange
        val audit = Audit(
            id = "12345",
            rawPayload = mapOf("key1" to "value1", "key2" to "value2")
        )
        val persistedAudit = audit.copy(id = "persisted-id")

        every { auditRepository.save(audit) } returns persistedAudit
        every { auditRepository.findById("persisted-id") } returns Optional.of(persistedAudit)

        // Act
        val savedAudit = auditRepository.save(audit)
        val foundAudit = auditRepository.findById("persisted-id").get()

        // Assert
        verify { auditRepository.save(audit) }
        verify { auditRepository.findById("persisted-id") }
        assertEquals(persistedAudit.rawPayload, foundAudit.rawPayload)
    }
}
