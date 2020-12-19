import models.TokenType
import models.User

// Dummy users
val alice = User(
    "Alice", wallet = hashMapOf(
        TokenType.KSM to 5.0,
        TokenType.DOT to 75.0,
        TokenType.ETH to 3.0
    )
)
val bob = User(
    "Bob", wallet = hashMapOf(
        TokenType.KSM to 3.0,
        TokenType.DOT to 25.0,
        TokenType.ETH to 1.0
    )
)
val charlie = User(
    "Charlie", wallet = hashMapOf(
        TokenType.KSM to 1.0,
        TokenType.DOT to 150.0,
        TokenType.ETH to 0.5
    )
)
val dave = User("Dave", wallet = hashMapOf())
val eve = User("Eve", wallet = hashMapOf())

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