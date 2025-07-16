package planmate.domain.usecase.statesUseCases

import planmate.domain.models.State
import planmate.domain.repository.StatesRepository

class CreateStateUseCase(private val repo: StatesRepository) {
     fun createState(state: State) = repo.createState(state)
}