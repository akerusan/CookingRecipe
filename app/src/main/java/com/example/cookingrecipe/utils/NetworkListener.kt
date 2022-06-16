package com.example.cookingrecipe.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkListener: ConnectivityManager.NetworkCallback() {

    private val hasConnection =  MutableStateFlow(false)

    fun checkConnection(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // TODO: Cannot be use with SDK23 and under (search alternative)
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false

        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                    return@forEach
                }
            }
        }
        hasConnection.value = isConnected
        return hasConnection
    }

    override fun onAvailable(network: Network) {
        hasConnection.value = true
    }

    override fun onLost(network: Network) {
        hasConnection.value = false
    }
}