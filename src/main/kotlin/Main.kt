import dao.ClassDao
import dao.FileDao
import dao.UserDao
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import service.FileService
import service.UserService
import utils.Database
import routers.classRouter
import routers.fileRouter
import routers.userRouter
import service.ClassService

fun main() {

    Database().getInMemoryConnection()

    embeddedServer(Netty, port = 8080, module = Application::myModule).start(wait = true)
}

fun Application.myModule() {

    routing {
        val userDao = UserDao()
        val classDao = ClassDao()
        val fileDao = FileDao()

        val userService = UserService(userDao, classDao)
        val fileService = FileService(fileDao, userDao)
        val classService = ClassService(classDao)

        userRouter(userService)
        fileRouter(fileService)
        classRouter(classService)
    }
}