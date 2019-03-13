package android.sample.nbateamviewer.database

import android.content.Context
import android.sample.nbateamviewer.database.dao.PlayerDao
import android.sample.nbateamviewer.database.dao.TeamDao
import android.sample.nbateamviewer.model.Player
import android.sample.nbateamviewer.model.Team
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Team::class, Player::class],
    exportSchema = false,
    version = 1
)
abstract class NbaViewerDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao

    companion object {
        private lateinit var nbaViewerDatabase: NbaViewerDatabase

        fun getInstance(context: Context): NbaViewerDatabase {
            if (!Companion::nbaViewerDatabase.isInitialized) {
                buildDbInstance(context)
            }
            return nbaViewerDatabase
        }

        private fun buildDbInstance(context: Context) {
            nbaViewerDatabase = Room.databaseBuilder(
                context,
                NbaViewerDatabase::class.java,
                "nbaViewer.sqlite"
            )
                .build()
        }
    }
}