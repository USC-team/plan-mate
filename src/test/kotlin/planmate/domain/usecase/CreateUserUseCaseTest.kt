package planmate.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.models.User
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.exceptions.InvalidRoleException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateUserUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var createUserUseCase: CreateUserUseCase

    @BeforeEach
    fun setup() {
        createUserUseCase = CreateUserUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createUser should succeed when user is admin and name is not empty`() {
        // Given
        val creator = User(id = Uuid.random(),name="Ala", role = `User.Role`.ADMIN)
        val user = User(id =  Uuid.random(), name = "New User", role = `User.Role`.MATE)

        every { repository.createUser(user) } returns Unit

        // When
        createUserUseCase.createUser(user, creator)

        // Then
        verify(exactly = 1) { repository.createUser(user) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createUser should throw InvalidRoleException when user is not admin`() {
        // Given
        val creator = User(id =  Uuid.random(),name="Ala", role = `User.Role`.MATE)
        val user = User(id =  Uuid.random(), name = "New User", role = `User.Role`.MATE)

        // When & Then
        assertThrows<InvalidRoleException> {
            createUserUseCase.createUser(user, creator)
        }
    }
}