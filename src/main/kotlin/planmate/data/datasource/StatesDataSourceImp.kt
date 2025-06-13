package planmate.data.datasource

import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.StateDto
import planmate.data.repository.datasource.StatesDataSource
import kotlin.uuid.ExperimentalUuidApi

class StatesDataSourceImp(
    private val csvFileHandler: CsvFileHandler,
) : StatesDataSource {

    private val header = arrayOf("id", "name", "projectId")

    override fun getAllStates(projectId: String): List<StateDto> = try {
        csvFileHandler.readAllLines()
            .drop(1)
            .filter { cols ->
                cols.size >= 3 && cols[2] == projectId
            }
            .map { cols ->
                StateDto(
                    id = cols[0],
                    name = cols[1],
                    projectId = cols[2]
                )
            }
    } catch (e: Exception) {
        throw Exception("Failed to read states CSV for project $projectId", e)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createState(state: StateDto) {

        try {
            val filename = "states_project_${state.projectId}.csv"

            val stateRow = arrayOf(state.id, state.name, state.projectId)

            csvFileHandler.appendLine(headerColumns = header, newRow = stateRow)
        } catch (e: Exception) {
            throw Exception(
                "Failed to append new State '${state.id}' to CSV for project ${state.projectId}",
                e
            )
        }
    }

    override fun updateState(state: StateDto) {
        try {
            csvFileHandler.updateLine(
                headerColumns = header,
                updatedRow = arrayOf(state.id, state.name, state.projectId)
            )
        } catch (e: Exception) {
            throw Exception(
                "Failed to update State '${state.id}' in CSV for project ${state.projectId}",
                e
            )
        }
    }

    override fun deleteState(stateId: String) {
        try {

            csvFileHandler.deleteLine(
                headerColumns = header,
                rowIdToDelete = stateId
            )
        } catch (e: Exception) {
            throw Exception("Failed to delete State '$stateId' from CSV", e)
        }
    }
}