package planmate.data.repository.datasource

import planmate.data.dto.StateDto
import kotlin.uuid.ExperimentalUuidApi

interface ProjectsDataSource {

    @OptIn(ExperimentalUuidApi::class)
    fun getAllProjects(): List<StateDto>

}