package com.cvopa.peter.scratchy.ui.activation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.cvopa.peter.core.ScratchRepository
import com.cvopa.peter.scratchy.common.BaseViewModel
import com.cvopa.peter.scratchy.common.ErrorState
import com.cvopa.peter.scratchy.common.toErrorState
import com.cvopa.peter.scratchy.ui.main.ScratchStateUI
import com.cvopa.peter.scratchy.ui.main.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class ActivationScreenViewModel
@Inject
constructor(
    val scratchRepository: ScratchRepository,
) :
    BaseViewModel<ActivationScreenState, ActivationScreenEvent, ActivationScreenActions>(),
    DefaultLifecycleObserver {

    override val initialState: ActivationScreenState
        get() = ActivationScreenState()

    init {
        scratchRepository
            .observeScratchState()
            .onEach { emitState { state -> state.copy(cardState = it.toUI()) } }
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: ActivationScreenActions) {
        when (action) {
            ActivationScreenActions.OnActivation -> {
                viewModelScope.launch {
                    val result = scratchRepository.sendActivationCode()
                    emitState { it.copy(error = result.toErrorState()) }
                }
            }
            ActivationScreenActions.OnDismissError -> {
                emitState { previousState -> previousState.copy(error = null) }
            }
        }
    }
}

data class ActivationScreenState(
    val error: ErrorState? = null,
    val cardState: ScratchStateUI = ScratchStateUI.UNSCRATCHED,
)

data object ActivationScreenEvent

sealed class ActivationScreenActions {
    data object OnActivation : ActivationScreenActions()

    data object OnDismissError : ActivationScreenActions()
}
