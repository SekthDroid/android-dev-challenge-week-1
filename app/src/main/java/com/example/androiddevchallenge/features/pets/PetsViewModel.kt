package com.example.androiddevchallenge.features.pets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.Pet
import com.example.androiddevchallenge.obtainPets

class PetsViewModel(private val getPetsUseCase: GetPetsUseCase = GetPetsUseCase()) : ViewModel() {
    private val _pets = MutableLiveData(obtainPets())
    val pets: LiveData<List<Pet>> = _pets

    fun filter(criteria: String = "") {
        _pets.value = getPetsUseCase(criteria)
    }
}