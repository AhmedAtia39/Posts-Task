package sa.com.data

import com.slack.eithernet.ApiResult
import sa.com.data.local.LocalDataSource
import sa.com.data.local.posts.PostEntity
import sa.com.data.remote.RemoteDataSource
import sa.com.data.remote.model.ApiError
import sa.com.data.remote.model.PostObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepo @Inject constructor(
    private val remoteDS: RemoteDataSource,
    private val localDS: LocalDataSource,
) {

    suspend fun getLocalPosts(): List<PostEntity> {
        val list = localDS.getPosts()
       return  list
    }

    suspend fun savePosts(posts: List<PostEntity>) {
        localDS.savePosts(posts)
    }

    suspend fun getRemotePosts(): ApiResult<ArrayList<PostObject>, ApiError> {
        return  remoteDS.getPosts()
    }

    suspend fun getRemotePost(id:Int): ApiResult<PostObject, ApiError> {
        return  remoteDS.getPost(id)
    }
}