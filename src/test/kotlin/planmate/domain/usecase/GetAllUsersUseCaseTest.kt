package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.repository.UsersRepository
import kotlin.test.assertEquals

class GetAllUsersUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @BeforeEach
    fun setup() {
        getAllUsersUseCase = GetAllUsersUseCase(repository)
    }

    @Test
    fun `getAllUsers should delegate to repository and return list of users`() {
        // Given
        val expectedUsers = listOf(
            User("1", "User1", Role.ADMIN),
            User("2", "User2", Role.MATE),
            User("3", "User3", Role.MATE)
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