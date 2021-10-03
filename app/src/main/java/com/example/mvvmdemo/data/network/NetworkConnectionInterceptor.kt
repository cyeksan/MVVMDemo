package com.example.mvvmdemo.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.mvvmdemo.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isInternetAvailable()) {
            throw NoInternetException("Make sure you have an active connection")
        }

        // If network is available, chain will proceed and response will be returned
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable() : Boolean {

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }

    }
}