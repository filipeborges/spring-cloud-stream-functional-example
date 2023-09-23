package br.com.filipeborges.poc.kafka.producerconsumersample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProducerConsumerSampleApplication

fun main(args: Array<String>) {
	runApplication<ProducerConsumerSampleApplication>(*args)
}
