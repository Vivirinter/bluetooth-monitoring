package com.vivir.bluetooth_terminal

import java.io.Serializable

data class ListItem(
    var name: String,
    var mac: String
): Serializable