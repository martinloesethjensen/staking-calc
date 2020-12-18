package models

import factory.TokenType

typealias Price = Double
typealias Amount = Double
typealias RewardAmount = Double
typealias WalletItem = Map<TokenType, Amount>

data class User(val name: String, var wallet: HashMap<TokenType, Amount> = hashMapOf())

data class Token(val id: String, val price: Price) {
    companion object {
        @JvmStatic
        val empty = Token("", 0.0)
    }
}

// Contains information about the protocol/project
data class Protocol(val name: String, val token: Token) {
    companion object {
        @JvmStatic
        val empty = Protocol("", Token.empty)
    }
}

data class Rewards(val token: Token, val amount: Amount) {
    companion object {
        @JvmStatic
        val empty = Rewards(Token.empty, 0.0)
    }
}
