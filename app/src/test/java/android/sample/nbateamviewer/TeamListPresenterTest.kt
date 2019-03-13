package android.sample.nbateamviewer

import android.sample.nbateamviewer.database.NbaViewerDatabase
import android.sample.nbateamviewer.database.dao.PlayerDao
import android.sample.nbateamviewer.database.dao.TeamDao
import android.sample.nbateamviewer.database.repository.TeamRepository
import android.sample.nbateamviewer.model.Player
import android.sample.nbateamviewer.model.Team
import android.sample.nbateamviewer.ui.contract.TeamPageContractor
import android.sample.nbateamviewer.ui.presenter.TeamPagePresenter
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TeamListPresenterTest {
    private val FAKE_TEAM1 = Team(1001, "Boston Celtics", 45, 20, listOf())

    private val FAKE_PLAYER1 = Player(4001, 1001, "Kadeem", "Allen", "SG", 45)
    private val FAKE_PLAYER2 = Player(4002, 1001, "Aron", "Baynes", "C", 46)
    private val FAKE_PLAYER3 = Player(4003, 1002, "Lorenzo", "Brown", "PG", 4)
    private val FAKE_PLAYER4 = Player(4004, 1002, "Serge", "Ibaka", "F-C", 85)
    private val PLAYERS_LIST = listOf(FAKE_PLAYER1, FAKE_PLAYER2, FAKE_PLAYER3, FAKE_PLAYER4)

    private val FAKE_TEAM_ID = 1001

    private lateinit var teamPagePresenter: TeamPageContractor.Presenter

    private lateinit var teamRepository: TeamRepository

    @Mock
    private lateinit var mockTeamPageFragment: TeamPageContractor.View

    @Mock
    private lateinit var mockDatabase: NbaViewerDatabase

    @Mock
    private lateinit var mockTeamDao: TeamDao

    @Mock
    private lateinit var mockPlayerDao: PlayerDao

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        teamRepository = TeamRepository(mockDatabase)
        teamPagePresenter =
            TeamPagePresenter(
                mockTeamPageFragment,
                teamRepository,
                FAKE_TEAM_ID,
                Unconfined,
                Unconfined
            )
    }

    @Test
    fun testSetList() {
        `when`(mockDatabase.teamDao()).thenReturn(mockTeamDao)
        `when`(mockDatabase.playerDao()).thenReturn(mockPlayerDao)
        `when`(teamRepository.getTeamForGivenId(FAKE_TEAM_ID)).thenReturn(FAKE_TEAM1)
        `when`(teamRepository.getPlayersForTeamId(FAKE_TEAM_ID)).thenReturn(PLAYERS_LIST)

        runBlocking {
            teamPagePresenter.setList()
            verify(mockTeamPageFragment, times(2)).showLoader()
            verify(mockTeamPageFragment, times(2)).hideLoader()
            verify(mockTeamPageFragment).updateTeamData(any(Team::class.java))
            verify(mockTeamPageFragment).setRecyclerView(ArgumentMatchers.anyList())
        }
    }
}