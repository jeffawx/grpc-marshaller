package demo

import com.airwallex.grpc.annotations.GrpcService
import com.airwallex.grpc.error.Error
import com.github.michaelbull.result.Result
import javax.annotation.Generated
import pbandk.wkt.StringValue

@Generated(
    value = ["com.airwallex.grpc.generator.protoc.service.ServiceCodeGenerator"],
    date = "Fri Oct 14 22:20:39 CST 2022",
    comments = "demo/user.proto",
)
@GrpcService(name = "UserService", protoJavaPackage = "demo")
interface UserServiceRpc {

    suspend fun create(request: User): Result<StringValue, Error>

    suspend fun get(request: StringValue): Result<User, Error>
}
