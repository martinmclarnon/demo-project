// file: src/main/kotlin/com/demo/service/DocumentDBService.kt

package com.demo.service

import com.demo.dto.CreateOrderRequest

interface DocumentDBService {
    fun insertDocument(uuid: String, payload: CreateOrderRequest): Boolean
}
