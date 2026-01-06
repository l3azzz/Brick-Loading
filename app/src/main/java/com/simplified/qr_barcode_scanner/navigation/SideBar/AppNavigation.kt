package com.simplified.qr_barcode_scanner.navigation.SideBar


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simplified.qr_barcode_scanner.R
import com.simplified.qr_barcode_scanner.data.datastore.PublicViewModel
import com.simplified.qr_barcode_scanner.data.datastore.PublicViewModelFactory
import com.simplified.qr_barcode_scanner.di.AppModuleImpl
import com.simplified.qr_barcode_scanner.screens.about.AboutScreen
import com.simplified.qr_barcode_scanner.screens.buymeacoffee.BuyMeCoffeeScreen
import com.simplified.qr_barcode_scanner.screens.create.CreateScreen
import com.simplified.qr_barcode_scanner.screens.favorite.FavoriteScreen
import com.simplified.qr_barcode_scanner.screens.history.HistoryScreen
import com.simplified.qr_barcode_scanner.screens.home.MainCameraScreen
import com.simplified.qr_barcode_scanner.screens.result.ScannedResultScreen
import com.simplified.qr_barcode_scanner.screens.settings.SettingsScreen
import com.simplified.qr_barcode_scanner.screens.welcome.WelcomeScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class DrawerItemInfo(val route: String, val title: String, val icon: Int)

@Composable
fun AppNavigation(onAppNavigationReady: () -> Unit) {
    LaunchedEffect(Unit) {
        onAppNavigationReady()
    }


    val context = LocalContext.current
    val appModule = remember { AppModuleImpl(context) }
    val publicViewModel: PublicViewModel = viewModel(
        factory = PublicViewModelFactory(appModule.qrRepository)
    )

    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    var showWelcome by remember { mutableStateOf(sharedPref.getBoolean("isFirstLaunch", true)) }


    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mainNavItems = listOf(
        DrawerItemInfo("main_camera_screen", "Camera", R.drawable.camera_icon),
        DrawerItemInfo("create_screen", "Create", R.drawable.create_icon),
        DrawerItemInfo("history_screen", "History", R.drawable.history_icon),
        DrawerItemInfo("favorite_screen", "Favorite", R.drawable.favorite_icon),

        )

    val supportNavItems = listOf(
        DrawerItemInfo("settings_screen", "Settings", R.drawable.settings_white),
        DrawerItemInfo("buymecoffee_screen", "Buy me a coffee", R.drawable.coffee_white),
        DrawerItemInfo("about_screen", "About", R.drawable.about_icon)
    )

    LaunchedEffect(showWelcome) {
        if (showWelcome) {
            delay(3500)
            showWelcome = false
            sharedPref.edit().putBoolean("isFirstLaunch", false).apply()
            navController.navigate("main_camera_screen") {
                popUpTo("welcome_screen") { inclusive = true }
            }
        }
    }


    ModalNavigationDrawer(
        gesturesEnabled = if (currentRoute == "main_camera_screen" && drawerState.isClosed) false else true,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(

                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth(0.8f)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    ),
                windowInsets = WindowInsets.systemBars,
                drawerContainerColor = Color(26, 26, 28)


            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(26, 26, 28))
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Menu",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Image(
                            painter = painterResource(id = R.drawable.menuclose_white),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    scope.launch { drawerState.close() }
                                }
                                .size(40.dp)

                        )
                    }

                    mainNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        DrawerItem(
                            item = item,
                            isSelected = isSelected,
                            navController = navController
                        ) {
                            scope.launch { drawerState.close() }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    supportNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        DrawerItem(
                            item = item,
                            isSelected = isSelected,
                            navController = navController
                        ) {
                            scope.launch { drawerState.close() }
                        }
                    }
                }
            }
        },
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        NavHost(
            navController = navController,
            startDestination = if (showWelcome) "welcome_screen" else "main_camera_screen"
        ) {
            composable("main_camera_screen") {
                MainCameraScreen(
                    publicViewModel = publicViewModel,
                    navController = navController,
                    onMenuClick = { scope.launch { drawerState.open() } })
            }
            composable("create_screen") { CreateScreen(navController) }
            composable("history_screen") { HistoryScreen(publicViewModel, navController) }
            composable("favorite_screen") { FavoriteScreen(publicViewModel, navController) }
            composable("settings_screen") { SettingsScreen(navController) }
            composable("buymecoffee_screen") { BuyMeCoffeeScreen(navController) }
            composable("about_screen") { AboutScreen(navController) }
            composable(route = "welcome_screen") { WelcomeScreen() }
            composable(route = "result_screen") {
                ScannedResultScreen(
                    navController = navController,
                    publicViewModel = publicViewModel
                )
            }
        }
    }
}

@Composable
private fun DrawerItem(
    item: DrawerItemInfo,
    isSelected: Boolean,
    navController: NavController,
    onClose: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF2A2A2D) else Color.Transparent
    val contentColor = if (isSelected) Color.White else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(
                elevation = if (isSelected) 10.dp else 0.dp,
                shape = RoundedCornerShape(50),
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(50)
            )
            .clickable {
                navController.navigate(item.route) {
                    launchSingleTop = true
                    restoreState = true
                }
                onClose()
            }
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            modifier = Modifier.size(26.dp),
            colorFilter = ColorFilter.tint(contentColor)
        )

        Text(
            text = item.title,
            color = contentColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 18.dp)
        )
    }
}
