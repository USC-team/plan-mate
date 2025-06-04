package planmate.data.repository

import planmate.data.csvHandler.CsvFileHandler
import planmate.domain.models.Project
import planmate.domain.repository.ProjectRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid



class ProjectsRepositoryImp(
    private val csvFileHandler: CsvFileHandler,
) : ProjectRepository {

    private val header = arrayOf("id", "name")
    private val filename = "projects.csv"

    @OptIn(ExperimentalUuidApi::class)
    override fun getAllProjects(): List<Project> {
       return csvFileHandler.readAllLines()
            .map { line ->
                Project(
                    id = Uuid.parse(line[0]),
                    name = line[1],
                )
            }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun createProject(project: Project) {
        val projectRow = arrayOf(project.id.toString(), project.name)

        csvFileHandler.appendLine(headerColumns = header, newRow = projectRow)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun updateProject(project: Project) {
        csvFileHandler.updateLine(
            headerColumns = arrayOf("id", "name"),
            updatedRow = arrayOf(project.id.toString(), project.name)
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteProject(projectId: Uuid) {
        csvFileHandler.deleteLine(
            headerColumns = arrayOf("id", "name"),
            rowIdToDelete = projectId.toString()
        )
    }
}