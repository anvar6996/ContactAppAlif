package uz.udevs.contactapp.presentation.contact.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import uz.udevs.contactapp.ContactsTopAppBar
import uz.udevs.contactapp.R
import uz.udevs.contactapp.presentation.contact.entry.EntryBody
import uz.udevs.contactapp.presentation.navigation.NavigationDestination


object EditScreenDestination : NavigationDestination {
    override val route = "contact_edit"
    override val titleRes = R.string.edit_contact
    const val contactIdArg = "contactId"
    val routeWithArgs = "$route/{$contactIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditScreenViewModel = hiltViewModel<EditScreenViewModel>()
) {

    Scaffold(topBar = {
        ContactsTopAppBar(
            title = stringResource(EditScreenDestination.titleRes),
            navigateBack = true,
            navigateUp = onNavigateUp
        )
    }) { innerPadding ->
        EntryBody(
            contactUiState = viewModel.contactUiState,
            onContactValueChange = viewModel::updateUiState,
            onSaveClick = {
                viewModel.onEvent(EditContactIntent.EditContact(viewModel.contactUiState))
                navigateBack()
            },
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        )
    }
}