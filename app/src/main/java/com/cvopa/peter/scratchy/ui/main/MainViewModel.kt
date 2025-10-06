package com.cvopa.peter.scratchy.ui.main

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.cvopa.peter.core.ScratchRepository
import com.cvopa.peter.core.ScratchState
import com.cvopa.peter.scratchy.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val scratchRepository: ScratchRepository,
) :
    BaseViewModel<MainViewScreenState, MainAppEvent, MainScreenActions>(),
    DefaultLifecycleObserver {

    override val initialState: MainViewScreenState
        get() = MainViewScreenState()

    init {
        scratchRepository.observeScratchState().onEach { newCardState ->
            emitState { previousState ->
                previousState.copy(cardState = newCardState.toUI())
            }
        }.launchIn(viewModelScope)
    }

    override fun handleAction(action: MainScreenActions) {
        when (action) {
            MainScreenActions.OnButtonActivationClicked -> {
                emitEvent(MainAppEvent.NavigateToActivation)
            }

            MainScreenActions.OnButtonScratchClicked -> {
                emitEvent(MainAppEvent.NavigateToScratch)
            }
        }
    }
}

data class MainViewScreenState(val cardState: ScratchStateUI = ScratchStateUI.UNSCRATCHED)

sealed class ScratchStateUI() {
    data object UNSCRATCHED : ScratchStateUI()
    data class SCRATCHED(val code: String) : ScratchStateUI()
    data object ACTIVATED : ScratchStateUI()
}

fun ScratchState.toUI(): ScratchStateUI {
    return when (this) {
        ScratchState.UNSCRATCHED -> ScratchStateUI.UNSCRATCHED
        is ScratchState.SCRATCHED -> ScratchStateUI.SCRATCHED(code)
        is ScratchState.ACTIVATED -> ScratchStateUI.ACTIVATED
    }
}

sealed class MainAppEvent {
    data object NavigateToActivation : MainAppEvent()
    data object NavigateToScratch : MainAppEvent()
}

sealed class MainScreenActions {
    data object OnButtonActivationClicked : MainScreenActions()
    data object OnButtonScratchClicked : MainScreenActions()
}

