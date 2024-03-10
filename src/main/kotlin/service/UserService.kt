package service

import dao.ClassDao
import dao.UserDao
import models.SignupRequest
import models.UserInformation
import utils.Utils

class UserService(private val userDao: UserDao, private val classDao: ClassDao) {
    private val utils = Utils()

    fun authenticate(email: String, password: String): Boolean {
        val user = userDao.findByEmail(email) ?: return false

        return utils.verifyPassword(password, user.password)
    }

    fun signup(signupRequest: SignupRequest): Boolean {
        val existingUser = userDao.findByEmail(signupRequest.email)

        if (existingUser != null){
            return false
        }
        val hashedPassword = utils.hashPassword(signupRequest.password)
        val newUserInformation = UserInformation(
            userId = utils.generateUUID(),
            name = signupRequest.name,
            surname = signupRequest.surname,
            email = signupRequest.email,
            password = hashedPassword,
            isTeacher = signupRequest.isTeacher
        )

        userDao.createUser(newUserInformation)
        return true
    }

    fun addClassroom(classId: String, userId: String): Boolean {
        val user = userDao.findById(userId)
        val classroom = classDao.findById(classId)
        return if ((user != null) && (classroom != null)) {
            userDao.addClassroom(userId, classId)
            true
        } else {
            false
        }
    }

    fun getProfileInformation(userId: String): UserInformation? {
        return userDao.findById(userId)
    }
}