package com.crazymeal.resliencestarter

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "resilience")
data class CircuitBreakerProperties(
    val default: CustomProperties
)

data class CustomProperties(
    val calls: Int,
    val slowduration: Long
)
