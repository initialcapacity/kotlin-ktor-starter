package test.initialcapacity.logging

import org.slf4j.LoggerFactory
import kotlin.test.Test

class BasicJSONServiceProviderTest {

    @Test
    fun test1() {
        val logger = LoggerFactory.getLogger("test1")
        logger.info("Hello world.")
    }
}