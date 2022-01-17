package com.example.demo.routes

import com.example.demo.API_VERSION
import com.example.demo.repository.Repository
import io.ktor.auth.*
import io.ktor.locations.*
import io.ktor.routing.*

const val ACTIVE_USERS = "$API_VERSION/activeusers"
const val SET_USER_TO_ACTIVE = "$ACTIVE_USERS/add"
const val SET_USER_TO_INACTIVE = "$ACTIVE_USERS/remove"


@KtorExperimentalLocationsAPI
@Location(SET_USER_TO_ACTIVE)
class ActiveUserSetActive

@KtorExperimentalLocationsAPI
@Location(SET_USER_TO_INACTIVE)
class ActiveUserSetInactive


@KtorExperimentalLocationsAPI
// 1
fun Route.activeUsers(
    db: Repository
) {
    authenticate {
        post<ActiveUserSetActive> {

        }
    }


    authenticate {
        delete<ActiveUserSetInactive> {

        }
    }

}
