package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.models.User
import planmate.domain.models.Role
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.exceptions.InvalidRoleException
import planmate.domain.usecase.usersUseCases.CreateUserUseCase

class CreateUserUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var createUserUseCase: CreateUserUseCase

    @BeforeEach
    fun setup() {
        createUserUseCase = CreateUserUseCase(repository)
    }

    @Test
    fun `createUser should succeed when user is admin and name is not empty`() {
        // Given
        val creator = User(id = "1",name="Ala", role = Role.ADMIN)
        val user = User(id = "2", name = "New User", role = Role.MATE)

        every { repository.createUser(user) } returns Unit

        // When
        createUserUseCase.createUser(user, creator)

        // Then
        verify(exactly = 1) { repository.createUser(user) }
    }

    @Test
    fun `createUser should throw InvalidRoleException when user is not admin`() {
        // Given
        val creator = User(id = "1",name="Ala", role = Role.MATE)
        val user = User(id = "2", name = "New User", role = Role.MATE)

        // When & Then
        assertThrows<InvalidRoleException> {
            createUserUseCase.createUser(user, creator)
        }
    }
}