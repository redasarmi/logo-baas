package utils

import models.PracticeInformation
import models.UserInformation
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore

class SchemaInitializer() {

    val utils = Utils()

    fun addUsers() {
        studentSet.forEach { student ->
            Database.Users.insertIgnore {
                it[id] = student.userId
                it[name] = student.name
                it[surname] = student.surname
                it[isTeacher] = student.isTeacher
                it[password] = utils.hashPassword(student.password) // Assuming you have a method to hash passwords
                it[email] = student.email
            }
        }
    }

    fun addFiles() {
        filesSet.forEach { file ->
            Database.Files.insertIgnore {
                it[id] = file.practiceId
                it[name] = file.name
            }
        }
    }

    private val studentSet = listOf(
        // Students
        UserInformation("student01", "Alice", "Anderson", false, "password01", "alice.anderson@example.com"),
        UserInformation(utils.generateUUID(), "Bob", "Brown", false, "password02", "bob.brown@example.com"),

        // Teachers
        UserInformation("teacher01", "Ian", "Irwin", true, "password09", "ian.irwin@example.com"),
        UserInformation(utils.generateUUID(), "Jane", "Johnson", true, "password10", "jane.johnson@example.com")
    )

    private val filesSet = listOf(
        PracticeInformation(utils.generateUUID(), name = "star graph")
    )
}
