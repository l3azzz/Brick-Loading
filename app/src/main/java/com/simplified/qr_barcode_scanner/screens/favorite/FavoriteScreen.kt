package com.simplified.qr_barcode_scanner.screens.favorite


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simplified.qr_barcode_scanner.data.datastore.PublicViewModel
import com.simplified.qr_barcode_scanner.navigation.HeaderBar.HeaderBar

@Composable
fun FavoriteScreen(
    publicViewModel: PublicViewModel,
    navController: NavController? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(27, 26, 25))
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            HeaderBar(45, "Favorite", navController)


        }

    }
}