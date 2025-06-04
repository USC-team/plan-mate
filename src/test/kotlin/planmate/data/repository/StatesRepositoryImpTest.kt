package planmate.data.repository

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import planmate.data.csvHandler.CsvFileHandler
import planmate.domain.models.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class StatesRepositoryImpTest {
    private val mockCsvHandler = mockk<CsvFileHandler>(relaxed = true)
    private val repo = StatesRepositoryImp(mockCsvHandler)

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createState should append new state When correct filename, header, and row`() {
        // Given
        val state = State(id = Uuid.random(), name = "ToDo", projectId = Uuid.random())

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

}