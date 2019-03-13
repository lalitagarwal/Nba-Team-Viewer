package android.sample.nbateamviewer

import android.app.Application
import android.sample.nbateamviewer.database.NbaViewerDatabase
import android.sample.nbateamviewer.database.repository.TeamRepository

class NbaViewerApplication : Application() {
    lateinit var nbaViewerDatabase: NbaViewerDatabase

    override fun onCreate() {
        super.onCreate()
        nbaViewerDatabase = NbaViewerDatabase.getInstance(this)
    }
}