package com.example.southparkquotes.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.southparkquotes.model.Character

@Dao
interface SPDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characterEntities: List<Character>)

    @Delete
    suspend fun delete(quotes: List<Character>)

    @Update
    suspend fun update(quotes: List<Character>)

    @Query("SELECT * FROM Character")
    fun getAll(): LiveData<List<Character>>

}