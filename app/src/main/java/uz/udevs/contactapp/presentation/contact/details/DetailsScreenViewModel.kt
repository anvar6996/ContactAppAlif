package uz.udevs.contactapp.presentation.contact.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.udevs.contactapp.domain.ContactsRepository
import uz.udevs.contactapp.presentation.contact.ContactUiState
import uz.udevs.contactapp.presentation.contact.toContact
import uz.udevs.contactapp.presentation.contact.toContactUiState
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    state: SavedStateHandle,
    private val contactsRepository: ContactsRepository
) : ViewModel() {
    private val contactId: Int = checkNotNull(state[DetailsScreenDestination.contactIdArg])

    val uiState: StateFlow<ContactUiState> = contactsRepository.getContactStream(contactId)
        .filterNotNull()
        .map { it.toContactUiState(actionEnable = true) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ContactUiState()
        )

    fun onEvent(detailsContactIntent: DetailsContactIntent) {
        when (detailsContactIntent) {
            DetailsContactIntent.DeleteTask -> {
                deleteContact()
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private fun deleteContact() {
        viewModelScope.launch {
            contactsRepository.deleteContact(uiState.value.toContact())
        }
    }
}