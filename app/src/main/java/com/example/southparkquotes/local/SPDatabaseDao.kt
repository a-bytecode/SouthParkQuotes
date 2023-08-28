package com.example.southparkquotes.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.southparkquotes.model.Character

@Dao
interface SPDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characterEntities: List<Character>)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Query("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='characters'")
    suspend fun resetIdSequence()

    @Update
    suspend fun update(quotes: List<Character>)

    @Query("SELECT * FROM characters")
    fun getAll(): LiveData<List<Character>>

}