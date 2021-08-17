package harpo.client

import harpo.criptography.ecdh.ECDHCipher
import harpo.criptography.ecdh.KeyPair
import harpo.criptography.shamir.SecretSharing
import harpo.infrastructure.injector.ServiceLocator

class Client(private var cipher: ECDHCipher? = null) {
    init {
        this.cipher = ServiceLocator.getInjector().getInstance(ECDHCipher::class.java)
    }

    fun createKeyPairs(): KeyPair = cipher!!.getKeyPair()
    fun encrypt(secret: String, keyPair: KeyPair) = cipher!!.encrypt(secret, keyPair)
    fun split(secret: String): List<SecretSharing.SecretFragment> = SecretSharing.split(secret)
}