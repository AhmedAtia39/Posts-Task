package sa.com.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostObject(
    @SerialName("body")
    var body: String,
    @SerialName("id")
    var id: Int,
    @SerialName("title")
    var title: String,
    @SerialName("userId")
    var userId: Int
):java.io.Serializable