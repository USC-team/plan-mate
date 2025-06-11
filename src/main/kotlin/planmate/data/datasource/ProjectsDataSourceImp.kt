package planmate.data.datasource

import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.ProjectDto
import planmate.data.repository.datasource.ProjectsDataSource


class ProjectsDataSourceImp (
    private val csvFileHandler: CsvFileHandler,
) : ProjectsDataSource {

    private val header = arrayOf("id", "name")
    private val filename = "projects.csv"

    override fun getAllProjects(): List<ProjectDto> {
        return csvFileHandler.readAllLines()
            .map { line ->
                ProjectDto(
                    id = line[0],
                    name = line[1],
                )
            }
    }

    override fun createProject(project: ProjectDto) {
        val projectRow = arrayOf(project.id.toString(), project.name)

        csvFileHandler.appendLine(headerColumns = header, newRow = projectRow)
    }

    override fun updateProject(project: ProjectDto) {
        csvFileHandler.updateLine(
            headerColumns = arrayOf("id", "name"),
            updatedRow = arrayOf(project.id.toString(), project.name)
        )
    }

    override fun deleteProject(projectId: String) {
        csvFileHandler.deleteLine(
            headerColumns = arrayOf("id", "name"),
            rowIdToDelete = projectId
        )
    }
}