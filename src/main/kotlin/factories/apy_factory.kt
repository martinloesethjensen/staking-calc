package factories

import models.TokenType
import utils.DOT_APY
import utils.ETH_APY
import utils.KSM_APY

object APYFactory {
    fun apyFromToken(tokenType: TokenType): Double = when (tokenType) {
        TokenType.DOT -> DOT_APY
        TokenType.KSM -> KSM_APY
        TokenType.ETH -> ETH_APY
        TokenType.UNKNOWN -> 0.0
    }
}