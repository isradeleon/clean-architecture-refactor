package com.techyourchance.architecture.common.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.techyourchance.architecture.common.database.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteQuestionDao {

    @Upsert
    suspend fun upsert(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun observe(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun observeById(id: String): Flow<FavoriteEntity?>

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun delete(id: String)


}