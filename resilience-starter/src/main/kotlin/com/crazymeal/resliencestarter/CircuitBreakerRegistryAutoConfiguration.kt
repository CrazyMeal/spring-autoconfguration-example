package com.crazymeal.resliencestarter

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.annotation.Order
import java.time.Duration

@AutoConfiguration
class CircuitBreakerRegistryAutoConfiguration {

    @Order(1)
    @Configuration
    @PropertySource("classpath:circuit.properties")
    @EnableConfigurationProperties(CircuitBreakerProperties::class)
    class PropertiesEnablerConfiguration

    @Order(2)
    @Configuration
    class CircuitBreakerRegistryConfiguration {
        private var logger = LogManager.getLogger(CircuitBreakerRegistryConfiguration::class.java)

        @Bean
        @ConditionalOnMissingBean(CircuitBreakerRegistry::class)
        fun circuitBreakerRegistry(): CircuitBreakerRegistry {
            logger.info("Creating custom circuit breaker registry")
            return CircuitBreakerRegistry.custom().build()
        }
    }

    @Order(3)
    @Configuration
    class CustomCircuitBreakerRegistryConfiguration(
        @Autowired private val circuitBreakerRegistry: CircuitBreakerRegistry,
        @Autowired private val circuitBreakerProperties: CircuitBreakerProperties
    ) {
        private var logger = LogManager.getLogger(CustomCircuitBreakerRegistryConfiguration::class.java)

        init {
            addCustomCircuitBreakerConfig(circuitBreakerRegistry)
        }

        private fun addCustomCircuitBreakerConfig(circuitBreakerRegistry: CircuitBreakerRegistry) {
            logger.info("Creating custom circuit breaker config")
            val config = CircuitBreakerConfig.custom()
                .minimumNumberOfCalls(circuitBreakerProperties.default.calls)
                .slowCallDurationThreshold(Duration.ofSeconds(circuitBreakerProperties.default.slowduration))
                .build()

            circuitBreakerRegistry.addConfiguration("customConfiguration", config)
        }
    }
}