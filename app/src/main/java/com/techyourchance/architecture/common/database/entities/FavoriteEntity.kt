package com.techyourchance.architecture.common.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techyourchance.architecture.domain.model.question.Question

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
)

fun FavoriteEntity.toQuestionModel(): Question {
    return Question(
        id = id,
        title = title
    )
}