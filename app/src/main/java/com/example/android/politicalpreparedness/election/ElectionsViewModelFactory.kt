package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.database.ElectionDao


class ElectionsViewModelFactory(private val electionDao: ElectionDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectionsViewModel::class.java)) {
            return createElectionsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    private fun createElectionsViewModel(): ElectionsViewModel {
        val dataSource = electionDao
        return ElectionsViewModel(dataSource)
    }
}

