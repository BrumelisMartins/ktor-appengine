/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.example.demo

/* ktlint-disable no-wildcard-imports */
import com.example.demo.repository.BotRepository
import com.example.demo.repository.DatabaseFactory
import com.example.demo.routes.users
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.html.*
import io.ktor.locations.*
import io.ktor.routing.*
import kotlinx.html.*

const val username = "master_user"
const val password = "adh125DFkeO0r2Rgr"
const val API_VERSION = "/v1"


// Entry Point of the application as defined in resources/application.conf.
// @see https://ktor.io/servers/configuration.html#hocon-file
fun Application.module() {
    // This adds Date and Server headers to each response, and allows custom additional headers
    install(DefaultHeaders)
    // This uses use the logger to log every call (request/response)
    install(CallLogging)

    install(Locations) {
    }

    install(Compression) {
        gzip()
        deflate {
            priority = 10.0
            minimumSize(1024)
        }
    }
    DatabaseFactory.init()
    val db = BotRepository()


    install(Authentication) {
        basic {
            realm = "ktor"
            validate { credentials ->
                if (credentials.name == username && credentials.password == password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        users(db)
    }
}
