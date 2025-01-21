// file: src/test/kotlin/com/demo/service/BlogServiceImpl.kt

package com.demo.service

import com.demo.dto.BlogResponse
import com.demo.dto.ListBlogRequest

interface BlogService {

    fun list(listBlogRequest: ListBlogRequest): List<BlogResponse>
}