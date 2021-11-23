package com.vivir.bluetooth_terminal

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*

class ConnectThread(private val device: BluetoothDevice) : Thread() {
    val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    var mSocket: BluetoothSocket? = null
    lateinit var rThread: ReceiveThread

    init {
        try {
            mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (i: IOException) {

        }
    }

    override fun run() {
        try {
            Log.d("Log: ", "Connecting...")
            mSocket?.connect()
            Log.d("Log: ", "Connected")
            rThread = ReceiveThread(mSocket!!)
            rThread.start()
        } catch (i: IOException) {
            Log.d("Log: ", "Can not connect to device")
            closeConnect()
        }
    }

    fun closeConnect() {
        try {
            mSocket?.close()
        } catch (i: IOException) {

        }
    }
}