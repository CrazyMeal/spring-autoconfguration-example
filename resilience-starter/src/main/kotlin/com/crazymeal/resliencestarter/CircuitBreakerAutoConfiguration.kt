package com.crazymeal.resliencestarter

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.context.annotation.Bean

@AutoConfiguration
@AutoConfigureAfter(CircuitBreakerRegistryAutoConfiguration::class)
class CircuitBreakerAutoConfiguration(
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {

    private var logger = LogManager.getLogger(CircuitBreakerAutoConfiguration::class.java)

    @Bean
    fun createCircuitBreaker(): CircuitBreaker {
        logger.info("Creating circuit breaker")
        return circuitBreakerRegistry.circuitBreaker("myCicruitBreaker", "customConfiguration")
    }
}