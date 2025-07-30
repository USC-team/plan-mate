package planmate.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.domain.models.User
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.usersUseCases.UpdateUserUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class UpdateUserUseCaseTest {
    private var repository: UsersRepository = mockk(relaxed = true)
    private lateinit var updateUsersUseCase: UpdateUserUseCase

    @BeforeEach
    fun setup() {
        updateUsersUseCase = UpdateUserUseCase(repository)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateUser should update when repository updates successfully`() {
        runTest {
            // Given
            val userId = random()
            val user = User(userId, "User1", User.Role.ADMIN)

            coEvery { repository.updateUser(user) } returns Unit

            // When
            updateUsersUseCase.updateUser(user)

            // Then
            coVerify(exactly = 1) { repository.updateUser(user) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateUser should throw exception when repository throws`() {
        runTest {
            // Given
            val userId = random()
            val user = User(id = userId, "User1", User.Role.ADMIN)

            coEvery { repository.updateUser(user) } throws Exception()

            // When && Then
            assertThrows<Exception> {
                updateUsersUseCase.updateUser(user)
            }
        }
    }
}