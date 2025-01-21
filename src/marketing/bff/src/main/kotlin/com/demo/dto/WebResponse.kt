// file: src/main/kotlin/com/demo/dto/WebResponse.kt

package com.demo.dto

data class WebResponse<T>(

    val code: Int,

    val status: String,

    val data: T
)
