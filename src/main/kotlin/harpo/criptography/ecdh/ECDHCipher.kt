package harpo.criptography.ecdh

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64.decode
import org.bouncycastle.util.encoders.Base64.toBase64String
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Security
import java.security.spec.ECGenParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class ECDHCipher {
    private var ecKeyGen: KeyPairGenerator by Delegates.notNull()
    private var keyFactory: KeyFactory by Delegates.notNull()
    companion object {
        const val ALGORITHM = "ECDH"
        const val STD_NAME = "secp256k1"
        const val TRANSFORMATION = "ECIES"
    }

    init {
        Security.addProvider(BouncyCastleProvider())
        this.keyFactory = KeyFactory.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME)
        this.ecKeyGen = KeyPairGenerator.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME)
        ecKeyGen.initialize(ECGenParameterSpec(STD_NAME))
    }

    fun getKeyPair(): KeyPair {
        val ecKeyPair = ecKeyGen.generateKeyPair()
        return KeyPair(publicKey = toBase64String(ecKeyPair.public.encoded),
                        privateKey = toBase64String(ecKeyPair.private.encoded))
    }

    fun encrypt(message: String, keyPair: KeyPair): String {
        val iesCipher = Cipher.getInstance(TRANSFORMATION, BouncyCastleProvider.PROVIDER_NAME)

        val spec = X509EncodedKeySpec(decode(keyPair.publicKey.toByteArray()))
        val publicKey = this.keyFactory.generatePublic(spec)
        iesCipher.init(Cipher.ENCRYPT_MODE, publicKey)

        val encryptedMessage = iesCipher.doFinal(message.toByteArray())

        return toBase64String(encryptedMessage)
    }

    fun decrypt(message: String, keyPair: KeyPair): String {
        val iesDecipher = Cipher.getInstance(TRANSFORMATION, BouncyCastleProvider.PROVIDER_NAME)

        val spec = PKCS8EncodedKeySpec(decode(keyPair.privateKey.toByteArray()))
        val privateKey = this.keyFactory.generatePrivate(spec)
        iesDecipher.init(Cipher.DECRYPT_MODE, privateKey)

        return String(iesDecipher.doFinal(decode(message.toByteArray())))
    }


}

data class KeyPair(val privateKey: String, val publicKey: String)
