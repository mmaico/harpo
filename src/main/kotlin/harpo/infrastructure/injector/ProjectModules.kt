package harpo.infrastructure.injector

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Provides
import harpo.criptography.ecdh.ECDHCipher
import harpo.criptography.shamir.SecretSharing

class ProjectModules: AbstractModule() {
    @Provides
    fun getCipher(): ECDHCipher = ECDHCipher()

    @Provides
    fun getShamir(): SecretSharing = SecretSharing()
}

class ServiceLocator {

    companion object {
        private val injector: Injector = Guice.createInjector(ProjectModules())
        fun getInjector() = injector
    }
}