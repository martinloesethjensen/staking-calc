package models

class NonExistingUser(override val message: String) : Error(message)
class InsufficientFunds(override val message: String) : Error(message)
class NoTokens(override val message: String) : Error(message)
class NoRewards(override val message: String) : Error(message)
class UnknownToken(override val message: String) : Error(message)
