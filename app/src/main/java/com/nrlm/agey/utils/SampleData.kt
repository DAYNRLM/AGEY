package com.nrlm.agey.utils

import androidx.lifecycle.MutableLiveData

 class SampleData {

    companion object{
        var defaultKilometer = MutableLiveData<Long>(20L)
        var openingKm = MutableLiveData<String>("")

        var notificationCount  = MutableLiveData<Int>(0)

    }
}