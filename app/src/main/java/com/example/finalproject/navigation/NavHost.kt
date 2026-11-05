package com.example.finalproject.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.finalproject.component.BottomBar
import com.example.finalproject.component.MainScaffold
import com.example.finalproject.model.BottomNavItem
import com.example.finalproject.model.SessionState
import com.example.finalproject.model.SessionViewModel
import com.example.finalproject.page.auth.login.LoginScreen
import com.example.finalproject.page.auth.login.LoginViewModel
import com.example.finalproject.page.auth.register.RegisterScreen
import com.example.finalproject.page.auth.register.RegisterViewModel
import com.example.finalproject.page.category.CategoryScreen
import com.example.finalproject.page.category.CategoryViewModel
import com.example.finalproject.page.product.detail.ProductDetailScreen
import com.example.finalproject.page.product.detail.ProductDetailUiEvent
import com.example.finalproject.page.product.detail.ProductDetailViewModel
import com.example.finalproject.page.product.form.ProductFormScreen
import com.example.finalproject.page.product.form.ProductFormUiEvent
import com.example.finalproject.page.product.form.ProductFormViewModel
import com.example.finalproject.page.product.list.ProductListScreen
import com.example.finalproject.page.product.list.ProductListUiEvent
import com.example.finalproject.page.product.list.ProductListViewModel

@Composable
fun AppNavHost(
    sessionViewModel: SessionViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val sessionState by sessionViewModel.sessionState.collectAsStateWithLifecycle()
    val actions = rememberNavActions(navController)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    SessionNavEffect(
        navController = navController,
        sessionState = sessionState
    )

    val isCategory = currentDestination?.hasRoute<NavRoute.Category>() == true
    val isProductList = currentDestination?.hasRoute<NavRoute.ProductList>() == true
    val isProductDetail = currentDestination?.hasRoute<NavRoute.ProductDetail>() == true

    val showBottomBar = isCategory || isProductList || isProductDetail

    val bottomItems = when {
        isCategory -> listOf(
            BottomNavItem("Categorías", Icons.Default.Category, "category"),
            BottomNavItem("Logout", Icons.AutoMirrored.Filled.ExitToApp, "logout")
        )
        isProductList || isProductDetail -> listOf(
            BottomNavItem("Categorías", Icons.Default.Category, "category"),
            BottomNavItem("Productos", Icons.Default.ShoppingCart, "products"),
            BottomNavItem("Logout", Icons.AutoMirrored.Filled.ExitToApp, "logout")
        )
        else -> emptyList()
    }

    val selectedIndex = if (isCategory) 0 else 1

    MainScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {},
        bottomBar = {
            if (showBottomBar && bottomItems.isNotEmpty()) {
                BottomBar(
                    items = bottomItems,
                    selectIndex = selectedIndex,
                    onItemSelected = { index ->
                        when (bottomItems[index].route) {
                            "category" -> actions.toCategory()
                            "products" -> {
                                if (isProductDetail) actions.up()
                            }
                            "logout" -> sessionViewModel.logout()
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.Login,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable<NavRoute.Login> {
                val vm: LoginViewModel = hiltViewModel()
                val state by vm.uiState.collectAsStateWithLifecycle()

                LoginScreen(
                    uiState = state,
                    onEvent = vm::onEvent,
                    onNavigateToRegister = { actions.toRegister() },
                    onLoginSuccess = { actions.toCategory() }
                )
            }

            composable<NavRoute.Register> {
                val vm: RegisterViewModel = hiltViewModel()
                val state by vm.uiState.collectAsStateWithLifecycle()

                RegisterScreen(
                    uiState = state,
                    onEvent = vm::onEvent,
                    onNavigateToLogin = { actions.up() }
                )
            }

            composable<NavRoute.Category> {
                val vm: CategoryViewModel = hiltViewModel()
                val state by vm.uiState.collectAsStateWithLifecycle()

                CategoryScreen(
                    uiState = state,
                    onEvent = vm::onEvent,
                    onNavigateToCategoryProducts = { id, name ->
                        actions.toProductList(id, name)
                    }
                )
            }

            composable<NavRoute.ProductList> { backStackEntry ->
                val vm: ProductListViewModel = hiltViewModel()
                val state by vm.uiState.collectAsStateWithLifecycle()
                val route = backStackEntry.toRoute<NavRoute.ProductList>()
                val currentUserId = (sessionState as? SessionState.LoggedIn)?.user?.idUser ?: ""

                LaunchedEffect(route) {
                    vm.onEvent(
                        ProductListUiEvent.LoadProducts(
                            idUser = currentUserId,
                            idCategory = route.idCategory,
                            catName = route.catName
                        )
                    )
                }

                ProductListScreen(
                    uiState = state,
                    onEvent = vm::onEvent,
                    onProductClick = { product ->
                        actions.toProductDetail(product.idProduct)
                    },
                    onAddProductClick = { catId ->
                        actions.toProductForm(catId)
                    }
                )
            }

            composable<NavRoute.ProductDetail> { backStackEntry ->
                val vm: ProductDetailViewModel = hiltViewModel()
                val state by vm.uiState.collectAsStateWithLifecycle()
                val route = backStackEntry.toRoute<NavRoute.ProductDetail>()
                val currentUserId = (sessionState as? SessionState.LoggedIn)?.user?.idUser ?: ""

                LaunchedEffect(route.idProduct) {
                    vm.onEvent(ProductDetailUiEvent.LoadProduct(route.idProduct, currentUserId))
                }

                ProductDetailScreen(
                    uiState = state,
                    onEvent = vm::onEvent,
                    onNavigateBack = { actions.up() },
                    onNavigateToEdit = { product ->
                        actions.toProductForm(product.idCategory, product.idProduct)
                    }
                )
            }

            composable<NavRoute.ProductForm> { backStackEntry ->
                val vm: ProductFormViewModel = hiltViewModel()
                val state by vm.uiState.collectAsStateWithLifecycle()
                val route = backStackEntry.toRoute<NavRoute.ProductForm>()
                val currentUserId = (sessionState as? SessionState.LoggedIn)?.user?.idUser ?: ""

                LaunchedEffect(route.idProduct) {
                    vm.onEvent(
                        ProductFormUiEvent.InitForm(
                            idCategory = route.idCategory,
                            idUser = currentUserId,
                            idProduct = route.idProduct
                        )
                    )
                }

                ProductFormScreen(
                    uiState = state,
                    onEvent = vm::onEvent,
                    onNavigateBack = { actions.up() }
                )
            }
        }
    }
}