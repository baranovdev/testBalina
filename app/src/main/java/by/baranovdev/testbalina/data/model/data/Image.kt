package by.baranovdev.testbalina.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.baranovdev.testbalina.utils.DatabaseConst

@Entity(tableName = DatabaseConst.IMAGE_TABLE_NAME)
data class Image(
    @ColumnInfo(name = DatabaseConst.IMAGE_DATE_COLUMN_NAME)
    val date: Int? = null,

    @ColumnInfo(name = DatabaseConst.IMAGE_LNG_COLUMN_NAME)
    val lng: Double? = null,

    @PrimaryKey
    @ColumnInfo(name = DatabaseConst.IMAGE_ID_COLUMN_NAME)
    val id: Int? = null,

    @ColumnInfo(name = DatabaseConst.IMAGE_URL_COLUMN_NAME)
    val url: String? = null,

    @ColumnInfo(name = DatabaseConst.IMAGE_LAT_COLUMN_NAME)
    val lat: Double? = null
): UiModel()