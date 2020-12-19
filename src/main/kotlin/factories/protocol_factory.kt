package factories

import models.Protocol
import models.Token
import models.TokenType

object ProtocolFactory {
    fun protocolFromToken(tokenType: TokenType): Protocol = when (tokenType) {
        TokenType.DOT -> Protocol("Polkadot", Token("DOT", 5.0))
        TokenType.KSM -> Protocol("Kusama", Token("KSM", 50.0))
        TokenType.ETH -> Protocol("Ethereum", Token("ETH", 500.0))
        TokenType.UNKNOWN -> Protocol.empty
    }
}
