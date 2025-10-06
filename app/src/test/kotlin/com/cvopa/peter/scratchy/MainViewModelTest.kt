package com.cvopa.peter.scratchy

import app.cash.turbine.test
import com.cvopa.peter.core.ScratchRepository
import com.cvopa.peter.core.ScratchState
import com.cvopa.peter.scratchy.ui.main.MainViewModel
import com.cvopa.peter.scratchy.ui.main.ScratchStateUI
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule(order = 0) val mainDispatcherRule = MainDispatcherRule()

    private lateinit var subject: MainViewModel
    private val scratchRepository: ScratchRepository = mockk(relaxed = false, relaxUnitFun = false)
    val cardStateTestFlow = MutableStateFlow<ScratchState>(ScratchState.UNSCRATCHED)

    @Before
    fun setup() {
        coEvery { scratchRepository.observeScratchState() } returns cardStateTestFlow
    }

    @Test
    fun `when is initialized, state should be UNSCRATCHED`() = runTest {
        initViewModel()
        subject.state.test { awaitItem().cardState.shouldBe(ScratchStateUI.UNSCRATCHED) }
    }

    @Test
    fun `state updates when repository emits new ScratchState SCRATCHED`() = runTest {
        initViewModel()
        val testString = "testStr"
        cardStateTestFlow.emit(ScratchState.SCRATCHED(testString))
        subject.state.test {
            val item = awaitItem()
            item.cardState.shouldBeTypeOf<ScratchStateUI.SCRATCHED>()
            item.cardState.code.shouldBe(testString)
        }
    }

    private fun initViewModel() {
        subject =
            MainViewModel(
                scratchRepository = scratchRepository,
            )
    }
}
