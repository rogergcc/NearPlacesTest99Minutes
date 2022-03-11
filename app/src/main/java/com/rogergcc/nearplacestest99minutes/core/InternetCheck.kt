package com.rogergcc.nearplacestest99minutes.core

import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.Socket

object InternetCheck {

    suspend fun isNetworkAvailable() = coroutineScope {
            return@coroutineScope try {
                val socket = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)
                socket.connect(socketAddress)
                socket.close()
                true
            } catch (e: Exception) {
                false
            }
    }
}