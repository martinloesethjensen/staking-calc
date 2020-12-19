import commands.CommandProcessor
import commands.StakingAddCommand
import factories.ProtocolFactory
import models.TokenType
import models.User
import store.StakingRepository
import utils.*
import kotlin.system.exitProcess

enum class MenuItem {
    STAKE, CLAIM, QUIT
}

fun main() {
    val commandProcessor = CommandProcessor()
    val stakingRepository = StakingRepository()
    var stakeFinished = false
    var claimFinished = false

    """
        |-------------------------------
        |Available users 
        |Enter one of the options below:
        |   '1' --> Alice
        |   '2' --> Bob
        |   '3' --> Charlie 
        |Default is Alice
        |-------------------------------   
    """.trimMargin().print()

    val user = when (readLine()) {
        "1" -> alice
        "2" -> bob
        "3" -> charlie
        else -> alice
    }

    UserSession.login(user)

    stakingRepository.claimRewards(user, TokenType.DOT)

    """
        |-------------------------------
        |User: ${user.name}
        |Enter one of the options below:
        |   '1' --> Stake tokens
        |   '2' --> Claim rewards
        |   'q' --> Quit 
        |Defaults to staking tokens
        |-------------------------------   
    """.trimMargin().print()

    val menuItem = when (readLine()?.toLowerCase()) {
        "1" -> MenuItem.STAKE
        "2" -> MenuItem.CLAIM
        "q" -> MenuItem.QUIT
        else -> MenuItem.STAKE
    }

    if (menuItem == MenuItem.QUIT) return

    while (menuItem == MenuItem.STAKE && !stakeFinished) {
        print("Input a token you'd like to stake: ")
        val stakeIn = readLine()?.toTokenType()

        // Early return --> we don't want to proceed as token is unknown to us
        if (stakeIn.isNullOrUnknown()) return

        // Factory to get the protocol/project info for a token
        val protocol = ProtocolFactory.protocolFromToken(stakeIn!!)

        print("Input amount in ${protocol.token.id}: ")
        val amountIn = readLineDouble()

        // Return if null or NaN
        if (amountIn == null || (amountIn.isNaN() && amountIn <= 0.0)) return

        commandProcessor.addToQueue(StakingAddCommand(user, stakeIn, amountIn))

        print("Want to stake more assets? y/n: ")
        val input = readLine()

        // Finish loop if user does not want to stake anymore
        if (!input.shouldContinue()) stakeFinished = true
    }

    while (menuItem == MenuItem.CLAIM && !claimFinished) {
        TODO("Finish claim/redeem rewards functionality")

        print("Want to claim more rewards? y/n: ")
        val input = readLine()

        // Finish loop if user does not want to claim anymore rewards
        if (!input.shouldContinue()) claimFinished = true
    }

    // Process commands
    commandProcessor.processCommands()

    UserSession.logout()
}
