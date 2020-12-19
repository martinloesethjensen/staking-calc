import factory.TokenType
import models.User
import store.StakingRepository

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