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

    private val _representativesResponse = MutableLiveData<RepresentativeResponse>()
    val representativeResponse: LiveData<RepresentativeResponse> = _representativesResponse

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>> = _representatives

    private val _addressOfUser = MutableLiveData<Address>()
    val addressOfUser: LiveData<Address> = _addressOfUser

    fun setAddressOfUser(address: Address) {
        _addressOfUser.value = address
    }

    fun setRepresentatives(list: List<Representative>) {
        _representatives.value = list
    }

    fun getRepresentativeResponse() {
        viewModelScope.launch {
            val response = getCivicApiRepresentatives()
            _representativesResponse.value = response
            setRepresentativesFromResponse()
        }
    }

    private suspend fun getCivicApiRepresentatives(): RepresentativeResponse {
        return CivicsApi.retrofitService.getRepresentatives(_addressOfUser.value!!.toFormattedString())
    }

    private fun setRepresentativesFromResponse() {
        val offices = _representativesResponse.value!!.offices
        val officials = _representativesResponse.value!!.officials
        _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
    }
}
