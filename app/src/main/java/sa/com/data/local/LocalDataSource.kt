package sa.com.data.local

import sa.com.data.local.posts.PostEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val appDB: AppDatabase
) {
    suspend fun savePosts(posts: List<PostEntity>) {
        posts.forEach { appDB.postDAO().insert(it) }
    }

    suspend fun getPosts(): List<PostEntity> {
        return appDB.postDAO().getAll().map { it }
    }

}