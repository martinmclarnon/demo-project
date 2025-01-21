// file: src/test/kotlin/com/demo/utility/KafkaConsumerConfigTest.kt

package com.demo.utility

import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.kafka.support.serializer.JsonDeserializer

class KafkaConsumerConfigTest {

    private val config = KafkaConsumerConfig("localhost:9092", "test-group")

    @Test
    fun `createOrderConsumerFactory should return a valid consumer factory`() {
        // Act
        val consumerFactory = config.createOrderConsumerFactory()

        // Assert
        assertNotNull(consumerFactory)
    }

    @Test
    fun `createOrderKafkaListenerContainerFactory should return a valid container factory`() {
        // Act
        val containerFactory = config.createOrderKafkaListenerContainerFactory()

        // Assert
        assertNotNull(containerFactory)
        assert(containerFactory.consumerFactory.keyDeserializer is StringDeserializer)
        assert(containerFactory.consumerFactory.valueDeserializer is JsonDeserializer<*>)
    }
}