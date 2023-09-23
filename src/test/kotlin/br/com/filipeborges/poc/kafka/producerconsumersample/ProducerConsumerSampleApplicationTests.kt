package br.com.filipeborges.poc.kafka.producerconsumersample

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.messaging.support.GenericMessage

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
		val myText = "error"
		val event = GenericMessage(myText)

		inputDestination.send(event, "upper-not-reactive-input")
		val received = outputDestination.receive(5000L, "upper-not-reactive-output")

		Assertions.assertNull(received)
	}

}
