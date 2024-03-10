package service

import dao.ClassDao
import models.ClassInformation
import models.ClassRequest
import models.CoTeacherRequest
import utils.Utils

class ClassService(private val classDao: ClassDao) {
    private val utils = Utils()

    fun addClass(classRequest: ClassRequest): ClassInformation? {
        val classInfo = ClassInformation(
            classId = utils.generateUUID(),
            name = classRequest.name,
            teacherId = classRequest.teacherId
        )
        return classDao.createClass(classInfo)
    }

    fun addCoTeacher(coTeacherRequest: CoTeacherRequest) {
        classDao.addCoTeacher(coTeacherRequest.classId, coTeacherRequest.studentId)
    }

    fun getClass(classId: String): ClassInformation? {
        return classDao.findById(classId)
    }

    fun getAllClasses(): List<ClassInformation> {
        return classDao.getAllClasses()
    }

    fun addPractice(classId: String, practiceId: String) {
        classDao.addPractice(classId, practiceId)
    }
}