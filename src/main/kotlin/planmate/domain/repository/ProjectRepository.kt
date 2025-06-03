package planmate.domain.repository

import planmate.domain.models.Project

interface ProjectRepository {
    fun getAllProjects()
    fun saveProject(project:Project)
}