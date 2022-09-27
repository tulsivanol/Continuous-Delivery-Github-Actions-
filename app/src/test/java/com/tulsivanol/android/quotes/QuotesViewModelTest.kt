package com.tulsivanol.android.quotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.tulsivanol.android.quotes.data.Quote
import com.tulsivanol.android.quotes.data.QuotesRepositoryImpl
import com.tulsivanol.android.quotes.ui.viewmodel.QuotesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class QuotesViewModelTest {

    @Mock
    private lateinit var viewModel: QuotesViewModel

    @Mock
    private lateinit var repositoryImpl: QuotesRepositoryImpl

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var isLoadingLiveData: LiveData<Boolean>

    /**
     * Setup values before init tests
     *
     */
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = spy(QuotesViewModel(repositoryImpl))
        isLoadingLiveData = viewModel.dataLoading
    }

    /**
     * Test asserting values for [LiveData] items on [QuotesViewModel] to insert [Quote]
     *
     */
    @Test
    fun `Assert loading values are correct fetching quotes`() {
        val testQuote = Quote(
            id = 1, text = "Hello World!", author = "Tulsi Vanol",
            date = "14/01/1999"
        )
        var isLoading = isLoadingLiveData.value
        isLoading?.let { assertTrue(it) }
        viewModel.insertQuote(testQuote)
        isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }
    }

    /**
     * Test asserting values for [LiveData] items on [QuotesViewModel] to delete [Quote]
     *
     */
    @Test
    fun `Assert loading values are correct deleting quote`() {
        val testQuote = Quote(
            id = 1, text = "Hello World!", author = "Tulsi Vanol",
            date = "14/01/1999"
        )
        var isLoading = isLoadingLiveData.value
        isLoading?.let { assertTrue(it) }
        viewModel.delete(testQuote)
        isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }
    }

    /**
     * Test asserting values for [LiveData] items on [QuotesViewModel] to update [Quote]
     *
     */
    @Test
    fun `Assert loading values are correct updating quote`() {
        val testQuote = Quote(
            id = 1, text = "Hello World!", author = "Tulsi Vanol",
            date = "14/01/1999"
        )
        var isLoading = isLoadingLiveData.value
        isLoading?.let { assertTrue(it) }
        viewModel.updateQuote(testQuote)
        isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }
    }


}