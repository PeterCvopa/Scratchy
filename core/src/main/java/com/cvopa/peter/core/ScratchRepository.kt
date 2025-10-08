package com.cvopa.peter.core

import com.cvopa.peter.network.ActivationResponse
import com.cvopa.peter.network.ActivationServiceDataSource
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import retrofit2.HttpException
import timber.log.Timber

const val SCRATCH_DELAY = 2000 // ms

interface ScratchRepository {
    fun observeScratchState(): Flow<ScratchState>

    suspend fun sendActivationCode(code: String?): Result

    suspend fun scratchCard(): ScratchState.SCRATCHED?
}

@Singleton
class ScratchRepositoryImpl
@Inject
constructor(private val activationServiceDataSource: ActivationServiceDataSource) :
    ScratchRepository {

    private val cardState = MutableStateFlow<ScratchState>(ScratchState.UNSCRATCHED)

    override fun observeScratchState(): Flow<ScratchState> {
        return cardState
    }

    override suspend fun sendActivationCode(code: String?): Result {
        return runCatching {
                val code = code ?: return Result.Error.NoCode
                val isCodeValid = activationServiceDataSource.sendActivationCode(code).isValid()
                if (isCodeValid) {
                    Result.Success
                } else {
                    Result.Error.InvalidCode
                }
            }
            .onSuccess { result ->
                if (result is Result.Success) {
                    cardState.emit(ScratchState.ACTIVATED)
                }
            }
            .onFailure {
                Timber.e(it)
                if (it is HttpException) {
                    Result.Error.NetworkResult
                } else {
                    Result.Error.General
                }
            }
            .getOrNull() ?: Result.Error.General
    }

    override suspend fun scratchCard(): ScratchState.SCRATCHED? {
        val totalDuration = SCRATCH_DELAY
        val increment = 100L
        val steps = totalDuration / increment
        (1..steps).forEach { i ->
            if (coroutineContext.isActive.not()) {
                return null
            }
            delay(increment)
        }
        val newCode = ScratchState.SCRATCHED(UUID.randomUUID().toString())
        cardState.emit(newCode)
        return newCode
    }
}

fun ActivationResponse.isValid(): Boolean {
    return runCatching { androidVersion.toInt() > 277028 }.getOrNull() ?: false
}

sealed class ScratchState(open val code: String?) {
    data object UNSCRATCHED : ScratchState(null)

    data class SCRATCHED(override val code: String) : ScratchState(code)

    data object ACTIVATED : ScratchState(null)
}
