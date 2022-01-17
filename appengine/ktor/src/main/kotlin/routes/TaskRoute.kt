package com.example.demo.routes

import com.example.demo.API_VERSION
import com.example.demo.repository.Repository
import io.ktor.auth.*
import io.ktor.locations.*
import io.ktor.routing.*

const val TASKS = "$API_VERSION/tasks"
const val TASK_GET_BY_NAME = "$TASKS/name"
const val TASK_CREATE = "$TASKS/create"
const val TASK_DELETE = "$TASKS/delete"

@KtorExperimentalLocationsAPI
@Location(TASK_GET_BY_NAME)
class TaskGetByNameRoute

@KtorExperimentalLocationsAPI
@Location(TASK_CREATE)
class TaskCreateRoute

@KtorExperimentalLocationsAPI
@Location(TASK_DELETE)
class TaskDeleteRoute


@KtorExperimentalLocationsAPI
// 1
fun Route.tasks(
    db: Repository
) {
    authenticate {
        post<TaskCreateRoute> {

        }
    }

    authenticate {
        get<TaskGetByNameRoute> {

        }
    }

    authenticate {
        delete<TaskDeleteRoute> {

        }
    }

}
