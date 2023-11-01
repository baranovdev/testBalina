package by.baranovdev.testbalina.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.baranovdev.testbalina.utils.DatabaseConst

@Entity(tableName = DatabaseConst.USER_TABLE_NAME)
data class User(

    @PrimaryKey
    @ColumnInfo(name = DatabaseConst.USER_ID_COLUMN_NAME)
    val userId: Int? = null,

    @ColumnInfo(name = DatabaseConst.USER_LOGIN_COLUMN_NAME)
    val login: String? = null,

    @ColumnInfo(name = DatabaseConst.USER_TOKEN_COLUMN_NAME)
    val token: String? = null

): UiModel()