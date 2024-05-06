import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import com.example.shoppingl_list_app.ui.theme.Route
import com.example.shoppingl_list_app.ui.theme.details.DetailScreen
import com.example.shoppingl_list_app.ui.theme.home.HomeScreen

@Composable
fun ShoppingListNavigation(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navHostController, startDestination = Route.Home.route) {
        composable(Route.Home.route) {
            HomeScreen(onNavigate = { id ->
                navHostController.navigate("${Route.Detail.route}?id=$id")
            })
        }
        composable(
            route = "${Route.Detail.route}?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val id = arguments.getInt("id")
            DetailScreen(id = id) {
                navHostController.navigateUp()
            }
        }
    }
}
