package com.cvopa.peter.scratchy.common

import com.cvopa.peter.core.Result

enum class ErrorState(val message: String) {
    NoCardCodeError("No card code!"),
    InvalidCodeError("Invalid card error!"),
    NetworkError("Network error!"),
    UnknownError("General error. Something went wrong!")
}

fun Result.toErrorState(): ErrorState? {
    return when (this) {
        Result.Error.General -> ErrorState.UnknownError
        Result.Error.InvalidCode -> ErrorState.InvalidCodeError
        Result.Error.NetworkResult -> ErrorState.NetworkError
        Result.Error.NoCode -> ErrorState.NoCardCodeError
        Result.Success -> null
    }
}
