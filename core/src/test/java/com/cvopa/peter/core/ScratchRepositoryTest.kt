package com.cvopa.peter.core

import app.cash.turbine.test
import com.cvopa.peter.network.ActivationResponse
import com.cvopa.peter.network.ActivationServiceDataSource
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScratchRepositoryTest {
    @get:Rule(order = 0) val mainDispatcherRule = MainDispatcherRule()

    private val activationServiceDataSource: ActivationServiceDataSource = mockk()

    private lateinit var subject: ScratchRepositoryImpl

    @Before
    fun setUp() {
        subject = ScratchRepositoryImpl(activationServiceDataSource)
    }

    @Test
    fun `observeScratchState should initially emit UNSCRATCHED`() = runTest {
        subject.observeScratchState().test {
            awaitItem().shouldBeTypeOf<ScratchState.UNSCRATCHED>()
            expectNoEvents()
        }
    }

    val validResponse = ActivationResponse("1344343344")
    val invalidResponse = ActivationResponse("0")

    @Test
    fun `when sendActivationCode is called, it should emit ACTIVATED if response is valid`() =
        runTest {
            coEvery { activationServiceDataSource.sendActivationCode(any()) } returns validResponse
            val result = subject.sendActivationCode("fsdfs")
            result.shouldBe(Result.Success)

            subject.observeScratchState().test {
                val item = awaitItem()
                item.shouldBeTypeOf<ScratchState.ACTIVATED>()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when sendActivationCode is called, it should return InvalidCode if response is invalid`() =
        runTest {
            coEvery { activationServiceDataSource.sendActivationCode(any()) } returns
                invalidResponse
            val result = subject.sendActivationCode("fsdfs")
            result.shouldBe(Result.Error.InvalidCode)
        }

    @Test
    fun `when scratchCard is called, it should emit SCRATCHED with a new code with state SCRACHED and card state should SCRACHED`() =
        runTest {
            val result = subject.scratchCard()
            result.shouldBeTypeOf<ScratchState.SCRATCHED>()
            result.code.isEmpty().shouldBeFalse()

            subject.observeScratchState().test {
                val item = awaitItem()
                item.shouldBeTypeOf<ScratchState.SCRATCHED>()
                cancelAndIgnoreRemainingEvents()
            }
        }
}
