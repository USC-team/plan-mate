package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.usersUseCases.DeleteUserUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class DeleteUserUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @BeforeEach
    fun setup() {
        deleteUserUseCase = DeleteUserUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteUser should delegate to repository`() {
        // Given
        val userId = random()

        every { repository.deleteUser(userId) } returns Unit

        // When
        deleteUserUseCase.deleteUser(userId)

        // Then
        verify(exactly = 1) { repository.deleteUser(userId) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteUser should propagate exception if repository throws`() {
        // Given
        val userId = random()

        every { repository.deleteUser(userId) } throws Exception()

        // When && Then
        assertThrows<Exception> {
            deleteUserUseCase.deleteUser(userId)
        }
    }
}