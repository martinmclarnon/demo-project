// file: src/main/kotlin/com/demo/persistence/repository/AuditRepository.kt

package com.demo.persistence.repository


import com.demo.persistence.entity.Audit
import org.springframework.data.mongodb.repository.MongoRepository

interface AuditRepository : MongoRepository<Audit, String> {
}