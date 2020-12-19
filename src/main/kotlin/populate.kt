import commands.CommandProcessor
import models.TokenType
import store.StakingRepository

fun main() {
    val commandProcessor = CommandProcessor()
    val stakingRepository = StakingRepository()

    UserSession.login(alice)

    stakingRepository.stake(alice, TokenType.DOT, 10.0)
    stakingRepository.stake(alice, TokenType.DOT, 10.0)
    stakingRepository.stake(alice, TokenType.KSM, 1.0)
    stakingRepository.stake(alice, TokenType.ETH, 2.0)

//    UserSession.login(bob)
//
//    stakingRepository.stake(bob, TokenType.DOT, 20.0)
//    println(UserSession.user?.wallet?.filter { it.key == TokenType.DOT }?.values?.sum())

//    commandProcessor
//        .addToQueue(StakingAddCommand(alice, TokenType.KSM, amount = 3.0))
//        .addToQueue(StakingAddCommand(bob, TokenType.ETH, amount = 3.06))
//        .addToQueue(StakingAddCommand(charlie, TokenType.DOT, amount = 25.0))

    UserSession.user?.wallet?.forEach {
        println(it)
    }


    UserSession.logout()
}