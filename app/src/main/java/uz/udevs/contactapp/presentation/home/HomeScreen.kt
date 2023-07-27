package uz.udevs.contactapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.udevs.contactapp.ContactsTopAppBar
import uz.udevs.contactapp.R
import uz.udevs.contactapp.data.entity.Contact
import uz.udevs.contactapp.presentation.navigation.NavigationDestination


object HomeScreenDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.my_contacts
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    navigateToEntryScreen: () -> Unit,
    navigateToUpdateScreen: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val homeUiState = viewModel.homeUiState.collectAsState()

    Scaffold(
        topBar = {
            ContactsTopAppBar(
                title = stringResource(HomeScreenDestination.titleRes),
                navigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntryScreen,
                modifier = modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Entry Contact",
                    tint = Color.Blue
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            contactList = homeUiState.value.contactList,
            onContactClick = navigateToUpdateScreen,
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        )
    }
}

@Composable
fun HomeBody(
    contactList: List<Contact>,
    onContactClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (contactList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.list_empty),
                contentDescription = null,
                modifier = modifier.size(200.dp)
            )
            Text(
                text = stringResource(R.string.no_contacts),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
        }

    } else {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            ContactList(
                contactList = contactList,
                onContactClick = { onContactClick(it.id) }
            )
        }

    }
}


@Composable
fun ContactList(
    contactList: List<Contact>,
    onContactClick: (Contact) -> Unit,
) {
    LazyColumn {
        items(contactList, { it.id }) { contact ->
            ContactItems(
                contact = contact,
                onContactClick = onContactClick
            )
            Divider()
        }
    }
}


@Composable
fun ContactItems(
    contact: Contact,
    onContactClick: (Contact) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onContactClick(contact) }
        ) {
            Image(
                painter = painterResource(R.drawable.contacts_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = contact.name,
                textAlign = TextAlign.Left,
                modifier = modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
