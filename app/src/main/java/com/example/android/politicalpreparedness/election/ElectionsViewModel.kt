package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch


class ElectionsViewModel(private val dataSource: ElectionDao) : ViewModel() {

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>> = _upcomingElections

    private val _savedElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>> = _savedElections

    init {
        loadData()
    }

    private fun loadData() {
        getUpcomingElectionsFromNetwork()
        getSavedElectionsFromDatabase()
    }

    private fun getUpcomingElectionsFromNetwork() {
        viewModelScope.launch {
            val electionResponse = CivicsApi.retrofitService.getElectionResponse()
            _upcomingElections.value = electionResponse.elections
        }
    }

    fun getSavedElectionsFromDatabase() {
        viewModelScope.launch {
            _savedElections.value = dataSource.getAll()
        }
    }
}
