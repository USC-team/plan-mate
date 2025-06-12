package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.repository.UsersRepository

class DeleteUserUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @BeforeEach
    fun setup() {
        deleteUserUseCase = DeleteUserUseCase(repository)
    }

    @Test
    fun `deleteUser should delegate to repository`() {
        // Given
        val userId = "1"

        every { repository.deleteUser(userId) } returns Unit

        // When
        deleteUserUseCase.deleteUser(userId)

        // Then
        verify(exactly = 1) { repository.deleteUser(userId) }
    }

    @Test
    fun `deleteUser should propagate exception if repository throws`() {
        // Given
        val userId = "1"

        every { repository.deleteUser(userId) } throws Exception()

        // When && Then
        assertThrows<Exception> {
            deleteUserUseCase.deleteUser(userId)
        }
    }
}