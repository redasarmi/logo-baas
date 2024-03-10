package service

import dao.FileDao
import dao.UserDao
import models.PracticeGradeRequest
import models.PracticeInformation
import models.PracticeRequest
import utils.Utils

class FileService(private val fileDao: FileDao, private val userDao: UserDao) {
    val utils = Utils()

    fun getPractice(id: String): PracticeInformation? {
        return fileDao.findById(id)
    }

    fun getAllPractice(): List<PracticeInformation> {
        return fileDao.getAllFile()
    }

    fun postPractice(practiceInformation: PracticeInformation){
        val id = utils.generateUUID()
        fileDao.createFile(PracticeInformation(
            practiceId = id,
            name = practiceInformation.name
        ))
    }

    fun addPracticeStudent(practiceRequest: PracticeRequest): Boolean{
        val user = userDao.findById(practiceRequest.studentId)?.userId
        val practice = fileDao.findById(practiceRequest.practiceId)?.practiceId
        return if ((user != null) && (practice != null )) {
            fileDao.addPracticeStudent(user, practice, practiceRequest.script)
            true
        } else {
            false
        }
    }


    fun gradePractice(practiceGradeRequest: PracticeGradeRequest){
        val practiceId = fileDao.findById(practiceGradeRequest.practiceId)?.practiceId
        val user = userDao.findById(practiceGradeRequest.studentId)?.userId
        if ((user != null) && (practiceId != null )) {
            fileDao.updateFile(practiceId, user, practiceGradeRequest.grade)
        }
    }
}