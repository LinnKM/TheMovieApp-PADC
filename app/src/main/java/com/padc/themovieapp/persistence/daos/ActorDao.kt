package com.padc.themovieapp.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.themovieapp.data.vos.ActorVO

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActors(actors: List<ActorVO>)

    @Query("SELECT * FROM actors")
    fun getAllActors(): LiveData<List<ActorVO>>
}