package com.cvopa.peter.scratchy.ui.scratch

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.cvopa.peter.core.ScratchRepository
import com.cvopa.peter.scratchy.common.BaseViewModel
import com.cvopa.peter.scratchy.ui.main.ScratchStateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ScratchScreenViewModel
@Inject
constructor(
    val scratchRepository: ScratchRepository,
) :
    BaseViewModel<ScratchScreenState, ScratchScreenEvent, ScratchScreenActions>(),
    DefaultLifecycleObserver {

    override val initialState: ScratchScreenState
        get() = ScratchScreenState()

    override fun handleAction(action: ScratchScreenActions) {
        when (action) {
            ScratchScreenActions.OnScratchClicked -> {
                viewModelScope.launch { startScratch() }
            }
        }
    }

    private suspend fun startScratch() {
        emitState { it.copy(cardState = ScratchStateUI.SCRATCHING) }
        val newCode = scratchRepository.scratchCard()
        if (newCode == null) {
            emitState { it.copy(cardState = ScratchStateUI.UNSCRATCHED) }
        } else {
            emitState { it.copy(cardState = ScratchStateUI.SCRATCHED(newCode.code)) }
        }
    }
}

data class ScratchScreenState(
    val errorState: Boolean = false,
    val cardState: ScratchStateUI = ScratchStateUI.UNSCRATCHED
)

sealed class ScratchScreenEvent {}

sealed class ScratchScreenActions {
    data object OnScratchClicked : ScratchScreenActions()
}
