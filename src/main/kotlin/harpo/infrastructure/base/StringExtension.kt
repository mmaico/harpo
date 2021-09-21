package harpo.infrastructure.base

import java.math.BigInteger

class Base {
    companion object {
        const val HEX:Int = 16
    }
}

infix fun String.hexToBigInteger(base: Int): BigInteger {
    return BigInteger(this, base)
}

