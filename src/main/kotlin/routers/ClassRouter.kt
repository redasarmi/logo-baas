package routers

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.ClassRequest
import models.CoTeacherRequest
import service.ClassService

fun Route.classRouter(classService: ClassService) {

    val gson = Gson()

    route("/classes") {

        post("/add") {
            try {
                val classRequest = gson.fromJson(call.receiveText(), ClassRequest::class.java)
                val classInfo = classService.addClass(classRequest)
                if (classInfo != null) {
                    call.respond(HttpStatusCode.Created, classInfo)
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to create class")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            }
        }

        post("/add-co-teacher") {
            try {
                val coTeacherRequest = gson.fromJson(call.receiveText(), CoTeacherRequest::class.java)
                classService.addCoTeacher(coTeacherRequest)
                call.respond(HttpStatusCode.OK, "Co-teacher added successfully")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            }
        }

        get("/{classId}") {
            val classId = call.parameters["classId"]
            if (classId != null) {
                val classInfo = classService.getClass(classId)
                if (classInfo != null) {
                    call.respond(HttpStatusCode.OK, classInfo)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Class not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid class ID")
            }
        }

        get("") {
            try {
                val classes = classService.getAllClasses()
                call.respond(classes)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, "Classes not found")
            }
        }

        post("/add-practice") {
            val classId = call.request.queryParameters["classId"]
            val practiceId = call.request.queryParameters["practiceId"]
            if (classId != null && practiceId != null) {
                classService.addPractice(classId, practiceId)
                call.respond(HttpStatusCode.OK, "Practice added to class successfully")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing classId or practiceId")
            }
        }
    }
}
