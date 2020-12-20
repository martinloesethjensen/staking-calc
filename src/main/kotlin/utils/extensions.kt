package utils

import models.Amount
import models.TokenType
import models.User

fun String.toTokenType(): TokenType = when (this.toUpperCase()) {
    TokenType.DOT.toSimpleName() -> TokenType.DOT
    TokenType.KSM.toSimpleName() -> TokenType.KSM
    TokenType.ETH.toSimpleName() -> TokenType.ETH
    else -> TokenType.UNKNOWN
}

fun TokenType.toSimpleName(): String = this.javaClass.simpleName.toUpperCase()

fun TokenType?.isNullOrUnknown(): Boolean = this == null || this is TokenType.UNKNOWN

fun TokenType.apy() = when (this) {
    TokenType.DOT -> DOT_APY
    TokenType.KSM -> KSM_APY
    TokenType.ETH -> ETH_APY
    TokenType.UNKNOWN -> 0.0
}

fun readLineDouble() = readLine()?.toDouble()

fun String?.shouldContinue(): Boolean = this != null && this.isNotEmpty() && this.toLowerCase().startsWith('y')

fun String.print() = println(this)

// Returns true if user has that token in their wallet
fun User.hasToken(token: TokenType) = this.wallet.containsKey(token)

// We know that user already has that token,
// so now we just need to check if amount to be staked if within the fund that the user has.
fun User.hasFunds(token: TokenType, amount: Amount) = amount <= this.wallet[token]!!

// Return funds for token
fun User.fundsFromToken(token: TokenType) = this.wallet[token]
