package planmate.domain.usecase.exceptions

class StateNotFoundException(message: String): Exception(message)
class InvalidRoleException: Exception("The user is not an Admin") {}
class NameCantBeNullException :Exception("Name can't be null"){}