package utils

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import utils.Database.Classes.entityId
import utils.Database.Users.uniqueIndex

class Database {

    fun getInMemoryConnection() {
        org.jetbrains.exposed.sql.Database.connect("jdbc:mysql://localhost:3306/logodb", driver = "com.mysql.cj.jdbc.Driver", user = "root", password = "rootroot")
        transaction {
            SchemaUtils.create(Users, Files, Classes)

            SchemaInitializer().addUsers()
            SchemaInitializer().addFiles()
        }
        println("Database initialized with schema")
    }

    object Users : IdTable<String>("Users") {
        override val id = varchar("userId", 255).entityId()
        val name = varchar("name", 255)
        val surname = varchar("surname", 255)
        val isTeacher = bool("isTeacher")
        val password = varchar("password", 255)
        val email = varchar("email", 255).uniqueIndex()
        val classroomId = optReference("classroomId", Classes)

        override val primaryKey = PrimaryKey(id)
    }

    object Files : IdTable<String>("Files") {
        override val id = varchar("id", 255).entityId()
        val studentId = optReference("studentId", Users)
        val name = varchar("name", 255)
        val script = text("script").nullable()
        val grade = integer("grade").nullable()

        override val primaryKey = PrimaryKey(id)
    }

    object Classes : IdTable<String>("Classes") {
        override val id = varchar("classId", 255).entityId()
        val name = varchar("name", 255)
        val teacherId = reference("teacherId", Users).nullable()
        val coTeacherId = optReference("coTeacherId", Users)
        val practiceId = optReference("practiceId", Files)

        override val primaryKey = PrimaryKey(id)
    }
}