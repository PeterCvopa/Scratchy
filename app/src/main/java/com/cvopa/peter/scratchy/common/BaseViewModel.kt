package com.cvopa.peter.scratchy.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<State : Any, Event : Any, Action : Any> : ViewModel() {

    protected abstract val initialState: State

    private val _event = Channel<Event>(Channel.CONFLATED)
    val event = _event.receiveAsFlow()

    init {
        Timber.tag(this::class.simpleName ?: "").d("Initialization ${this::class.simpleName}")
    }

    private val _state: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }

    val state: StateFlow<State>
        get() = _state

    fun onAction(action: Action) {
        Timber.tag(this::class.simpleName ?: "").d("Received action: $action")
        handleAction(action)
    }

    protected fun emitState(state: State) {
        Timber.tag(this::class.simpleName ?: "")
            .d("${this@BaseViewModel.hashCode()} Emits state: $state ")
        viewModelScope.launch { _state.update { state } }
    }

    protected fun emitState(state: (State) -> State) {
        viewModelScope.launch {
            _state.update { previousState ->
                state(previousState).also { newState ->
                    Timber.tag(this@BaseViewModel::class.simpleName ?: "")
                        .d("${this.hashCode()} Emit state: $newState")
                }
            }
        }
    }

    protected fun emitEvent(event: Event) {
        Timber.tag(this::class.simpleName ?: "").d("Emit event: $event")
        viewModelScope.launch { _event.send(event) }
    }

    abstract fun handleAction(action: Action)

    override fun onCleared() {
        Timber.tag(this::class.simpleName ?: "").d("${this@BaseViewModel.hashCode()}  onCleared")
        super.onCleared()
    }
}
