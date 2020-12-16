import factory.ProtocolFactory
import factory.TokenType
import models.User
import store.StakingRepository
import utils.readLineDouble
import utils.shouldContinue
import utils.toTokenType

// Command
interface StakingCommand {
    fun execute()
}

class StakingAddCommand(private val user: User, private val tokenType: TokenType, private val amount: Double) : StakingCommand {
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
 * It will look in the store if user
 */
class StakingRedeemCommand(private val user: User, private val tokenType: TokenType) : StakingCommand {
    override fun execute() {
        TODO("Not yet implemented: Implement so it uses the store")
    }
}

class CommandProcessor {

    private val queue = ArrayList<StakingCommand>()

    fun addToQueue(stakingCommand: StakingCommand): CommandProcessor =
        apply {
            queue.add(stakingCommand)
        }

    fun processCommands(): CommandProcessor =
        apply {
            queue.forEach { it.execute() }
            queue.clear()
        }
}

fun main() {
    val commandProcessor = CommandProcessor()
    val stakingRepository = StakingRepository()
    var finished = false

    val user = "Alice"



    while (!finished) {
        print("Input a token you'd like to stake: ")
        val stakeIn = readLine()?.toTokenType()

        // Early return --> we don't want to proceed as token is unknown to us
        if (stakeIn.isNullOrUnknown()) return

        val protocol = ProtocolFactory.protocolFromToken(stakeIn!!)
        print("Input amount in ${protocol.token.id}: ")
        val amountIn = readLineDouble()

        // Return if null on NaN
        if (amountIn == null || amountIn.isNaN() || amountIn <= 0.0) return

        commandProcessor.addToQueue(StakingAddCommand(user, stakeIn, amountIn))

        print("Want to stake more assets? y/n: ")
        val input = readLine()

        if (!input.shouldContinue()) finished = true
    }

    commandProcessor.processCommands()
}

// TODO
fun populate(stakingRepository: StakingRepository, commandProcessor: CommandProcessor) {


    commandProcessor
        .addToQueue(StakingAddCommand(User("Alice"), TokenType.KSM, amount = 3.0))
        .addToQueue(StakingAddCommand("Bob", TokenType.ETH, amount = 3.06))
        .addToQueue(StakingAddCommand("", TokenType.DOT, amount = 25.0))
}

// TODO
fun populateCommands() {

}