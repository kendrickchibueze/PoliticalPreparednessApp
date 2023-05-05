package com.example.android.politicalpreparedness.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert
    suspend fun insert(election: Election)

    @Query("SELECT * FROM election_table")
    suspend fun getAll() : List<Election>

    @Query("SELECT * from election_table WHERE id = :id")
    suspend fun getById(id: Int) : Election?

    @Delete
    suspend fun delete(election: Election)

    @Query("DELETE  FROM election_table")
    suspend fun clear()

}