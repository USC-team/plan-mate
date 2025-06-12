package planmate.domain.usecase

import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.exceptions.InvalidRoleException

class CreateUserUseCase(private val repo: UsersRepository) {

    fun createUser(user: User, creator:User){
        if (creator.role!=ADMIN)
            throw InvalidRoleException()
        else
            repo.createUser(user)
    }
    companion object{
        private val ADMIN= Role.ADMIN
    }
}