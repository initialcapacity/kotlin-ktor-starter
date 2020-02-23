package io.collective.workflow

import org.slf4j.LoggerFactory

class NoopWorkFinder : WorkFinder<NoopTask> {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun findRequested(name: String): List<NoopTask> {
        logger.info("finding work.")

        return mutableListOf(NoopTask("task-name", "task-value"))
    }

    override fun markCompleted(info: NoopTask) {
        logger.info("marking work complete.")
    }
}
