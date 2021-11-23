package com.vivir.bluetooth_terminal

import android.bluetooth.BluetoothAdapter

class BluetoothConnect(private val adapter: BluetoothAdapter) {
    lateinit var connectThread: ConnectThread
    fun connect(mac: String) {
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice(mac)
            device.let {
                connectThread = ConnectThread(it)
                connectThread.start()
            }
        }
    }

    fun sendMessage(message: String) {
        connectThread.rThread.sendMessage(message.toByteArray())
    }
}