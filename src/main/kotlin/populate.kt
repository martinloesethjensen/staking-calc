import factory.TokenType
import models.User
import models.WalletItem
import store.StakingRepository
import java.util.*

val wallet = hashMapOf(
    TokenType.KSM to 3.0,
    TokenType.DOT to 25.0,
    TokenType.ETH to 1.0
)

val alice = User("Alice", wallet)
val bob = User("Bob", wallet)
val charlie = User("Charlie", wallet)

fun main() {
    val commandProcessor = CommandProcessor()
    val stakingRepository = StakingRepository()

    var user = alice
    var result: WalletItem? = null

    result = stakingRepository.stake(user,TokenType.DOT, 10.0)
    user.wallet[TokenType.DOT]?.minus(result.entries.first().value)
    result = stakingRepository.stake(user,TokenType.DOT, 10.0)
    user.wallet[TokenType.DOT]?.minus(result.entries.first().value)
//    stakingRepository.stake(user,TokenType.DOT, 10.0)
    println(alice.wallet.filter { it.key == TokenType.DOT }.values.sum())

//    commandProcessor
//        .addToQueue(StakingAddCommand(alice, TokenType.KSM, amount = 3.0))
//        .addToQueue(StakingAddCommand(bob, TokenType.ETH, amount = 3.06))
//        .addToQueue(StakingAddCommand(charlie, TokenType.DOT, amount = 25.0))
}