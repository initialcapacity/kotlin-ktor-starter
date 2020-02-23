package io.initialcapacity.collector

import io.initialcapacity.workflow.Worker
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

class ExampleWorker(override val name: String = "data-collector") : Worker<ExampleTask> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(task: ExampleTask) {
        runBlocking {
            logger.info("starting data collection.")

            // todo - data collection happens here

            logger.info("completed data collection.")
        }
    }
}