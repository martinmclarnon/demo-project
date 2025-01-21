// file: src/test/kotlin/com/demo/service/CreateOrderProducerServiceTest.kt

package com.demo.service

import com.demo.dto.CreateOrderRequest
import com.demo.dto.WebResponse
import io.mockk.*
import org.junit.jupiter.api.Test
import java.util.concurrent.ExecutionException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateOrderProducerServiceTest {

    private val createOrderProducerService: CreateOrderProducerService = mockk()

    @Test
    fun `sendCreateOrderEvent should return a successful WebResponse`() {
        // Arrange
        val createOrderRequest = CreateOrderRequest(
            bookId = "123",
            userId = "user1",
            quantity = "2",
            orderDate = "2024-11-27"
        )
        val mockResponse = WebResponse(orderId = "O-12345", status = "SUCCESS")

        every { createOrderProducerService.sendCreateOrderEvent(createOrderRequest) } returns mockResponse

        // Act
        val response = createOrderProducerService.sendCreateOrderEvent(createOrderRequest)

        // Assert
        assertEquals("O-12345", response.orderId)
        assertEquals("SUCCESS", response.status)
        verify { createOrderProducerService.sendCreateOrderEvent(createOrderRequest) }
    }

    @Test
    fun `sendCreateOrderEvent should throw ExecutionException when an error occurs`() {
        // Arrange
        val createOrderRequest = CreateOrderRequest(
            bookId = "123",
            userId = "user1",
            quantity = "2",
            orderDate = "2024-11-27"
        )

        every { createOrderProducerService.sendCreateOrderEvent(createOrderRequest) } throws ExecutionException("Kafka Error", null)

        // Act & Assert
        val exception = assertFailsWith<ExecutionException> {
            createOrderProducerService.sendCreateOrderEvent(createOrderRequest)
        }
        assertEquals("Kafka Error", exception.message)
        verify { createOrderProducerService.sendCreateOrderEvent(createOrderRequest) }
    }
}
