package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.usersUseCases.FindUserUseCase
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FindUserUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var finsUserUseCase: FindUserUseCase

    @BeforeEach
    fun setup() {
        finsUserUseCase = FindUserUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `findUser should return user when found`() {
        // Given
        val users = listOf(
            User( Uuid.random(), "User1", Role.ADMIN),
            User( Uuid.random(), "User2", Role.MATE),
            User( Uuid.random(), "User3", Role.MATE)
        )

        every { repository.getAllUsers() } returns users

        // When
        val actual = finsUserUseCase.findUser("User1")

        // Then
        assertEquals(users[0], actual, "UseCase must return exactly what the repository returns")
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `findUsers should throw an exception when user is not found`() {
        // Given
        val users = listOf(
            User( Uuid.random(), "User1", Role.ADMIN),
            User( Uuid.random(), "User2", Role.MATE),
            User( Uuid.random(), "User3", Role.MATE)
        )

        every { repository.getAllUsers() } returns users

        // When & Then
        assertThrows<Exception> {
            finsUserUseCase.findUser("User10")
        }
    }

    @Test
    fun `findUsers should throw an exception when list of users is empty`() {
        // Given
        every { repository.getAllUsers() } returns emptyList()

        // When & Then
        assertThrows<Exception> {
            finsUserUseCase.findUser("User1")
        }
    }

}