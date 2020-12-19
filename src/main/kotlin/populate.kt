import commands.CommandProcessor
import models.TokenType
import store.StakingRepository

fun main() {
    val commandProcessor = CommandProcessor()
    val stakingRepository = StakingRepository()

//    var user = alice

    UserSession.login(alice)
    print(UserSession.user)

    stakingRepository.stake(alice, TokenType.DOT, 10.0)
    stakingRepository.stake(alice, TokenType.DOT, 10.0)

    UserSession.login(bob)
    print(UserSession.user)

    stakingRepository.stake(bob, TokenType.DOT, 20.0)
    println(UserSession.user?.wallet?.filter { it.key == TokenType.DOT }?.values?.sum())

//    commandProcessor
//        .addToQueue(StakingAddCommand(alice, TokenType.KSM, amount = 3.0))
//        .addToQueue(StakingAddCommand(bob, TokenType.ETH, amount = 3.06))
//        .addToQueue(StakingAddCommand(charlie, TokenType.DOT, amount = 25.0))

    UserSession.logout()
}