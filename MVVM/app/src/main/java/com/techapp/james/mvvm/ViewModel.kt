package com.techapp.james.mvvm

import android.databinding.BaseObservable
import android.databinding.Bindable

class ViewModel : BaseObservable() {
    @Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
}