package com.cvopa.peter.scratchy

import app.cash.turbine.test
import com.cvopa.peter.core.Result
import com.cvopa.peter.core.ScratchRepository
import com.cvopa.peter.core.ScratchState
import com.cvopa.peter.scratchy.ui.activation.ActivationScreenActions
import com.cvopa.peter.scratchy.ui.activation.ActivationScreenViewModel
import com.cvopa.peter.scratchy.ui.main.ScratchStateUI
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ActivationViewModelTest {

    @get:Rule(order = 0) val mainDispatcherRule = MainDispatcherRule()

    private lateinit var subject: ActivationScreenViewModel
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

    @Test
    fun `when activation is clicked, repository is called with success result then activation is success`() =
        runTest {
            coEvery { scratchRepository.sendActivationCode(any()) } returns Result.Success
            initViewModel()
            subject.onAction(ActivationScreenActions.OnActivation)
            coVerify { scratchRepository.sendActivationCode(any()) }
            subject.state.test {
                val item = awaitItem()
                item.error.shouldBeNull()
            }
        }

    @Test
    fun `when activation is clicked, repository is called with erroneous result then activation is in error state`() =
        runTest {
            coEvery { scratchRepository.sendActivationCode(any()) } returns Result.Error.NoCode
            initViewModel()
            subject.onAction(ActivationScreenActions.OnActivation)
            coVerify { scratchRepository.sendActivationCode(any()) }
            subject.state.test {
                val item = awaitItem()
                item.error.shouldNotBeNull()
            }
        }

    @Test
    fun `when activation is clicked, repository is called with erroneous result then activation is in error state and then error is clear the error state is null`() =
        runTest {
            coEvery { scratchRepository.sendActivationCode(any()) } returns Result.Error.NoCode
            initViewModel()
            subject.onAction(ActivationScreenActions.OnActivation)
            coVerify { scratchRepository.sendActivationCode(any()) }
            subject.state.test {
                val item1 = awaitItem()
                item1.error.shouldNotBeNull()
                subject.onAction(ActivationScreenActions.OnDismissError)
                val item2 = awaitItem()
                item2.error.shouldBeNull()
            }
        }

    private fun initViewModel() {
        subject =
            ActivationScreenViewModel(
                scratchRepository = scratchRepository,
            )
    }
}
