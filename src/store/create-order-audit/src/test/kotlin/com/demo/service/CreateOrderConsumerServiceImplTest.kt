// file: src/test/kotlin/com/demo/service/CreateOrderConsumerServiceImplTest.kt

package com.demo.service

import com.demo.dto.CreateOrderRequest
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.kafka.support.Acknowledgment



class CreateOrderConsumerServiceImplTest {

    private val documentDBServiceImpl: DocumentDBServiceImpl = mockk(relaxed = true)
    private val acknowledgment: Acknowledgment = mockk(relaxed = true)
    private val service = CreateOrderConsumerServiceImpl(documentDBServiceImpl)

    @Test
    fun `createOrderListener should acknowledge message and insert document`() {
        // Arrange
        val request = CreateOrderRequest(
            bookId = "12345",
            userId = "user1",
            quantity = "2",
            orderDate = "2024-11-26"
        )
        val uuidSlot = slot<String>()
        val requestSlot = slot<CreateOrderRequest>()

        every { documentDBServiceImpl.insertDocument(capture(uuidSlot), capture(requestSlot)) } returns true
        every { acknowledgment.acknowledge() } just Runs

        // Act
        service.createOrderListener(request, acknowledgment)

        // Assert
        // Verify that the acknowledgment was called
        verify { acknowledgment.acknowledge() }

        // Verify that insertDocument was called with correct arguments
        verify { documentDBServiceImpl.insertDocument(uuidSlot.captured, requestSlot.captured) }

        // Assertions on captured arguments
        assert(uuidSlot.captured.isNotEmpty()) // Check UUID is not empty
        assert(requestSlot.captured == request) // Check CreateOrderRequest matches the input
    }
}
