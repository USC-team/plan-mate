package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.repository.UsersRepository

class UpdateUserUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var updateUsersUseCase: UpdateUserUseCase

    @BeforeEach
    fun setup() {
        updateUsersUseCase = UpdateUserUseCase(repository)
    }

    @Test
    fun `updateUser should update when repository updates successfully`() {
        // Given
        val user = User("1", "User1", Role.ADMIN)

        every { repository.updateUser(user) }  returns Unit

        // When
        updateUsersUseCase.updateUser(user)

        // Then
        verify(exactly = 1) { repository.updateUser(user) }
    }

    @Test
    fun `updateUser should throw exception when repository throws`() {
        // Given
        val user = User("1", "User1", Role.ADMIN)

        every { repository.updateUser(user) } throws Exception()

        // When && Then
        assertThrows<Exception> {
            updateUsersUseCase.updateUser(user)
        }
    }
}