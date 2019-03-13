package android.sample.nbateamviewer

import android.sample.nbateamviewer.database.NbaViewerDatabase
import android.sample.nbateamviewer.database.dao.PlayerDao
import android.sample.nbateamviewer.database.dao.TeamDao
import android.sample.nbateamviewer.model.Player
import android.sample.nbateamviewer.model.Team
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PlayerTest {
    private lateinit var teamDao: TeamDao
    private lateinit var playerDao: PlayerDao
    private lateinit var nbaViewerDatabase: NbaViewerDatabase

    private val FAKE_TEAM1 = Team(1001, "Boston Celtics", 45, 20, listOf())
    private val FAKE_TEAM2 = Team(1002, "Toronto Raptors", 50, 25, listOf())
    private val TEAMS_LIST = listOf(FAKE_TEAM1, FAKE_TEAM2)

    private val FAKE_PLAYER1 = Player(4001, 1001, "Kadeem", "Allen", "SG", 45)
    private val FAKE_PLAYER2 = Player(4002, 1001, "Aron", "Baynes", "C", 46)
    private val FAKE_PLAYER3 = Player(4003, 1002, "Lorenzo", "Brown", "PG", 4)
    private val FAKE_PLAYER4 = Player(4004, 1002, "Serge", "Ibaka", "F-C", 85)
    private val PLAYERS_LIST = listOf(FAKE_PLAYER1, FAKE_PLAYER2, FAKE_PLAYER3, FAKE_PLAYER4)

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        nbaViewerDatabase = Room.inMemoryDatabaseBuilder(
            context, NbaViewerDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        teamDao = nbaViewerDatabase.teamDao()
        playerDao = nbaViewerDatabase.playerDao()
    }

    @After
    fun closeDb() {
        nbaViewerDatabase.close()
    }

    @Test
    fun writeUserAndReadInList() {
        teamDao.insert(TEAMS_LIST)
        playerDao.insert(PLAYERS_LIST)

        val playerList = playerDao.getPlayersForTeam(1001)
        assertEquals(2, playerList.size)
        assertEquals(playerList[0].firstName, PLAYERS_LIST[0].firstName)
    }
}
