// file: src/main/kotlin/com/demo/controller/BlogController.kt

package com.demo.controller

import com.demo.dto.BlogResponse
import com.demo.dto.ListBlogRequest
import com.demo.dto.WebResponse
import com.demo.service.BlogService
import org.springframework.web.bind.annotation.*

@RestController
class BlogController(val blogService: BlogService) {

    @CrossOrigin(origins = ["http://localhost:9082"])
    @GetMapping(
        value = ["/v1/marketing/blogs"],
        produces = ["application/json"]
    )
    fun listBlogs(@RequestParam(value = "size", defaultValue = "10") size: Int,
                  @RequestParam(value = "page", defaultValue = "0") page: Int): WebResponse<List<BlogResponse>> {
        val request = ListBlogRequest(page = page, size = size)
        val responses = blogService.list(request)
        return WebResponse(
            code = 200,
            status = "OK",
            data = responses
        )
    }
}