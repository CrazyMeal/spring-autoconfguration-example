package com.crazymeal.resilience4j.configuration

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GlobalConfiguration {
    private var logger = LogManager.getLogger(GlobalConfiguration::class.java)


    // Comment or Uncomment to check AutoConfiguration variants
    @Bean
    fun defaultCircuitBreakerRegistry(): CircuitBreakerRegistry {
        logger.info("Creating default circuit breaker registry")
        return CircuitBreakerRegistry.ofDefaults()
    }
}