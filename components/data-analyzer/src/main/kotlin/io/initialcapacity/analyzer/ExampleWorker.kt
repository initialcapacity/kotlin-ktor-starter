package io.initialcapacity.analyzer

import io.initialcapacity.workflow.Worker
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

class ExampleWorker(override val name: String = "data-analyzer") : Worker<ExampleTask> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(task: ExampleTask) {
        runBlocking {
            logger.info("starting data analysis.")

            // todo - data analysis happens here

            logger.info("completed data analysis.")
        }
    }
}