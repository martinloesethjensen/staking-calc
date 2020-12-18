package store

import factory.TokenType
import models.Amount
import models.NonExistingUser
import models.Price
import models.Rewards
import models.User

class StakingStore {
    private val store = HashMap<String, Price>()

    // Imitate a user base for the "authentication"
    private val userStore = mutableListOf("Alice", "Bob", "Charlie")

    fun authenticate(user: User) = userStore.contains(user.name)

    fun addFundsToStake(token: TokenType, amount: Amount) {
        when (token) {
            TokenType.DOT -> TODO()
            TokenType.KSM -> TODO()
            TokenType.ETH -> TODO()
            TokenType.UNKNOWN -> Rewards.empty
        }
    }

    fun getRewards(token: TokenType): Rewards = when (token) {
        TokenType.DOT -> TODO()
        TokenType.KSM -> TODO()
        TokenType.ETH -> TODO()
        TokenType.UNKNOWN -> Rewards.empty
    }

    fun commit() = println("Completed transaction: $store")
}

// Facade
class StakingRepository {
    private val store = StakingStore()

    // TODO
    fun stake(user: User, token: TokenType, amount: Double) {
        if (store.authenticate(user)) {
            store.addFundsToStake(token, amount)
            store.commit()
        } else throw NonExistingUser("User does not exist")
    }

    // TODO
    fun redeem(user: User, token: TokenType): Rewards {
        if (store.authenticate(user)) {
            return store.getRewards(token)
        } else {
            throw NonExistingUser("User does not exist")
        }
    }
}
