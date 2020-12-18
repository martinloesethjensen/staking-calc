package utils

import factory.TokenType

fun String.toTokenType(): TokenType = when (this.toLowerCase()) {
    TokenType.DOT.toSimpleName() -> TokenType.DOT
    TokenType.KSM.toSimpleName() -> TokenType.KSM
    TokenType.ETH.toSimpleName() -> TokenType.ETH
    else -> TokenType.UNKNOWN
}

fun TokenType.toSimpleName(): String = this.javaClass.simpleName.toLowerCase()

fun TokenType?.isNullOrUnknown(): Boolean = this == null || this is TokenType.UNKNOWN

fun readLineDouble() = readLine()?.toDouble()

fun String?.shouldContinue(): Boolean = this != null && this.isNotEmpty() && this.toLowerCase().startsWith('y')
