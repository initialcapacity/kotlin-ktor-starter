package io.initialcapacity.logging

import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.helpers.NOPMDCAdapter
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider


class BasicJSONServiceProvider : SLF4JServiceProvider {
    var REQUESTED_API_VERSION: String = "2.0.99"

    private var loggerFactory = BasicJSONLoggerFactory()
    private var markerFactory = BasicMarkerFactory()
    private var mdcAdapter = NOPMDCAdapter()

    override fun getLoggerFactory(): ILoggerFactory {
        return loggerFactory
    }

    override fun getMarkerFactory(): IMarkerFactory {
        return markerFactory
    }

    override fun getMDCAdapter(): MDCAdapter {
        return mdcAdapter
    }

    override fun getRequestedApiVersion(): String {
        return REQUESTED_API_VERSION
    }

    override fun initialize() {
    }
}