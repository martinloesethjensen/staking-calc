package store

import UserSession
import alice
import bob
import charlie
import factories.APYFactory
import models.*
import utils.*

class StakingStore {
    // Holds staked assets for user and has a pair for each token that contains
    // info about amount staked and rewards amount in that token.
    private val store = HashMap<String, HashMap<TokenType, Pair<Amount, RewardAmount>>>()

    // Imitate a user base for the "authentication"
    private val userStore = mutableListOf(alice, bob, charlie)

    fun User.authenticate() = userStore.contains(this)

    fun addFundsToStake(user: User, token: TokenType, amount: Amount) {
        if (!user.authenticate()) throw NonExistingUser("User does not exist")
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

        if (token.isNullOrUnknown()) throw UnknownToken("Token is unknown!")


        // Using factory to get APY for a token
        val apy = APYFactory.apyFromToken(token)

        // This will populate user and user's tokens if they are not present in the store.
        // It provides default values that can be used for compounding interest
        val tokenInStore = store
            .getOrPut(user.name) { hashMapOf(token to Pair(0.0, 0.0)) }
            .getOrPut(token) { Pair(0.0, 0.0) }

        // When user stakes more assets in a token that is already staked and in store.
        // Then it should compound the rewards to claim and add it to the amount (.first)
        val compoundAmount = tokenInStore.let {
            amount + it.first + it.second
        }

        // Put in store
        store[user.name]?.put(token, Pair(compoundAmount, compoundAmount * (apy / 100)))

        // Get the amount that should be the subtracted amount from the user wallet
        val remaining = user.wallet[token]?.minus(amount)

        // Replace the token value in the user's wallet with the updated remaining of token
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

    // Calculates the rewards depending on the months
    private fun calculateReward(amount: Double, apy: Double, months: Int = 12): Double {
        return (amount * (apy / 100) / 12) * months
    }

    // TODO:
    // Adds rewards to user's wallet
    fun claimRewards(user: User, token: TokenType) {
        if (!user.authenticate()) throw NonExistingUser("User does not exist")
        if (!user.hasRewards(token)) throw NoRewards("There's no rewards to redeem for token: ${token.toSimpleName()}")
        if (token.isNullOrUnknown()) throw UnknownToken("Token is unknown!")

        // Get the amount in rewards
        // .first is the staked amount
        // .second is the rewards to claim
        val stakedAmount = store[user.name]?.get(token)?.first as Double
        val rewardsAmount = store[user.name]?.get(token)?.second

        // Update store rewards for the user in a token that is staked
        store[user.name]?.put(token, Pair(stakedAmount, 0.0))

        // Add the claimed rewards to the user's wallet for that token
        UserSession.updateWalletFunds(token, rewardsAmount)

        println(store)
    }

    // Returns true if user has rewards in a token
    private fun User.hasRewards(token: TokenType) = store[this.name]!![token]!!.second >= 0
}

// Facade
class StakingRepository {
    private val store = StakingStore()

    fun stake(user: User, token: TokenType, amount: Double) {
        store.addFundsToStake(user, token, amount)
    }

    fun claimRewards(user: User, token: TokenType) {
        store.claimRewards(user, token)
    }
}
