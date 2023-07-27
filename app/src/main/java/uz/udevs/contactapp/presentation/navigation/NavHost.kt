package uz.udevs.contactapp.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uz.udevs.contactapp.presentation.contact.details.DetailsScreen
import uz.udevs.contactapp.presentation.contact.details.DetailsScreenDestination
import uz.udevs.contactapp.presentation.contact.edit.EditScreen
import uz.udevs.contactapp.presentation.contact.edit.EditScreenDestination
import uz.udevs.contactapp.presentation.contact.edit.EditScreenViewModel
import uz.udevs.contactapp.presentation.contact.entry.EntryScreen
import uz.udevs.contactapp.presentation.contact.entry.EntryScreenDestination
import uz.udevs.contactapp.presentation.home.HomeScreen
import uz.udevs.contactapp.presentation.home.HomeScreenDestination
import uz.udevs.contactapp.presentation.home.HomeViewModel


@ExperimentalMaterial3Api
@Composable
fun ContactsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeScreenDestination.route) {
            HomeScreen(navigateToEntryScreen = {
                navController.navigate(EntryScreenDestination.route)
            }, navigateToUpdateScreen = {
                navController.navigate("${DetailsScreenDestination.route}/${it}")
            }, modifier = Modifier, viewModel = hiltViewModel<HomeViewModel>()
            )
        }
        composable(route = EntryScreenDestination.route) {
            EntryScreen(
                onNavigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DetailsScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailsScreenDestination.contactIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailsScreen(
                navigateToEditContact = { navController.navigate("${EditScreenDestination.route}/${it}") },
                navigateBack = { navController.navigateUp() })
        }
        composable(
            route = EditScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(EditScreenDestination.contactIdArg) {
                type = NavType.IntType
            })
        ) {
            EditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                modifier = Modifier
                , viewModel = hiltViewModel<EditScreenViewModel>()
            )
        }
    }
}