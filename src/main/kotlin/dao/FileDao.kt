package dao

import models.PracticeGradeRequest
import models.PracticeInformation
import models.PracticeRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import utils.Database

class FileDao {

    fun createFile(practiceInformation: PracticeInformation) {
        transaction {
            Database.Files.insert {
                it[id] = practiceInformation.practiceId
                it[name] = practiceInformation.name
            }
        }
    }

    fun findById(id: String): PracticeInformation? {
        return transaction {
            Database.Files.select { Database.Files.id eq id }.mapNotNull { toPracticeInformation(it) }.singleOrNull()
        }
    }

    fun getAllFile(): List<PracticeInformation> {
        return transaction {
            Database.Files.selectAll().map { row ->
                PracticeInformation(
                    practiceId = row[Database.Files.id].toString(),
                    name = row[Database.Files.name],
                    script = row[Database.Files.script],
                    grade = row[Database.Files.grade]
                )
            }
        }
    }

    fun addPracticeStudent(user: String, practiceId: String, newScript: String) {
        transaction {
            Database.Files.update({ Database.Files.id eq  practiceId }) {
                it[script] = newScript
                it[studentId] = studentId
            }
        }
    }

    private fun toPracticeInformation(row: ResultRow): PracticeInformation =
        PracticeInformation(
            practiceId = row[Database.Files.id].toString(),
            name = row[Database.Files.name],
            script = row[Database.Files.script],
            grade = row[Database.Files.grade]
        )

    fun updateFile(practiceId: String, studentId: String, newGrade: Int) {
        transaction {
            Database.Files.update ({
                (Database.Files.id eq  practiceId) and
                (Database.Files.studentId eq  studentId)
            }) {
                it[grade] = newGrade
            }
        }
    }
}