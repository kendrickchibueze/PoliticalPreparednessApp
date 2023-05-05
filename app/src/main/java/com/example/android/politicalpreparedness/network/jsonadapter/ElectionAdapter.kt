package com.example.android.politicalpreparedness.network.jsonadapter

import com.example.android.politicalpreparedness.network.models.Division
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*


class ElectionAdapter {

    @FromJson
    fun divisionFromJson(ocdDivisionId: String): Division {
        val countryDelimiter = "country:"
        val stateDelimiter = "state:"
        val districtDelimiter = "district:"
        val country = ocdDivisionId.substringAfter(countryDelimiter,"")
            .substringBefore("/")
        val state = getStateFromOcdDivisionId(ocdDivisionId, stateDelimiter, districtDelimiter)
        return Division(ocdDivisionId, country, state)
    }

    private fun getStateFromOcdDivisionId(ocdDivisionId: String, stateDelimiter: String, districtDelimiter: String): String {
        var state = ocdDivisionId.substringAfter(stateDelimiter,"")
            .substringBefore("/")
        if(state.isEmpty()){
            state = ocdDivisionId.substringAfter(districtDelimiter,"")
                .substringBefore("/")
        }
        return state
    }

    @ToJson
    fun divisionToJson(division: Division): String {
        return division.id
    }

    @FromJson
    fun dateFromJson(dateString: String): Date? {
        return parseDate(dateString)
    }

    private fun parseDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.parse(dateString)
    }

    @ToJson
    fun dateToJson(date: Date): String {
        return formatDate(date)
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }
}
