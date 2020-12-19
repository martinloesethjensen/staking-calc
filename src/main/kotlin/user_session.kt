import factory.TokenType
import models.User

object UserSession {
    var user: User? = null

    fun login(user: User) {
        this.user = user
    }

    fun logout() {
        this.user = null
    }

    fun updateWalletFunds(tokenType: TokenType, value: Double?) =
        value?.let { user?.wallet?.replace(tokenType, it) }
}