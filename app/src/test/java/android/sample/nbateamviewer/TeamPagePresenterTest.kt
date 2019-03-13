package android.sample.nbateamviewer

import android.sample.nbateamviewer.database.NbaViewerDatabase
import android.sample.nbateamviewer.database.dao.TeamDao
import android.sample.nbateamviewer.database.repository.TeamRepository
import android.sample.nbateamviewer.model.Team
import android.sample.nbateamviewer.ui.contract.TeamListContractor
import android.sample.nbateamviewer.ui.fragment.TeamListFragment.Companion.LOSSES_SORT
import android.sample.nbateamviewer.ui.fragment.TeamListFragment.Companion.NAME_SORT
import android.sample.nbateamviewer.ui.fragment.TeamListFragment.Companion.WINS_SORT
import android.sample.nbateamviewer.ui.presenter.TeamListPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TeamPagePresenterTest {
    private val FAKE_TEAM1 = Team(1001, "Boston Celtics", 45, 20, listOf())
    private val FAKE_TEAM2 = Team(1002, "Toronto Raptors", 50, 25, listOf())
    private val TEAMS_LIST = listOf(FAKE_TEAM1, FAKE_TEAM2)

    private lateinit var teamListPresenter: TeamListContractor.Presenter

    private lateinit var teamRepository: TeamRepository

    @Mock
    private lateinit var mockTeamListFragment: TeamListContractor.View

    @Mock
    private lateinit var mockDatabase: NbaViewerDatabase

    @Mock
    private lateinit var mockTeamDao: TeamDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        teamRepository = TeamRepository(mockDatabase)
        teamListPresenter =
            TeamListPresenter(mockTeamListFragment, teamRepository, Dispatchers.Unconfined, Dispatchers.Unconfined)
    }

    @Test
    fun testSetList() {
        `when`(mockDatabase.teamDao()).thenReturn(mockTeamDao)
        `when`(teamRepository.getTeamsFromDB()).thenReturn(TEAMS_LIST)
        runBlocking {
            teamListPresenter.setList()
            verify(mockTeamListFragment).showLoader()
            verify(mockTeamListFragment).hideLoader()
            verify(mockTeamListFragment).setRecyclerView(ArgumentMatchers.anyList())
        }
    }

    @Test
    fun testSetComparatorNameSort() {
        teamListPresenter.setComparator(NAME_SORT, ArgumentMatchers.anyInt())
        verify(mockTeamListFragment).resetSortUI(listOf(WINS_SORT, LOSSES_SORT))
        verify(mockTeamListFragment).updateListView(ArgumentMatchers.anyList())
    }

    @Test
    fun testSetComparatorWinsSort() {
        teamListPresenter.setComparator(WINS_SORT, ArgumentMatchers.anyInt())
        verify(mockTeamListFragment).resetSortUI(listOf(NAME_SORT, LOSSES_SORT))
        verify(mockTeamListFragment).updateListView(ArgumentMatchers.anyList())
    }

    @Test
    fun testSetComparatorLossesSort() {
        teamListPresenter.setComparator(LOSSES_SORT, ArgumentMatchers.anyInt())
        verify(mockTeamListFragment).resetSortUI(listOf(NAME_SORT, WINS_SORT))
        verify(mockTeamListFragment).updateListView(ArgumentMatchers.anyList())
    }
}