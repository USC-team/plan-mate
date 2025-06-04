package planmate.data.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.repository.datasource.StatesDataSource
import planmate.domain.models.Project
import planmate.domain.models.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class StatesRepositoryImpTest {
    private val mockCsvHandler = mockk<CsvFileHandler>(relaxed = true)
    private val mockStatesDataSource = mockk<StatesDataSource>()
    private val repo = StatesRepositoryImp(mockStatesDataSource)

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState should append new state when correct filename, header, and row`() {
        // Given
        val state = State(id = random(), name = "ToDo", projectId = random())
        val projectId = random()
//        every { mockProjectsRepo.getProjectById(projectId) } returns Project(random(), "")

        // When
        repo.createState(state)

        // Then
        verify(exactly = 1) {
            mockCsvHandler.appendLine(
                headerColumns = arrayOf("id", "name", "projectId"), newRow = arrayOf(
                    state.id.toString(),
                    state.name,
                    state.projectId.toString()
                )
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState should throw Exception when project does not exist`() {
        // Given
        val projectId = random()
        val state = State(id = random(), name = "Review", projectId = projectId)

//        every { mockProjectsRepo.getProjectById(projectId) } returns null

        // When
        assertThrows<Exception> {
            repo.createState(state)
        }

        // Then
        verify(exactly = 0) {
            mockCsvHandler.appendLine(any(), any())
        }
    }

}