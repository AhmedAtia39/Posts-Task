package sa.com.data.remote

import com.slack.eithernet.ApiResult
import sa.com.data.remote.model.ApiError
import sa.com.data.remote.model.PostObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val api: PostsApi,
) {
    suspend fun getPosts(): ApiResult<ArrayList<PostObject>, ApiError> {
        return api.getPosts()
    }

    suspend fun getPost(id:Int): ApiResult<PostObject, ApiError> {
        return api.getPost(id)
    }
}