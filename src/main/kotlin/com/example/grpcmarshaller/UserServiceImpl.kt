package com.example.grpcmarshaller

import com.airwallex.grpc.error.Error
import com.airwallex.grpc.error.toResult
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import demo.User
import demo.UserServiceRpc
import java.util.UUID
import org.springframework.stereotype.Service
import pbandk.wkt.StringValue

@Service
class UserServiceImpl : UserServiceRpc {

    override suspend fun create(request: User): Result<StringValue, Error> {
        if (request.name == "admin") {
            return Error.invalidArgument("not allowed", code = "ERROR_CUSTOM")
                .toResult()
        }


        return Ok(StringValue(value = UUID.randomUUID().toString()))
    }

    override suspend fun get(request: StringValue): Result<User, Error> {
        return Ok(User(id = request.value))
    }
}