package commands

import factories.ProtocolFactory
import models.TokenType
import models.User
import store.StakingRepository

class StakingAddFundsCommand(
    private val user: User,
    private val tokenType: TokenType,
    private val amount: Double,
    private val stakingRepository: StakingRepository
) : StakingCommand {
    override fun execute() {
        val protocol = ProtocolFactory.protocolFromToken(tokenType)
        val token = protocol.token
        println("Staking ${protocol.name} (${token.id}) with amount: $amount")
        println("Current price for 1 ${token.id} is $${token.price}")
        stakingRepository.stake(user, tokenType, amount)
    }
}

/**
 * Redeems staking rewards for a chosen token.
 * It will look in the store if user has redeemable tokens
 */
class StakingClaimRewardCommand(
    private val user: User,
    private val tokenType: TokenType,
    private val stakingRepository: StakingRepository
) : StakingCommand {
    override fun execute() {
        println("Claiming your rewards...")
        stakingRepository.claimRewards(user, tokenType)
    }
}