package models

import kotlinx.serialization.Serializable

data class UserInformation(
    val userId: String,
    val name: String,
    val surname: String,
    val isTeacher: Boolean,
    val password: String,
    val email: String,
    val classroomId: List<String>? = listOf()
)

data class PracticeInformation(
    val practiceId: String,
    val studentId: String? = "",
    val name: String,
    val script: String? = "",
    val grade: Int? = 0
)

data class PracticeRequest(
    val practiceId: String,
    val studentId: String,
    val script: String
)

data class PracticeGradeRequest(
    val practiceId: String,
    val studentId: String,
    val grade: Int
)

data class SignupRequest(
    val name: String,
    val surname: String,
    val isTeacher: Boolean,
    val password: String,
    val email: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class ClassRequest(
    val name: String,
    val subject: String,
    val teacherId: String
)

data class ClassInformation(
    val classId: String,
    val name: String,
    val teacherId : String,
    val coTeacherId: String? = "",
    val practiceId: String? = ""
)

data class CoTeacherRequest(
    val classId: String,
    val studentId: String
)

data class ClassroomRequest(
    val classId: String,
    val studentId: String
)