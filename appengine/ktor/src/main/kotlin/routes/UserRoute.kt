package com.example.demo.routes

import com.example.demo.API_VERSION
import com.example.demo.repository.Repository
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val USERS = "$API_VERSION/users"
const val USER_GET_BY_EMAIL = "$USERS/email"
const val USER_CREATE = "$USERS/create"
const val USER_GET_BY_STATUS = "$USERS/status"
const val USER_UPDATE_STATUS = "$USER_GET_BY_STATUS/update"
const val USER_DELETE = "$USERS/delete"


@KtorExperimentalLocationsAPI
@Location(USER_GET_BY_EMAIL)
class UserGetMyEmailRoute

@KtorExperimentalLocationsAPI
@Location(USER_CREATE)
class UserCreateRoute

@KtorExperimentalLocationsAPI
@Location(USER_GET_BY_STATUS)
class UserGetByStatusRoute

@KtorExperimentalLocationsAPI
@Location(USER_UPDATE_STATUS)
class UserUpdateStatus

@KtorExperimentalLocationsAPI
@Location(USER_DELETE)
class UserDeleteRoute

@KtorExperimentalLocationsAPI
// 1
fun Route.users(
    db: Repository
) {
    authenticate {
        post<UserCreateRoute> { // 2
            val signupParameters = call.receive<Parameters>() // 3
            val password = signupParameters["password"] // 4
                ?: return@post call.respond(
                    HttpStatusCode.Unauthorized, "Missing Fields"
                )
            val displayName = signupParameters["displayName"]
                ?: return@post call.respond(
                    HttpStatusCode.Unauthorized, "Missing Fields"
                )
            val email = signupParameters["email"]
                ?: return@post call.respond(
                    HttpStatusCode.Unauthorized, "Missing Fields"
                )
            val status = signupParameters["status"]
                ?: return@post call.respond(
                    HttpStatusCode.Unauthorized, "Missing Fields"
                )
            val type = signupParameters["type"]
                ?: return@post call.respond(
                    HttpStatusCode.Unauthorized, "Missing Fields"
                )
            val amountOfGold = signupParameters["amountOfGold"]
                ?: "0"
            val amountOfResources = signupParameters["amountOfResources"]
                ?: "0"
            try {
                db.addUser(
                    email,
                    displayName,
                    password,
                    status,
                    type,
                    amountOfGold.toLong(),
                    amountOfResources.toLong()
                )
                call.respond(HttpStatusCode.Created)
            } catch (e: Throwable) {
                application.log.error("Failed to register user", e)
                call.respond(HttpStatusCode.BadRequest, "Problems creating User")
            }
        }
    }

    authenticate {
        get<UserGetByStatusRoute> { // 1
            val signinParameters = call.request.queryParameters
            val status = signinParameters["status"]
                ?: return@get call.respond(
                    HttpStatusCode.Unauthorized, "Missing Fields"
                )

            try {
                val user = db.findUserByStatus(status) // 2
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.NoContent, "No User found")
                }
            } catch (e: Throwable) {
                application.log.error("Failed to find User", e)
                call.respond(HttpStatusCode.BadRequest, "No User found")
            }
        }
    }

    authenticate {
        get<UserGetMyEmailRoute> { // 1
            val signinParameters = call.request.queryParameters
            val email = signinParameters["email"]
                ?: return@get call.respond(
                    HttpStatusCode.Unauthorized, "Missing Fields"
                )

            try {
                val user = db.findUserByEmail(email) // 2
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.NoContent, "No User found")
                }
            } catch (e: Throwable) {
                application.log.error("Failed to find User", e)
                call.respond(HttpStatusCode.BadRequest, "No User found")
            }
        }
    }

    authenticate {
        delete<UserDeleteRoute> { // 1
            val signinParameters = call.receive<Parameters>()
            val email = signinParameters["email"]
                ?: return@delete call.respond(
                    HttpStatusCode.Unauthorized, "Missing Fields"
                )
            try {
                db.removeUser(email) // 2
                call.respond(HttpStatusCode.OK)
            } catch (e: Throwable) {
                application.log.error("Failed to remove User", e)
                call.respond(HttpStatusCode.BadRequest, "Failed to remove user")
            }
        }
    }

    authenticate {
        post<UserUpdateStatus> {

        }
    }

}


