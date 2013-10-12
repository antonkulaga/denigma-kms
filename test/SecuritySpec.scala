import io.github.nremond.PBKDF2
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import com.github.t3hnar.bcrypt._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class SecuritySpec extends Specification {

  "Security" should {


    "Test PBKDF2" in new WithApplication() {

      PBKDF2("password", "salt", 2, 20, "HmacSHA1") must beEqualTo("ea6c014dc72d6f8ccd1ed92ace1d41f0d8de8957")
      PBKDF2("password", "salt", 4096, 20, "HmacSHA1") must beEqualTo("4b007901b765489abead49d926f721d065a429c1")
      PBKDF2("passwordPASSWORDpassword", "saltSALTsaltSALTsaltSALTsaltSALTsalt", 4096, 25, "HmacSHA1") must beEqualTo("3d2eec4fe41c849b80c8d83662c0e44a8b291a964cf2f07038")
      //PBKDF2("pass\0word", "sa\0lt", 4096, 16, "HmacSHA1") must beEqualTo("56fa6aa75548099dcc37d7f03425e0c3")

    }

    "Test pcrypt" in new WithApplication {
      val salt = "$2a$10$8K1p/a0dL1LXMIgoEDFrwO"
      "password".bcrypt(salt) must beEqualTo("$2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu")
    }

    //    "Check django compartability" in new WithApplication() {
    //      val username = "antonkulaga"
    //      val algo = "pbkdf2_sha256"
    //      val iters = 10000
    //      val salt = "BnOXX5iDbMEv"
    //
    //      val email = "antonkulaga@gmail.com"
    //      val password = "mind2mind"
    //      val hash = "lkNMW5P5dz2REHoRlX1EgzlxRICqTakwLvIYn0p2nbU="
    //      PBKDF2(password, salt, iters, 256,"HmacSHA256") must beEqualTo(hash)
    //
    //    }


  }
}
