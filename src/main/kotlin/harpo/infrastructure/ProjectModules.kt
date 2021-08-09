package harpo.infrastructure

import com.google.inject.AbstractModule
import com.google.inject.Provides
import harpo.criptography.ecdh.ECDHCipher

class ProjectModules: AbstractModule() {


    @Provides
    fun getCipher(): ECDHCipher = ECDHCipher()
}