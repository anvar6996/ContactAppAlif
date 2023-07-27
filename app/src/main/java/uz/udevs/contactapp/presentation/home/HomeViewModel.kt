package uz.udevs.contactapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import uz.udevs.contactapp.data.entity.Contact
import uz.udevs.contactapp.domain.ContactsRepository
import javax.inject.Inject

data class HomeUiState(
    val contactList: List<Contact> = listOf()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    contactsRepository: ContactsRepository
): ViewModel(){
    val homeUiState: StateFlow<HomeUiState> =
        contactsRepository.getAllContactsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}