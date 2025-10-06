package com.cvopa.peter.scratchy.ui.activation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.cvopa.peter.core.ScratchRepository
import com.cvopa.peter.scratchy.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
   val scratchRepository: ScratchRepository,
) :
    BaseViewModel<ActivationScreenState, ActivationScreenEvent, ActivationScreenActions>(),
    DefaultLifecycleObserver {

    override val initialState: ActivationScreenState
        get() = ActivationScreenState

    override fun handleAction(action: ActivationScreenActions) {
        when(action){
            ActivationScreenActions.OnActivation -> {
                viewModelScope.launch {
                    scratchRepository.sendActivationCode()
                }
            }
        }
    }
}

data object ActivationScreenState

sealed class ActivationScreenEvent {
    data object NavigateToLogin : ActivationScreenEvent()
}

sealed class ActivationScreenActions {
    data object OnActivation : ActivationScreenActions()
}

