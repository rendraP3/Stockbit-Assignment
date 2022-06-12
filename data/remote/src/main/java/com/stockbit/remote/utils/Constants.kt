package com.stockbit.remote.utils

import com.stockbit.remote.BuildConfig

object Constants {

    const val AUTHORIZATION = "Authorization"
    const val API_KEY = "ApiKey ${BuildConfig.API_KEY}"
    const val TIMEOUT = 6000L
    const val BACKOFF_DURATION_BASE = 1000L
    const val BACKOFF_DURATION_MAX = 1000L

}