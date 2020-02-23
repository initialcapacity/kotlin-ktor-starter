package io.collective.workflow

interface WorkFinder<T> {
    fun findRequested(name: String): List<T>

    fun markCompleted(info: T)
}