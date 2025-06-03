package planmate.domain.repository

import planmate.domain.models.State

interface CreateStateRepository {
    fun createState(state: State)
}