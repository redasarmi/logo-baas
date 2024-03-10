package dao

import models.ClassInformation
import models.PracticeInformation
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import utils.Database

class ClassDao {
    
    fun findById(id: String): ClassInformation? =
        transaction() {
            Database.Classes.select { Database.Classes.id eq id }.mapNotNull { toClassInformation(it) }.singleOrNull()
        }
    
    fun createClass(classInformation: ClassInformation): ClassInformation? =
        transaction() { 
            Database.Classes.insert { 
                it[id] = classInformation.classId
                it[name] = classInformation.name
                it[teacherId] = classInformation.teacherId
            }.resultedValues?.singleOrNull()?.let { toClassInformation(it) }
        }

    fun addCoTeacher(classId: String, coTeacherId: String) =
        transaction {
            Database.Classes.update({ Database.Classes.id eq classId }) {
                it[this.coTeacherId] = coTeacherId
            }
        }

    fun addPractice(classId: String, practiceId: String) =
        transaction {
            Database.Classes.update({ Database.Classes.id eq classId }) {
                it[this.practiceId] = practiceId
            }
        }

    fun getAllClasses(): List<ClassInformation> {
        return transaction {
            Database.Classes.selectAll().map { row ->
                ClassInformation(
                    classId = row[Database.Classes.id].toString(),
                    practiceId = row[Database.Classes.practiceId].toString(),
                    name = row[Database.Classes.name],
                    coTeacherId = row[Database.Classes.coTeacherId].toString(),
                    teacherId = row[Database.Classes.teacherId].toString()
                )
            }
        }
    }

    private fun toClassInformation(row: ResultRow): ClassInformation =
        ClassInformation(
            classId = row[Database.Classes.id].toString(),
            name = row[Database.Classes.name],
            teacherId = row[Database.Classes.teacherId].toString()
        )
}