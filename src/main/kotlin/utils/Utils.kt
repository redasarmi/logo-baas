package utils

import org.mindrot.jbcrypt.BCrypt
import java.util.UUID

class Utils() {

    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }

    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }
}