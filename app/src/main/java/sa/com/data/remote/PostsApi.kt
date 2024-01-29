package sa.com.data.remote

import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path
import sa.com.data.remote.model.ApiError
import sa.com.data.remote.model.PostObject

interface PostsApi {

    @GET("posts")
    suspend fun getPosts(): ApiResult<ArrayList<PostObject>, ApiError>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id:Int): ApiResult<PostObject, ApiError>

}