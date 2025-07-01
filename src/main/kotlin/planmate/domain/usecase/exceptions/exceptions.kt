package planmate.domain.usecase.exceptions

class InvalidRoleException: Exception("The user is not an Admin")
class NameCantBeNullException :Exception("Name can't be null")
class UserNotFoundException: Exception("User is not found!")
class NoUsersFoundException: Exception("No Users are found!")