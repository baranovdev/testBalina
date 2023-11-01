package by.baranovdev.testbalina.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.baranovdev.testbalina.utils.DatabaseConst

@Entity(tableName = DatabaseConst.COMMENT_TABLE_NAME)
data class Comment(
    @ColumnInfo(name = DatabaseConst.COMMENT_IMAGE_ID_COLUMN_NAME)
    val imageId: Int? = null,

    @ColumnInfo(name = DatabaseConst.COMMENT_DATE_COLUMN_NAME)
    val date: Int? = null,

    @PrimaryKey
    @ColumnInfo(name = DatabaseConst.COMMENT_ID_COLUMN_NAME)
    val id: Int? = null,

    @ColumnInfo(name = DatabaseConst.COMMENT_TEXT_COLUMN_NAME)
    val text: String? = null
): UiModel()