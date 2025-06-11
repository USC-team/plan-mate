package planmate.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.dto.UserDto
import planmate.data.repository.datasource.UsersDataSource
import planmate.domain.models.Role
import planmate.domain.models.User

class UsersRepositoryImpTest {
    private val mockUsersDataSource = mockk<UsersDataSource>(relaxed = true)
    private val repo = UsersRepositoryImp(mockUsersDataSource)

    @Test
    fun `getAllUsers should return all users when data source returns DTOs`() {
        // Given
        val dto1 = UserDto(
            id = "1",
            name = "User1",
            role = "ADMIN"
        )
        val dto2 = UserDto(
            id = "2",
            name = "User2",
            role = "MATE"
        )
        every { mockUsersDataSource.getAllUsers() } returns listOf(dto1, dto2)

        // When
        val result = repo.getAllUsers()

        // Then
        assertThat(result).containsExactlyElementsIn(listOf(dto1.toDomain(), dto2.toDomain()))
    }

    @Test
    fun `getAllUsers should throw exception when data source fails`() {
        // Given
        every { mockUsersDataSource.getAllUsers() } throws RuntimeException("I/O error")

        // When && Then
        assertThrows<Exception> { repo.getAllUsers() }
    }

    @Test
    fun `createUser should delegate to data source with correct DTO`() {
        // Given
        val userId = "1"
        val user = User(id = userId, name = "User1", role = Role.MATE)

        // When
        repo.createUser(user)

        // Then
        verify(exactly = 1) {
            mockUsersDataSource.createUser(
                match { dto ->
                    dto.id == userId &&
                            dto.name == "User1" &&
                            dto.role== Role.MATE.toString()
                }
            )
        }
    }

    @Test
    fun `createUser should wrap exception when data source fails`() {
        // Given
        val user = User(id = "1", name = "User1", role = Role.ADMIN)
        every { mockUsersDataSource.createUser(any()) } throws RuntimeException("Disk full")

        // When & Then
        assertThrows<Exception> { repo.createUser(user) }
    }
    @Test
    fun `createUser should throw exception when user name is empty`() {
        // Given
        val user = User(id = "1", name = "", role = Role.ADMIN)

        // When & Then
        assertThrows<Exception> { repo.createUser(user) }
    }

    @Test
    fun `updateUser should delegate to data source with correct DTO`() {
        // Given
        val userId = "1"
        val user = User(id = userId, name = "User1", role = Role.ADMIN)

        // When
        repo.updateUser(user)

        // Then
        verify(exactly = 1) {
            mockUsersDataSource.updateUser(
                match { dto ->
                    dto.id == userId &&
                            dto.name == "User1" &&
                            dto.role== Role.ADMIN.toString()
                }
            )
        }
    }

    @Test
    fun `updateUser should wrap exception when data source fails`() {
        // Given
        val user = User(id = "1", name = "User1",role = Role.ADMIN)
        every { mockUsersDataSource.updateUser(any()) } throws RuntimeException("Error")

        // When & Then
        assertThrows<Exception> { repo.updateUser(user) }
    }

    @Test
    fun `deleteUser should delegate to data source with correct ID`() {
        // Given
        val userId = "1"

        // When
        repo.deleteUser(userId)

        // Then
        verify(exactly = 1) {
            mockUsersDataSource.deleteUser(userId)
        }
    }

    @Test
    fun `deleteUser should wrap exception when data source fails`() {
        // Given
        val userId = "1"
        every { mockUsersDataSource.deleteUser(userId) } throws RuntimeException("Delete failure")

        // When & Then
        assertThrows<Exception> { repo.deleteUser(userId) }
    }

}