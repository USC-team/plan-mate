package planmate.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import planmate.data.dto.ProjectDto
import planmate.data.repository.datasource.ProjectsDataSource
import planmate.domain.models.Project
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid.Companion.random

class ProjectsRepositoryImpTest {
    private val mockProjectsDataSource = mockk<ProjectsDataSource>(relaxed = true)
    private val repo = ProjectsRepositoryImp(mockProjectsDataSource)

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getAllProjects should return all projects when data source returns DTOs`() {
        // Given
        val dto1 = ProjectDto(
            id = random().toString(),
            name = "Project1",
        )
        val dto2 = ProjectDto(
            id = random().toString(),
            name = "Project2",
        )
        every { mockProjectsDataSource.getAllProjects() } returns listOf(dto1, dto2)

        // When
        val result = repo.getAllProjects()

        // Then
        assertThat(result).containsExactlyElementsIn(listOf(dto1.toDomain(), dto2.toDomain()))
    }

    @Test
    fun `getAllProjects should throw exception when data source fails`() {
        // Given
        every { mockProjectsDataSource.getAllProjects() } throws RuntimeException("I/O error")

        // When && Then
        assertThrows<Exception> { repo.getAllProjects() }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should delegate to data source with correct DTO`() {
        // Given
        val projectId = random()
        val project = Project(id = projectId, name = "Project1")

        // When
        repo.createProject(project)

        // Then
        verify(exactly = 1) {
            mockProjectsDataSource.createProject(
                match { dto ->
                    dto.id == projectId.toString() &&
                            dto.name == "Project1"
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `createProject should wrap exception when data source fails`() {
        // Given
        val project = Project(id = random(), name = "Project1")
        every { mockProjectsDataSource.createProject(any()) } throws RuntimeException("Disk full")

        // When & Then
        assertThrows<Exception> { repo.createProject(project) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateProject should delegate to data source with correct DTO`() {
        // Given
        val projectId = random()
        val project = Project(id = projectId, name = "Project1")

        // When
        repo.updateProject(project)

        // Then
        verify(exactly = 1) {
            mockProjectsDataSource.updateProject(
                match { dto ->
                    dto.id == projectId.toString() &&
                            dto.name == "Project1"
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `updateProject should wrap exception when data source fails`() {
        // Given
        val project = Project(id = random(), name = "Project1")
        every { mockProjectsDataSource.updateProject(any()) } throws RuntimeException("Error")

        // When & Then
        assertThrows<Exception> { repo.updateProject(project) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteProject should delegate to data source with correct ID`() {
        // Given
        val projectId = random()

        // When
        repo.deleteProject(projectId)

        // Then
        verify(exactly = 1) {
            mockProjectsDataSource.deleteProject(projectId.toString())
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `deleteProject should wrap exception when data source fails`() {
        // Given
        val stateId = random()
        every { mockProjectsDataSource.deleteProject(stateId.toString()) } throws RuntimeException("Delete failure")

        // When & Then
        assertThrows<Exception> { repo.deleteProject(stateId) }
    }
}