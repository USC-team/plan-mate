package planmate.domain.usecase

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.User
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetAllUsersUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @BeforeEach
    fun setup() {
        getAllUsersUseCase = GetAllUsersUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllUsers should delegate to repository and return list of users`() {
        runTest {
            // Given
            val expectedUsers = listOf(
                User(Uuid.random(), "User1", User.Role.ADMIN),
                User(Uuid.random(), "User2", User.Role.MATE),
                User(Uuid.random(), "User3", User.Role.MATE)
            )

            coEvery { repository.getAllUsers() } returns expectedUsers

            // When
            val actual = getAllUsersUseCase.getAllUsers()

            // Then
            assertEquals(expectedUsers, actual, "UseCase must return exactly what the repository returns")
        }
    }

    @Test
    fun `getAllUsers should return empty list when repository returns empty`() {
        runTest {
            // Given
            coEvery { repository.getAllUsers() } returns emptyList()

            // When
            val actual = getAllUsersUseCase.getAllUsers()

            // Then
            assertEquals(emptyList(), actual)
        }
    }
}