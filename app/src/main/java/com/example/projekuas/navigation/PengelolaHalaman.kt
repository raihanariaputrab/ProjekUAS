package com.example.contactapp_with_firebase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projekuas.ui.theme.AddPelanggan.DestinasiEntry
import com.example.projekuas.ui.theme.AddPelanggan.HalamanPelanggan
import com.example.projekuas.ui.theme.DetailPelangga.DetailDestination
import com.example.projekuas.ui.theme.DetailPelangga.DetailScreen
import com.example.projekuas.ui.theme.EditPelanggan.EditDestination
import com.example.projekuas.ui.theme.EditPelanggan.EditScreen
import com.example.projekuas.ui.theme.HalamanHomeView.DestinasiHome
import com.example.projekuas.ui.theme.HalamanHomeView.HomeScreen
import com.example.projekuas.ui.theme.home.DestinasiUtama
import com.example.projekuas.ui.theme.home.HalamanUtama


@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = DestinasiUtama.route,
        modifier = Modifier
    ) {
        composable(DestinasiUtama.route) {
            HalamanUtama(onNextButtonClicked = {
                navController.navigate(DestinasiHome.route)
            })

        }
        composable(
            DestinasiHome.route
        ) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { itemId ->
                    navController.navigate("${DetailDestination.route}/$itemId")
                    println("itemId: $itemId")
                })
        }
        composable(DestinasiEntry.route) {
            HalamanPelanggan(navigateBack = {
                navController.popBackStack()
            })

        }

        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.IdPelanggan) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val pelangganId = backStackEntry.arguments?.getString(DetailDestination.IdPelanggan)
            pelangganId?.let {
                DetailScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = {
                        navController.navigate("${EditDestination.route}/$pelangganId")
                        println("pelangganId: $pelangganId")
                    }
                )
            }
        }

        composable(
            route = EditDestination.routeWithArgs,
            arguments = listOf(navArgument(EditDestination.pelangganId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val pelangganId = backStackEntry.arguments?.getString(EditDestination.pelangganId)
            pelangganId?.let {
                EditScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}