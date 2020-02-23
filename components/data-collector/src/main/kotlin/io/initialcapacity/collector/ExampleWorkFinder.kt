package io.initialcapacity.collector

import io.initialcapacity.workflow.WorkFinder
import org.slf4j.LoggerFactory

class ExampleWorkFinder : WorkFinder<ExampleTask> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun findRequested(name: String): List<ExampleTask> {
        logger.info("finding work.")

        val work = ExampleTask("some info")

        return mutableListOf(work)
    }

    override fun markCompleted(info: ExampleTask) {
        logger.info("marking work complete.")
    }
}