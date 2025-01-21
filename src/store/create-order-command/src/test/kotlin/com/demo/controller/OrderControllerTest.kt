// file: src/test/kotlin/com/demo/controller/OrderControllerTest.kt

package com.demo.controller

import com.demo.dto.CreateOrderRequest
import com.demo.dto.WebResponse
import com.demo.service.CreateOrderProducerService
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import java.util.concurrent.ExecutionException

class OrderControllerTest {

    private val createOrderProducerService: CreateOrderProducerService = mockk()
    private val createOrderTopic = "test-create-order-topic"
    private val servers = "test-servers"

    private val orderController = OrderController(createOrderProducerService, createOrderTopic, servers)

    @Test
    fun `createOrder should call sendCreateOrderEvent and return OK`() {
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
        val response = orderController.createOrder(createOrderRequest)

        // Assert
        verify { createOrderProducerService.sendCreateOrderEvent(createOrderRequest) }
        assertEquals(HttpStatus.OK, (response as ResponseEntity<*>).statusCode)
    }

    @Test
    fun `createOrder should throw ExecutionException when service fails`() {
        // Arrange
        val createOrderRequest = CreateOrderRequest(
            bookId = "123",
            userId = "user1",
            quantity = "2",
            orderDate = "2024-11-27"
        )

        every { createOrderProducerService.sendCreateOrderEvent(any()) } throws ExecutionException("Error", null)

        // Act & Assert
        assertThrows<ExecutionException> {
            orderController.createOrder(createOrderRequest)
        }
        verify { createOrderProducerService.sendCreateOrderEvent(createOrderRequest) }
    }

    @Test
    fun `getEnvInfo should return correct environment info`() {
        // Act
        val envInfo = orderController.getEnvInfo()

        // Assert
        assertEquals("$createOrderTopic - $servers", envInfo)
    }
}
