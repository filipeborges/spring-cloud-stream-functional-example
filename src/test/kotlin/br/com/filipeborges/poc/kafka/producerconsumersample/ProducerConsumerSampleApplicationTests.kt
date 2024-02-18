package br.com.filipeborges.poc.kafka.producerconsumersample

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.messaging.support.GenericMessage

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest
class ProducerConsumerSampleApplicationTests {

	@Autowired
	private lateinit var inputDestination: InputDestination

	@Autowired
	private lateinit var outputDestination: OutputDestination

	@Test
	fun notReactiveTransformation() {
		val myText = "my-text"
		val event = GenericMessage(myText)

		inputDestination.send(event, "upper-not-reactive-input")
		val received = outputDestination.receive(2000L, "upper-not-reactive-output")

		Assertions.assertNotNull(received)

		val transformedText = String(received.payload)

		Assertions.assertNotNull(transformedText)
		Assertions.assertEquals("MY-TEXT", transformedText)
	}

	@Test
	fun notReactiveTransformationError() {
		// Force error on first message
		var myText = "error"
		var event = GenericMessage(myText)

		inputDestination.send(event, "upper-not-reactive-input")
		var received = outputDestination.receive(5000L, "upper-not-reactive-output")

		Assertions.assertNull(received)

		// Check that second message will be consumed be imperative consumer
		myText = "my-text"
		event = GenericMessage(myText)

		inputDestination.send(event, "upper-not-reactive-input")
		received = outputDestination.receive(2000L, "upper-not-reactive-output")

		Assertions.assertNotNull(received)

		var transformedText = String(received.payload)

		Assertions.assertNotNull(transformedText)
		Assertions.assertEquals("MY-TEXT", transformedText)
	}

	@Order(1)
	@Test
	fun reactiveTransformation() {
		val myText = "my-text"
		val event = GenericMessage(myText)

		inputDestination.send(event, "upper-reactive-input")
		val received = outputDestination.receive(2000L, "upper-reactive-output")

		Assertions.assertNotNull(received)

		val transformedText = String(received.payload)

		Assertions.assertNotNull(transformedText)
		Assertions.assertEquals("MY-TEXT", transformedText)
	}

	@Order(2)
	@Test
	fun reactiveTransformationError() {
		// Force error on first message
		var myText = "error"
		var event = GenericMessage(myText)

		inputDestination.send(event, "upper-reactive-input")
		var received = outputDestination.receive(5000L, "upper-reactive-output")

		Assertions.assertNull(received)

		// On reactive model, the Flux pipeline stops to receive messages - error signal end the Flux (reactive streams spec)
		myText = "my-text-2"
		event = GenericMessage(myText)

		inputDestination.send(event, "upper-reactive-input")
		received = outputDestination.receive(2000L, "upper-reactive-output")

		Assertions.assertNull(received)
	}
}
