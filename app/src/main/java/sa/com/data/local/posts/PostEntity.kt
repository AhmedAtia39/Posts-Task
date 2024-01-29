package sa.com.data.local.posts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@kotlinx.serialization.Serializable
@Entity(tableName = "posts", indices = [Index(value = ["id"], unique = true)])
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "post_id")
    val post_id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String,
):java.io.Serializable{
    constructor(post_id: Int,title: String, body: String) : this(0,post_id, title,body)
}