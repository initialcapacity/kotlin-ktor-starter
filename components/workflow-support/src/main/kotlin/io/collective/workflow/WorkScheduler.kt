package io.collective.workflow

import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class WorkScheduler<T>(private val finder: WorkFinder<T>, private val workers: MutableList<Worker<T>>, private val delay: Long = 10L) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val pool = Executors.newScheduledThreadPool(workers.size)
    private val service = Executors.newFixedThreadPool(10)

    fun start() {
        workers.forEach { worker ->
            logger.info("scheduling worker {}", worker.name)

            pool.scheduleWithFixedDelay(checkForWork(worker), 0, delay, TimeUnit.SECONDS)
        }
    }

    fun shutdown() {
        service.shutdown()
        pool.shutdown()
    }

    private fun checkForWork(worker: Worker<T>): () -> Unit {
        return {
            logger.debug("checking for work for {}", worker.name)

            finder.findRequested(worker.name).forEach {

                logger.info("found work for {}", worker.name)

                service.submit {
                    try {
                        worker.execute(it)

                        finder.markCompleted(it)

                        logger.info("completed work.")

                    } catch (e: Throwable) {
                        logger.error("unable to complete work", e)
                    }
                }
            }
            logger.debug("done checking for work for {}", worker.name)
        }
    }
}