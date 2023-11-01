package by.baranovdev.testbalina.utils

object DatabaseConst {
        //Tables

    const val USER_TABLE_NAME = "user_table"
    const val IMAGE_TABLE_NAME = "image_table_name"
    const val COMMENT_TABLE_NAME = "comment_table"

        //Columns

    //User
    const val USER_LOGIN_COLUMN_NAME = "user_login"
    const val USER_ID_COLUMN_NAME = "user_id"
    const val USER_TOKEN_COLUMN_NAME = "user_token"

    //Image
    const val IMAGE_DATE_COLUMN_NAME = "image_date"
    const val IMAGE_ID_COLUMN_NAME = "image_id"
    const val IMAGE_URL_COLUMN_NAME = "image_url"
    const val IMAGE_LAT_COLUMN_NAME = "image_lat"
    const val IMAGE_LNG_COLUMN_NAME = "image_lng"

    //Comment
    const val COMMENT_DATE_COLUMN_NAME = "comment_date"
    const val COMMENT_IMAGE_ID_COLUMN_NAME = "comment_image_id"
    const val COMMENT_ID_COLUMN_NAME = "comment_id"
    const val COMMENT_TEXT_COLUMN_NAME = "comment_text"
}