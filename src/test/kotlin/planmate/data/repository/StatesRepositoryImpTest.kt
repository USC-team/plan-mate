package planmate.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.dto.StateDto
import planmate.data.repository.datasource.StatesDataSource
import planmate.domain.models.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.Uuid.Companion.random

class StatesRepositoryImpTest {
    private val mockStatesDataSource = mockk<StatesDataSource>(relaxed = true)
    private val repo = StatesRepositoryImp(mockStatesDataSource)

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllStates should return mapped domain States when data source returns DTOs`() {
        // Given
        val projectId = random()
        val dto1 = StateDto(
            id = projectId.toString(),
            name = "ToDo",
            projectId = projectId.toString()
        )
        val dto2 = StateDto(
            id = random().toString(),
            name = "Done",
            projectId = projectId.toString()
        )
        every { mockStatesDataSource.getAllStates(projectId.toString()) } returns listOf(dto1, dto2)

        // When
        val result = repo.getAllStates(projectId)

        // Then
        assertThat(result.map { it.name }).containsExactlyElementsIn(listOf("ToDo", "Done"))
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllStates should wrap exception when data source fails`() {
        // Given
        val projectId = random()
        every { mockStatesDataSource.getAllStates(projectId.toString()) } throws RuntimeException("I/O error")

        // When && Then
        assertThrows<Exception> { repo.getAllStates(projectId) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState should delegate to data source with correct DTO`() {
        // Given
        val stateId = Uuid.random()
        val projectId = Uuid.random()
        val state = State(id = stateId, name = "Review", projectId = projectId)

        // When
        repo.createState(state)

        // Then
        verify(exactly = 1) {
            mockStatesDataSource.createState(
                match { dto ->
                    dto.id == stateId.toString() &&
                            dto.name == "Review" &&
                            dto.projectId == projectId.toString()
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState should wrap exception when data source fails`() {
        // Given
        val state = State(id = random(), name = "Review", projectId = random())
        every { mockStatesDataSource.createState(any()) } throws RuntimeException("Disk full")

        // When & Then
        assertThrows<Exception> { repo.createState(state) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateState should delegate to data source with correct DTO`() {
        // Given
        val stateId = random()
        val projectId = random()
        val state = State(id = stateId, name = "InProgress", projectId = projectId)

        // When
        repo.updateState(state)

        // Then
        verify(exactly = 1) {
            mockStatesDataSource.updateState(
                match { dto ->
                    dto.id == stateId.toString() &&
                            dto.name == "InProgress" &&
                            dto.projectId == projectId.toString()
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateState should wrap exception when data source fails`() {
        // Given
        val state = State(id = random(), name = "InProgress", projectId = random())
        every { mockStatesDataSource.updateState(any()) } throws RuntimeException("Error")

        // When & Then
        assertThrows<Exception> { repo.updateState(state) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteState should delegate to data source with correct ID`() {
        // Given
        val stateId = random()

        // When
        repo.deleteState(stateId)

        // Then
        verify(exactly = 1) {
            mockStatesDataSource.deleteState(stateId.toString())
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteState should wrap exception when data source fails`() {
        // Given
        val stateId = random()
        every { mockStatesDataSource.deleteState(stateId.toString()) } throws RuntimeException("Delete failure")

        // When & Then
        assertThrows<Exception> { repo.deleteState(stateId) }
    }
}