package br.com.filipeborges.poc.kafka.producerconsumersample

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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

    @Bean
    fun uppercaseReactive(): java.util.function.Function<Flux<String>, Flux<String>> {
        return java.util.function.Function { message ->
            message.doOnNext { println("======> Received text: $it") }
                    .map { if (it != "error") it.uppercase() else throw RuntimeException("runtime error") }
        }
    }
}