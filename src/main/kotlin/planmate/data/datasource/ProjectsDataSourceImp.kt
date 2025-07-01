package planmate.data.datasource

import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.ProjectDto
import planmate.data.repository.datasource.ProjectsDataSource


class ProjectsDataSourceImp(
    private val csvFileHandler: CsvFileHandler,
) : ProjectsDataSource {

    private val header = arrayOf("id", "name", "userId")

    override fun getAllProjects(): List<ProjectDto> {
        return csvFileHandler.readAllLines()
            .map { line ->
                ProjectDto(
                    id = line[ID_INDEX],
                    name = line[NAME_INDEX],
                    userId = line[USERID_INDEX]
                )
            }
    }

    override fun createProject(project: ProjectDto) {
        val projectRow = arrayOf(project.id.toString(), project.name, project.userId)

        csvFileHandler.appendLine(headerColumns = header, newRow = projectRow)
    }

    override fun updateProject(project: ProjectDto) {
        csvFileHandler.updateLine(
            headerColumns = header,
            updatedRow = arrayOf(project.id.toString(), project.name, project.userId)
        )
    }

    override fun deleteProject(projectId: String) {
        csvFileHandler.deleteLine(
            headerColumns = header,
            rowIdToDelete = projectId
        )
    }

    companion object{
        private const val ID_INDEX=0
        private const val NAME_INDEX=1
        private const val USERID_INDEX=2
    }
}