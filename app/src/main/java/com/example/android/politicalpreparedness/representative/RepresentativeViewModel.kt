package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch


class RepresentativeViewModel : ViewModel() {

    private var _representativesResponse = MutableLiveData<RepresentativeResponse>()
    val representativeResponse : LiveData<RepresentativeResponse>
        get() = _representativesResponse

    private var _representatives = MutableLiveData<List<Representative>>()
    val representatives : LiveData<List<Representative>>
        get() = _representatives


    private var _addressOfUser = MutableLiveData<Address>()
    val addressOfUser : LiveData<Address>
        get() = _addressOfUser



    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getRepresentativeResponse() {
        viewModelScope.launch {
            try {
                val response =
                    CivicsApi.retrofitService.getRepresentatives(_addressOfUser.value!!.toFormattedString())
                _representativesResponse.value = response

                val offices = _representativesResponse.value!!.offices
                val officials = _representativesResponse.value!!.officials
                _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to retrieve representatives: ${e.message}"
            }
        }
    }

    fun setAddressOfUser(address: Address){
        _addressOfUser.value = address
    }


    fun setRepresentatives(list : List<Representative>){
        _representatives.value = list
    }


}