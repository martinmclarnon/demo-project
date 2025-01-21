// file: src/main/kotlin/com/demo/controller/OrderController.kt

package com.demo.controller


import com.demo.dto.CreateOrderRequest
import com.demo.service.CreateOrderProducerService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ExecutionException

@RequestMapping("/v1/order")
@RestController
class OrderController(
    private val createOrderProducerService: CreateOrderProducerService,
    @Value("\${kafka.order.topic.create-order}") private val createOrderTopic: String,
    @Value("\${kafka.order.bootstrap-servers}") private val servers: String
) {

    @CrossOrigin(origins = ["http://localhost:10082"])
    @PostMapping
    @Throws(ExecutionException::class, InterruptedException::class)
    fun createOrder(@RequestBody createOrderRequest: CreateOrderRequest): ResponseEntity<*> {
        log.info("{}", createOrderRequest)
        createOrderProducerService.sendCreateOrderEvent(createOrderRequest)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @GetMapping("/env")
    fun getEnvInfo(): String {
        return "$createOrderTopic - $servers"
    }

    companion object {
        private val log = LoggerFactory.getLogger(OrderController::class.java)
    }
}
