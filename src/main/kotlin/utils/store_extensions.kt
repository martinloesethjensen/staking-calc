package utils

import models.Amount
import models.TokenType
import models.User

// Returns true if user has that token in their wallet
fun User.hasToken(token: TokenType) = this.wallet.containsKey(token)

// We know that user already has that token,
// so now we just need to check if amount to be staked if within the fund that the user has.
fun User.hasFunds(token: TokenType, amount: Amount) = amount <= this.wallet[token]!!

// Return funds for token
fun User.fundsFromToken(token: TokenType) = this.wallet[token]

