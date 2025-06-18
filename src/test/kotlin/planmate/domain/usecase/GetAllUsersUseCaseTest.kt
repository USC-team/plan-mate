package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.Role
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
        // Given
        val expectedUsers = listOf(
            User(Uuid.random(), "User1", Role.ADMIN),
            User(Uuid.random(), "User2", Role.MATE),
            User(Uuid.random(), "User3", Role.MATE)
        )

        every { repository.getAllUsers() } returns expectedUsers

        // When
        val actual = getAllUsersUseCase.getAllUsers()

        // Then
        assertEquals(expectedUsers, actual, "UseCase must return exactly what the repository returns")
    }

    @Test
    fun `getAllUsers should return empty list when repository returns empty`() {
        // Given
        every { repository.getAllUsers() } returns emptyList()

        // When
        val actual = getAllUsersUseCase.getAllUsers()

        // Then
        assertEquals(emptyList(), actual)
    }
}