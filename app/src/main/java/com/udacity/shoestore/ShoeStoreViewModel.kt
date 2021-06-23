package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class ShoeStoreViewModel: ViewModel() {
    private val _shoeList: MutableLiveData<MutableList<Shoe>> by lazy {
        MutableLiveData<MutableList<Shoe>>()
    }

    init {
        _shoeList.value = mutableListOf(
            Shoe("Nike Cross Trainer", 9.5, "Nike", "Hike show"),
            Shoe("Reebok Pump Omni", 8.0, "Reebok", "Basketball shoe"),
            Shoe("Puma Match Star", 7.5, "Puma", "Sneakers"),
            Shoe("Adidas Swift Run", 10.0, "Adidas", "Running shoe")
        )
    }

    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    val shoeName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val shoeCompany: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val shoeSize: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val shoeDesc: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _shoeAddedEvent: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val shoeAddedEvent: LiveData<Boolean>
        get() = _shoeAddedEvent

    fun onShoeAddedComplete() {
        _shoeAddedEvent.value = false
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun onSaveShoe() {
        // Add a new shoe to the list
        val shoe = Shoe(shoeName.value, shoeSize.value?.toDouble(), shoeCompany.value, shoeDesc.value)
        _shoeList.value?.add(shoe)
        _shoeList.notifyObserver()
        clearFields()
        _shoeAddedEvent.value = true
    }

    private fun clearFields() {
        shoeName.value = null
        shoeSize.value = null
        shoeCompany.value = null
        shoeDesc.value = null
    }
}
