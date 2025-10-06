package com.cvopa.peter.core
sealed class Result {
    data object Success : Result()

    sealed class Error : Result() {
        data object InvalidCode : Error()

        data object NetworkResult : Error()

        data object NoCode : Error()

        data object General : Error()
    }
}
