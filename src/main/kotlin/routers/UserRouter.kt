package routers

import com.google.gson.Gson
import models.LoginRequest
import models.SignupRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.ClassroomRequest
import service.UserService

fun Route.userRouter(userService: UserService) {
    val gson = Gson()

    route("/users") {

        post("/signup") {
            try {
                val signupRequest = gson.fromJson(call.receiveText(), SignupRequest::class.java)
                if (userService.signup(signupRequest)) {
                    call.respond(HttpStatusCode.Created, "User created successfully")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "User could not be created")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            }
        }

        put("/classroom") {
            try {
                val classroomRequest = gson.fromJson(call.receiveText(), ClassroomRequest::class.java)
                if (userService.addClassroom(classroomRequest.studentId, classroomRequest.classId)) {
                    call.respond(HttpStatusCode.OK, "Classroom added successfully")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Could not add to class")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            }
        }

        post("/authenticate") {
            try {
                val loginRequest = gson.fromJson(call.receiveText(), LoginRequest::class.java)

                if (userService.authenticate(loginRequest.email, loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, "Login successful")
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format auth")
            }
        }

        get("/profile") {
            val email = call.request.queryParameters["email"]
            if (email != null) {
                val profile = userService.getProfileInformation(email)
                if (profile != null) {
                    call.respond(HttpStatusCode.OK, profile)
                } else {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing email parameter")
            }
        }
    }
}
