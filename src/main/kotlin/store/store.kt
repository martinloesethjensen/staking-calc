package store

import UserSession
import factory.TokenType
import models.*
import utils.*

class StakingStore {
    // Holds staked assets for user and has a pair for each token that contains
    // info about amount staked and rewards amount in that token.
    private val store = HashMap<String, HashMap<TokenType, Pair<Amount, RewardAmount>>>()

    // Imitate a user base for the "authentication"
    private val userStore = mutableListOf("Alice", "Bob", "Charlie")

    fun authenticate(user: User) = userStore.contains(user.name)

    fun addFundsToStake(user: User, token: TokenType, amount: Amount) {
        if (amount.isNaN()) throw NumberFormatException()

        // Check if the user has the token that the user wants to stake
        if (!user.hasToken(token))
            throw NoTokens("There's no token: ${token.toSimpleName()} in the wallet")

        // Check if user has enough funds of token
        if (!user.hasFunds(token, amount))
            throw InsufficientFunds(
                "User has ${user.fundsFromToken(token)} in funds, " +
                        "but amount was: $amount"
            )

        val apy = when (token) {
            TokenType.DOT -> DOT_APY
            TokenType.KSM -> KSM_APY
            TokenType.ETH -> ETH_APY
            TokenType.UNKNOWN -> 0.0
        }

        // Put in store
        store[user.name] = hashMapOf(token to Pair(amount, amount * (apy / 100)))

        // Subtract amount from wallet
        val remaining = user.wallet[token]?.minus(amount)

        // Replace the token value with the updated remaining
        UserSession.updateWalletFunds(token, remaining)

        """
            ------------------------------------------------------------
            User: ${user.name}
                Staked $amount in ${token.toSimpleName()} with $apy% APY
                Estimated rewards:
                    1 month  = ${calculateReward(amount, apy, 1)}
                    6 months = ${calculateReward(amount, apy, 6)}
                    1 year   = ${calculateReward(amount, apy)}
                    3 years  = ${calculateReward(amount, apy, 36)} 
            ------------------------------------------------------------
        """.trimIndent().print()
    }

    private fun calculateReward(amount: Double, apy: Double, months: Int = 12): Double {
        return (amount * (apy / 100) / 12) * months
    }

    // TODO:
    // Check if user has rewards
    fun getRewards(user: User, token: TokenType): Rewards {
        if (!user.hasRewards(token))
            throw NoRewards("There's no rewards to redeem for token: ${token.toSimpleName()}")

        when (token) {
            TokenType.DOT -> {

            }
            TokenType.KSM -> TODO()
            TokenType.ETH -> TODO()
            TokenType.UNKNOWN -> Rewards.empty
        }
        return Rewards.empty
    }

    // Returns true if user has rewards in a token
    private fun User.hasRewards(token: TokenType) = store[this.name]!![token]!!.second >= 0

    fun commit() = println("Completed transaction: $store")
}

// Facade
class StakingRepository {
    private val store = StakingStore()

    fun stake(user: User, token: TokenType, amount: Double) {
        if (store.authenticate(user)) {
            store.addFundsToStake(user, token, amount)
            store.commit()
        } else throw NonExistingUser("User does not exist")
    }

    // TODO
    fun redeem(user: User, token: TokenType): Rewards {
        if (store.authenticate(user)) {
            return store.getRewards(user, token)
        } else {
            throw NonExistingUser("User does not exist")
        }
    }
}
