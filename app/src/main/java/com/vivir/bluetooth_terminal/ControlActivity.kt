package com.vivir.bluetooth_terminal

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.vivir.bluetooth_terminal.databinding.ActivityControlBinding

class ControlActivity : AppCompatActivity() {
    private lateinit var binding: ActivityControlBinding
    private lateinit var actListLauncher: ActivityResultLauncher<Intent>
    lateinit var bluetoothConnect: BluetoothConnect
    private var listItem: ListItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        setContentView(binding.root)
        onBluetoothListResult()
        init()
        binding.apply {
            bA.setOnClickListener {
                bluetoothConnect.sendMessage("A")
            }
            bB.setOnClickListener {
                bluetoothConnect.sendMessage("B")
            }
        }
    }

    private fun init() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        bluetoothConnect = BluetoothConnect(bluetoothAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.control_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.list_item) {
            actListLauncher.launch(Intent(this, BluetoothListActivity::class.java))

        } else if (item.itemId == R.id.id_connect) {
            listItem.let {
                bluetoothConnect.connect(it?.mac!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onBluetoothListResult() {
        actListLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    listItem =
                        it.data?.getSerializableExtra(BluetoothListActivity.DEVICE_KEY) as ListItem

                }
            }
    }

}