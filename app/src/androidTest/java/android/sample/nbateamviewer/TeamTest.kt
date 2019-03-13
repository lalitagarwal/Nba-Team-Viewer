package android.sample.nbateamviewer

import android.sample.nbateamviewer.database.NbaViewerDatabase
import android.sample.nbateamviewer.database.dao.TeamDao
import android.sample.nbateamviewer.model.Team
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TeamTest {
    private lateinit var teamDao: TeamDao
    private lateinit var nbaViewerDatabase: NbaViewerDatabase

    private val FAKE_TEAM1 = Team(1, "Boston Celtics", 45, 20, listOf())
    private val FAKE_TEAM2 = Team(2, "Toronto Raptors", 50, 25, listOf())
    private val TEAMS_LIST = listOf(FAKE_TEAM1, FAKE_TEAM2)

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        nbaViewerDatabase = Room.inMemoryDatabaseBuilder(
            context, NbaViewerDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        teamDao = nbaViewerDatabase.teamDao()
    }

    @After
    fun closeDb() {
        nbaViewerDatabase.close()
    }

    @Test
    fun writeUserAndReadInList() {
        teamDao.insert(TEAMS_LIST)

        val teamList = teamDao.getTeams()
        assertEquals(2, teamList.size)
        assertEquals(teamList[0].name, TEAMS_LIST[0].name)
    }

    @Test
    fun testGetTeamById() {
        teamDao.insert(TEAMS_LIST)

        val team = teamDao.getTeamForGivenId(2)
        assertEquals(team.name, TEAMS_LIST[1].name)
    }
}
