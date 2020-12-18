package models

class NonExistingUser(override val message: String) : Error(message)