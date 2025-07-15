package planmate.data.datasource

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.UserDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class UserDataSourceImpTest {

    private val mockCsvHandler = mockk<CsvFileHandler>(relaxed = true)
    private val dataSource = UserDataSourceImp(mockCsvHandler)

    private val header = arrayOf("id", "name","role")

    @Test
    fun `getAllUsers should return correct DTO list when CSV contains users`() {
        //Given
        val user1 = arrayOf("1", "user1","ADMIN")
        val user2 = arrayOf("2", "user2","MATE")
        val csvLines = listOf(header, user1, user2)

        every { mockCsvHandler.readAllLines() } returns csvLines

        val expected = listOf(
            UserDto("1", "user1","ADMIN"),
            UserDto("2", "user2","MATE")
        )

        //When
        val result = dataSource.getAllUsers().drop(1).map { UserDto(it.id, it.name, it.role) }

        //Then
        assertThat(result).containsExactlyElementsIn(expected)
    }

    @Test
    fun `getAllUsers throws IO exception when csv file is empty`() {
        // Given
        every { mockCsvHandler.readAllLines() } throws RuntimeException("I/O error")

        // When & Then
        assertThrows<Exception> {
            dataSource.getAllUsers()
        }
    }

    @Test
    fun `createUser should appendLine when correct header and row are passed`() {
        // Given
        val userDto = UserDto(
            "1",
            "User1",
            "ADMIN"
        )

        // When
        dataSource.createUser(userDto)

        // Then
        val expectedRow = arrayOf(userDto.id, userDto.name, userDto.role)
        verify(exactly = 1) {
            mockCsvHandler.appendLine(
                headerColumns = header,
                newRow = expectedRow
            )
        }
    }

    @Test
    fun `createUser throws exception when the disk is full`() {
        // Given
        val userDto = UserDto(
            "1",
            "User1",
            "ADMIN"
        )
        every {
            mockCsvHandler.appendLine(
                headerColumns = header,
                newRow = arrayOf(userDto.id, userDto.name, userDto.role)
            )
        } throws RuntimeException("Disk full")

        // When && Then
        assertThrows<Exception> { dataSource.createUser(userDto) }
    }

    @Test
    fun `updateUser should updateLine with correct parameters`() {
        // Given
        val userDto = UserDto(
            "1",
            "User1",
            "ADMIN"
        )

        // When
        dataSource.updateUser(userDto)

        // Then
        val expectedUpdatedRow = arrayOf(userDto.id, userDto.name, userDto.role)
        verify(exactly = 1) {
            mockCsvHandler.updateLine(
                headerColumns = header,
                updatedRow = expectedUpdatedRow
            )
        }
    }

    @Test
    fun `updateUser throws exception when a write exception happens`() {
        // Given
        val userDto = UserDto(
            "1",
            "User1",
            "ADMIN"
        )
        every {
            mockCsvHandler.updateLine(
                headerColumns = header,
                updatedRow = arrayOf(userDto.id, userDto.name, userDto.role)
            )
        } throws RuntimeException("Write error")

        // When && Then
        assertThrows<Exception> { dataSource.updateUser(userDto) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteUser should deleteLine when correct parameters`() {
        // Given
        val userId = random()

        // When
        dataSource.deleteUser(userId)

        // Then
        verify(exactly = 1) {
            mockCsvHandler.deleteLine(
                headerColumns = header,
                rowIdToDelete = userId.toString()
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteUser throws exception when delete fails`() {
        // Given
        val userId = random()
        every {
            mockCsvHandler.deleteLine(
                headerColumns = header,
                rowIdToDelete = userId.toString()
            )
        } throws RuntimeException("Delete failure")

        // When && Then
        assertThrows<Exception> { dataSource.deleteUser(userId) }
    }


}