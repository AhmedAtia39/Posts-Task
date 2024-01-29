package sa.com.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import sa.com.data.local.posts.PostDAO
import sa.com.data.local.posts.PostEntity

@Database(entities = [ PostEntity::class ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDAO(): PostDAO
}