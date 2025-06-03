package planmate.domain.repository

import planmate.domain.models.State

interface UpdateStateRepository {
    fun updateState(state: State)
}