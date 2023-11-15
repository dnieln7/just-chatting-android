package xyz.dnieln7.navigation.utils

import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import xyz.dnieln7.navigation.NavArgsDestination
import xyz.dnieln7.navigation.NavDestination

class CompareRouteToDestinationTest {

    @Test
    fun `GIVEN a matching route WHEN compareRouteToDestination THEN return true`() {
        val route = "just-chatting/home"
        val destination = buildDestination("just-chatting/home")

        val result = compareRouteToDestination(route, destination)

        result.shouldBeTrue()
    }

    @Test
    fun `GIVEN a not matching route WHEN compareRouteToDestination THEN return false`() {
        val route = "just-chatting/home"
        val destination = buildDestination("just-chatting/login")

        val result = compareRouteToDestination(route, destination)

        result.shouldBeFalse()
    }

    @Test
    fun `GIVEN a matching long route WHEN compareRouteToDestination THEN return true`() {
        val route = "just-chatting/signup/register"
        val destination = buildDestination("just-chatting/signup/register")

        val result = compareRouteToDestination(route, destination)

        result.shouldBeTrue()
    }

    @Test
    fun `GIVEN a not matching long route WHEN compareRouteToDestination THEN return false`() {
        val route = "just-chatting/signup/register"
        val destination = buildDestination("just-chatting/signup/verification")

        val result = compareRouteToDestination(route, destination)

        result.shouldBeFalse()
    }

    @Test
    fun `GIVEN a matching arg route WHEN compareRouteToDestination THEN return true`() {
        val route = "just-chatting/chats/pictures/{id}"
        val destination = buildArgsDestination("just-chatting/chats/pictures")

        val result = compareRouteToDestination(route, destination)

        result.shouldBeTrue()
    }

    @Test
    fun `GIVEN a not matching arg route WHEN compareRouteToDestination THEN return false`() {
        val route = "just-chatting/chats/pictures/{id}"
        val destination = buildArgsDestination("just-chatting/chats/videos")

        val result = compareRouteToDestination(route, destination)

        result.shouldBeFalse()
    }

    private fun buildDestination(route: String): NavDestination {
        return object : NavDestination(route) {}
    }

    private fun buildArgsDestination(route: String): NavArgsDestination {
        val args = listOf(
            navArgument("id") { type = NavType.IntType },
        )

        return object : NavArgsDestination(route, args) {}
    }
}