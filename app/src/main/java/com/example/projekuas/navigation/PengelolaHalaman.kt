package com.example.projekuas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projekuas.HalamanHomeView.DestinasiHome
import com.example.projekuas.HalamanHomeView.HomeScreen
import com.example.projekuas.AddPelanggan.DestinasiEntry
import com.example.projekuas.AddPelanggan.HalamanPelanggan
import com.example.projekuas.DetailPelanggan.DetailDestination
import com.example.projekuas.DetailPelanggan.DetailScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(
            DestinasiHome.route
        ) {
            HomeScreen(navigateToItemEntry = {
                navController.navigate(DestinasiEntry.route)
            },
                onDetailClick = { itemId ->
                    navController.navigate("${DetailDestination.route}/$itemId")
                    println("itemId: $itemId")
                })

        }
        composable(DestinasiEntry.route) {
            HalamanPelanggan(navigateBack = {
                navController.popBackStack()
            }
            )

        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.kontakId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val pelangganId = backStackEntry.arguments?.getString(DetailDestination.kontakId)
            pelangganId?.let {
                DetailScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = {
//                        navController.navigate("${EditDestination.route}/$pelangganId")
                        println("pelangganId: $pelangganId")
                    }
                )
            }
        }
    }
}