/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Inspired by code from the Conductor project available at https://github.com/Netflix/conductor

package io.initialcapacity.workflow

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