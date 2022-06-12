package com.stockbit.common.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.stockbit.common.utils.NavigationCommand
import com.stockbit.common.utils.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    companion object {
        const val TAG = "ViewModel"
    }

    // FOR ERROR HANDLER
    protected val _snackbarError = MutableLiveData<Event<Int>>()
    val snackBarError: LiveData<Event<Int>> get() = _snackbarError

    // FOR NAVIGATION
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        val suppressedException = exception.suppressed

        if (suppressedException.isNotEmpty()) {
            Log.d(TAG, "Caught $exception with another suppressed exception(s):")

            suppressedException.forEach { throwable ->
                Log.d(TAG, "Also caught $throwable")
            }
        } else {
            Log.d(TAG, "Caught $exception")
        }
    }

    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
     fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }

    protected fun launchWithViewModel(launchBlock: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(exceptionHandler) { launchBlock() }
}