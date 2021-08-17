
package harpo

import harpo.client.Client
import harpo.criptography.ecdh.ECDHCipher
import harpo.infrastructure.template_engine.Console
import javax.inject.Inject


class App @Inject constructor(val cipher: ECDHCipher) {

}

//fun main() {
//    val injector: Injector = Guice.createInjector(ProjectModules())
//    val instance = injector.getInstance(App::class.java)
//    val keyPair = instance.cipher.getKeyPair()
//    println("Private Key ${keyPair.privateKey}")
//    println("Public Key ${keyPair.publicKey}")
//
//    val text = "Hello world09"
//    println("Encrypt text: $text")
//
//    val cipher = instance.cipher.encrypt(text, keyPair)
//    println("Encrypted text: $cipher")
//    val split = SecretSharing.split(cipher)
//    split.stream().forEach { println("index=${it.index} - value=${it.value}") }
//    println("#############################################")
//
//    val listOf = listOf(
//        SecretSharing.SecretFragment(
//            2,
//            "mL+OCgSHVGBi0nE+K1H+yoOW+sSqJ37Dx8VzSuUCBrZlX1KZkUsLgsi6DVQJgvxhyJnjBGSwaPd/JcYoHjKZNJKasXnHeKdkAR1rVGi1Sf2jHWrzhcWJ+GzdCivH6p4OZxiE4AJzxbwXscXwmn30zNLp8xGTuGgu7tg2zxLsMN1EgPAh"
//        ),
//        SecretSharing.SecretFragment(
//            3,
//            "Yjz3OywQqiS4Pb7lqWhk30PDu/w5Cvr5HzWmwPGpxs9RH3Q6SWtZndgaORRfNE36Yz5xDAdSEKbXuiACieL8jCLMlr+a5TsUzkhenXahaXzFSkRRYVeZMESzojg/rkpdx2RKpkUWpITfFiAe7XbHX+DjyB9D11G2+itEn5k84Q6t8XtA"
//        ),
//        SecretSharing.SecretFragment(
//            4,
//            "ORI+Mc/e0NM3QRvs35kisf8SVn+JJF4g+XGd71WMh8yKcpcF5JpqgPlYshUke9DqNNfzXEotkJOB9E4iQlbbED0Iq1skZfEyk0mFw9mNBnWk7rVO8NGpd2S0PqWSwVQ64oIJhiWjowqCYfM/DoP5SpiDNa9P3R34fv99AoRgXOhEIGBQ"
//        )
//    )
//    println("\u001B[43m Recontruction: ${SecretSharing.assembly(listOf)}")
//
//
//    val deCipher = instance.cipher.decrypt(cipher, keyPair)
//    println("Decrypted Text $deCipher")
//}
fun main() {


    val context: MutableMap<String?, Any> = mutableMapOf("client" to Client())

    Console.execute(context, "/templates/client-template.vm")
}

