package sa.com.data.local.posts

import androidx.room.*

@Dao
interface PostDAO {

    @Query("SELECT * FROM `posts`")
    suspend fun getAll(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)
}