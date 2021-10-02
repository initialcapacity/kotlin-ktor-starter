package io.collective.start.analyzer

import io.collective.workflow.Worker
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

class ExampleWorker(override val name: String = "data-analyzer") : Worker<ExampleTask> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(task: ExampleTask) {
        runBlocking {
            logger.info("doing work. {}", task.info)


            logger.info("found content. {}", task.info)


            logger.info("completed work. {}", task.info)
        }
    }
}