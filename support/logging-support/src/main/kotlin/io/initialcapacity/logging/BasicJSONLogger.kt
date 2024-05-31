package io.initialcapacity.logging

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Marker
import org.slf4j.event.Level
import org.slf4j.helpers.LegacyAbstractLogger
import org.slf4j.helpers.MessageFormatter
import org.slf4j.spi.LocationAwareLogger
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class BasicJSONLogger(val desiredName: String) : LegacyAbstractLogger() {
    init {
        this.name = desiredName
    }

    override fun isTraceEnabled(): Boolean {
        return isLevelEnabled(LocationAwareLogger.TRACE_INT);
    }

    override fun isDebugEnabled(): Boolean {
        return isLevelEnabled(LocationAwareLogger.DEBUG_INT);
    }

    override fun isInfoEnabled(): Boolean {
        return isLevelEnabled(LocationAwareLogger.INFO_INT);
    }

    override fun isWarnEnabled(): Boolean {
        return isLevelEnabled(LocationAwareLogger.WARN_INT);
    }

    override fun isErrorEnabled(): Boolean {
        return isLevelEnabled(LocationAwareLogger.ERROR_INT);
    }

    override fun getFullyQualifiedCallerName(): String? {
        return null
    }

    @Serializable
    data class Entry(
        val time: String,
        val threadName: String,
        val severity: String,
        val loggerName: String,
        val message: String
    )

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")

    @Synchronized
    override fun handleNormalizedLoggingCall(
        level: Level?,
        marker: Marker?,
        messagePattern: String?,
        arguments: Array<out Any>?,
        throwable: Throwable?
    ) {

        val time = Instant.now().atZone(ZoneId.of("UTC")).format(formatter)
        val threadName = Thread.currentThread().name
        val severity = renderLevel(level!!.toInt())
        val loggerName = name
        val message = MessageFormatter.basicArrayFormat(messagePattern, arguments)

        // %r [%t] %level %logger - %m%n
        // val builder = StringBuilder(32)
        // builder.append(time);
        // builder.append(" ")
        // builder.append("[")
        // builder.append(threadName)
        // builder.append("] ")
        // builder.append(levelString)
        // builder.append(" ")
        // builder.append(loggetName).append(" - ");
        // builder.append(message);
        // val entry = builder.toString()

        val entry = Entry(time, threadName, severity, loggerName, message)
        val encodeToString = Json.encodeToString(entry)

        System.err.println(encodeToString)
        throwable?.printStackTrace()
        System.err.flush()
    }

    private fun isLevelEnabled(level: Int): Boolean {
        return (level >= LocationAwareLogger.INFO_INT)
    }

    private fun renderLevel(level: Int): String {
        when (level) {
            LocationAwareLogger.TRACE_INT -> return "TRACE"
            LocationAwareLogger.DEBUG_INT -> return "DEBUG"
            LocationAwareLogger.INFO_INT -> return "INFO"
            LocationAwareLogger.WARN_INT -> return "WARN"
            LocationAwareLogger.ERROR_INT -> return "ERROR"
        }
        throw IllegalStateException("Unrecognized level [$level]")
    }

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
}
