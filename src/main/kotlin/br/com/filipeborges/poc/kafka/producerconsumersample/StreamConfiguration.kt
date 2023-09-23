package br.com.filipeborges.poc.kafka.producerconsumersample

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StreamConfiguration {
    @Bean
    fun uppercaseNotReactive(): java.util.function.Function<String, String> {
        return java.util.function.Function {
            println("======> Received text: $it")
            if (it == "error") throw RuntimeException("runtime error")
            it.uppercase()
        }
    }
}