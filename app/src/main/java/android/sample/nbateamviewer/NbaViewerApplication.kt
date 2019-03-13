package android.sample.nbateamviewer

import android.app.Application
import android.sample.nbateamviewer.database.NbaViewerDatabase
import android.sample.nbateamviewer.database.repository.TeamRepository

class NbaViewerApplication : Application() {
    var nbaViewerDatabase: NbaViewerDatabase? = null
    lateinit var repository: TeamRepository

    override fun onCreate() {
        super.onCreate()
        nbaViewerDatabase = NbaViewerDatabase.getInstance(this)
        nbaViewerDatabase?.let {
            repository = TeamRepository(it)
        }
    }
}