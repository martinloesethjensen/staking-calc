package commands

import factories.ProtocolFactory
import models.TokenType
import models.User

class StakingAddCommand(private val user: User, private val tokenType: TokenType, private val amount: Double) :
    StakingCommand {
    override fun execute() {
        val protocol = ProtocolFactory.protocolFromToken(tokenType)
        val token = protocol.token
        println("Staking ${protocol.name} (${token.id}) with amount: $amount")
        println("Current price for 1 ${token.id} is $${token.price}")
        // todo add logic
    }
}

/**
 * Redeems staking rewards for a chosen token.
 * It will look in the store if user has redeemable tokens
 */
class StakingRedeemCommand(private val user: User, private val tokenType: TokenType) : StakingCommand {
    override fun execute() {
        TODO("Not yet implemented: Implement so it uses the store")
    }
}