package routers

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import service.FileService
import io.ktor.server.routing.*
import models.PracticeGradeRequest
import models.PracticeInformation
import models.PracticeRequest

fun Route.fileRouter(fileService: FileService) {

    val gson = Gson()

    route("/files") {

        get("/{id}") {
            val id = call.parameters["id"]
            val practice = fileService.getPractice(id ?: "")
            if (practice != null) {
                call.respond(practice)
            } else {
                call.respond(HttpStatusCode.NotFound, "Practice file not found")
            }
        }

        get("") {
            try {
                val files = fileService.getAllPractice()
                call.respond(files)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, "Practice files not found")
            }
        }

        post("/") {
            try {
                val practiceInformation = gson.fromJson(call.receiveText(), PracticeInformation::class.java)
                fileService.postPractice(practiceInformation)
                call.respond(HttpStatusCode.Created, "Practice file added successfully")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            }
        }

        put("/student") {
            try {
                val practiceRequest = gson.fromJson(call.receiveText(), PracticeRequest::class.java)
                fileService.addPracticeStudent(practiceRequest)
                call.respond(HttpStatusCode.Created, "Practice file added successfully")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            }
        }

        put("/grade") {
            try {
                val practiceGradeRequest = gson.fromJson(call.receiveText(), PracticeGradeRequest::class.java)
                fileService.gradePractice(practiceGradeRequest)
                call.respond(HttpStatusCode.OK, "Grade updated successfully")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            }
        }
    }
}
