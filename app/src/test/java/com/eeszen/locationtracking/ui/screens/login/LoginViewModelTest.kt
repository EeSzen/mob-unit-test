package com.eeszen.locationtracking.ui.screens.login

import com.eeszen.locationtracking.data.repo.IUserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    val repo: IUserRepo = mock()
    val viewModel = LoginViewModel(repo)

    @Before
    fun setup(){
        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        whenever(repo.getUser()).thenReturn("snickers")
    }

    @After
    fun cleanup(){
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchUser returns the mock value`(){
        assertEquals(expected = "snickers", actual = viewModel.fetchUser())
    }

    @Test
    fun `greetings should return hello mocked_value`(){
        assertEquals("Hello snickers",viewModel.greetings())
    }

    @Test
    fun `fetchUser should update the greetings stateflow with greetings`() = runTest {
        viewModel.fetchUser()
        val greetings = viewModel.greetings.drop(1).first()
        assertEquals("Hello snickers",greetings)
    }

    @Test
    fun `greet function should update the greetings stateflow with Hello $name`() = runTest {
        viewModel.greet("snickers")
        val msg = viewModel.greetings.drop(1).first()
        assertEquals("Hello snickers", msg, "Extra info about test")
    }

    @Test
    fun `Validation should fail for email and password`(){
        assert(viewModel.validate("email","password")!= null)
    }

    @Test
    fun `Validation should fail for email@a,com and pass`(){
        assert(viewModel.validate("email@a.com","pass") != null)
    }

    @Test
    fun `Validation should pass for email@a,com and password`(){
        assert(viewModel.validate("email@a.com","password") == null)
    }

    @Test
    fun `Validation should pass for email@gmail,com and password`(){
        assert(viewModel.validate("email@gmail.com","password") == null)
    }

    // LOGIN
    @Test
    fun `Login failed cuh , nub`() = runTest{
        viewModel.login(email = "test@a.com", password = "123456789")
        val test = viewModel.finish.first()
        assertEquals(
            message = "Login fail",
            expected = test,
            actual = "bro u failed"
        )
    }
}