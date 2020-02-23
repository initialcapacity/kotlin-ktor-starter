package io.collective.workflow

interface Worker<T> {
    val name: String
    fun execute(task: T)
}