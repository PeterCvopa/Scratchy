package com.cvopa.peter.core

import android.net.http.HttpException
import com.cvopa.peter.network.ActivationResponse
import com.cvopa.peter.network.ActivationServiceDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

const val SCRATCH_DELAY = 2000 //ms

interface ScratchRepository {
    fun observeScratchState(): Flow<ScratchState>
    suspend fun sendActivationCode(): Result
    suspend fun scratchCard()
}

@Singleton
class ScratchRepositoryImpl @Inject constructor(
    private val activationServiceDataSource: ActivationServiceDataSource
) : ScratchRepository {

    private val cardState = MutableStateFlow<ScratchState>(ScratchState.UNSCRATCHED)

    override fun observeScratchState(): Flow<ScratchState> {
        return cardState
    }

    override suspend fun sendActivationCode(): Result {
        return runCatching {
            val code = cardState.value.code ?: return Result.Error.NoCode
            val isCodeValid = activationServiceDataSource.sendActivationCode(code).isValid()
            if (isCodeValid) {
                Result.Success
            } else {
                Result.Error.InvalidCode
            }

        }.onSuccess { code ->
            cardState.emit(ScratchState.ACTIVATED)
        }.onFailure {
            if(it is HttpException){
                Result.Error.NetworkResult
            }else{
                Result.Error.General
            }
        }
            .getOrNull() ?: Result.Error.General
    }

    override suspend fun scratchCard() {
        val totalDuration = SCRATCH_DELAY
        val increment = 100L
        val steps = totalDuration / increment
        for (i in 1..steps) {
            if (coroutineContext.isActive.not()) {
                return
            }
            delay(increment)
        }
        cardState.value = ScratchState.SCRATCHED(UUID.randomUUID().toString())
    }
}


fun ActivationResponse.isValid(): Boolean {
    return runCatching {
        androidVersion.toInt() > 277028
    }.getOrNull() ?: false
}

sealed class ScratchState(open val code : String?) {
    data object UNSCRATCHED : ScratchState(null)
    data class SCRATCHED(override val code: String) : ScratchState(code)
    data object ACTIVATED : ScratchState(null)

}

enum class CoreExceptions(val message: String) {
    NoCardCodeError("No card code"), NetworkError("No card code"),
    UnknownError("general error");
}