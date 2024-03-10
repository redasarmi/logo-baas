package dao

import models.UserInformation
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import utils.Database

class UserDao {

    fun findById(id: String): UserInformation? =
        transaction() {
        Database.Users.select { Database.Users.id eq id }.mapNotNull { toUser(it) }.singleOrNull()
    }

    fun findByEmail(email: String): UserInformation? =
        transaction() {
            Database.Users.select { Database.Users.email eq email }.mapNotNull { toUser(it) }.singleOrNull()
        }

    fun createUser(userInformation: UserInformation): UserInformation? =
        transaction() {
        Database.Users.insert {
            it[id] = userInformation.userId
            it[name] = userInformation.name
            it[email] = userInformation.email
            it[password] = userInformation.password
            it[isTeacher] = userInformation.isTeacher
        }.resultedValues?.singleOrNull()?.let { toUser(it) }
    }

    fun addClassroom(userId: String, classId: String) =
        transaction {
            Database.Users.update({ Database.Users.id eq userId }) {
                it[this.classroomId] = classId
            }
        }

    private fun toUser(row: ResultRow): UserInformation =
        UserInformation(
            userId = row[Database.Users.id].toString(),
            name = row[Database.Users.name],
            surname = row[Database.Users.surname],
            email = row[Database.Users.email],
            password = row[Database.Users.password],
            isTeacher = row[Database.Users.isTeacher]
        )
}