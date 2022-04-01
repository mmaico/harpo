package harpo.criptography.shamir

import com.codahale.shamir.Scheme
import org.bouncycastle.util.encoders.Base64
import org.bouncycastle.util.encoders.Base64.toBase64String
import java.nio.charset.StandardCharsets
import java.security.SecureRandom

class SecretSharing {

    companion object {
        private const val QUANTITY_PARTS: Int = 5
        private const val MINIMUM_TO_RECOVERY = 3
        private val scheme = Scheme(SecureRandom(), QUANTITY_PARTS, MINIMUM_TO_RECOVERY)

        fun split(message: String): List<SecretFragment> {
            val secret = message.toByteArray(StandardCharsets.UTF_8)
            val parts = scheme.split(secret)
            return parts.entries.map { SecretFragment(it.key, toBase64String(it.value)) }.toList()
        }

        fun assembly(fragmets: List<SecretFragment>): String {
            val fragmentBytes = fragmets.associate { it.index to Base64.decode(it.value) }.toMap()
            return scheme.join(fragmentBytes).toString(StandardCharsets.UTF_8)
        }
    }

    data class SecretFragment(val index: Int, val value: String)
}
