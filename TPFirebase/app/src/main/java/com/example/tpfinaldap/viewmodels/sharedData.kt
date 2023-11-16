package com.example.tpfinaldap.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class sharedData : ViewModel() {
    val dataID = MutableLiveData<String>()
}