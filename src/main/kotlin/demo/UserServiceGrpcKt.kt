package demo

import com.example.grpcmarshaller.DescriptorLoader
import com.example.grpcmarshaller.PbandkMarshaller
import com.google.protobuf.Descriptors
import io.grpc.MethodDescriptor
import io.grpc.MethodDescriptor.generateFullMethodName
import io.grpc.ServerServiceDefinition
import io.grpc.ServiceDescriptor
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.protobuf.ProtoFileDescriptorSupplier
import io.grpc.protobuf.ProtoServiceDescriptorSupplier
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import pbandk.wkt.StringValue

class UserServiceGrpcKt {

    abstract class UserServiceCoroutineImplBase(
        coroutineContext: CoroutineContext = EmptyCoroutineContext
    ) : AbstractCoroutineServerImpl(coroutineContext) {

        abstract suspend fun create(request: User): StringValue

        abstract suspend fun get(request: StringValue): User

        override fun bindService(): ServerServiceDefinition {
            return ServerServiceDefinition.builder(serviceDescriptor)
                .addMethod(
                    unaryServerMethodDefinition(
                        context = this.context,
                        descriptor = createMethod,
                        implementation = ::create
                    )
                )
                .addMethod(
                    unaryServerMethodDefinition(
                        context = this.context,
                        descriptor = getMethod,
                        implementation = ::get
                    )
                )
                .build()
        }
    }

    class SchemaDescriptor(private val serviceName: String) : ProtoFileDescriptorSupplier, ProtoServiceDescriptorSupplier {

        private val desc = DescriptorLoader()

        override fun getFileDescriptor(): Descriptors.FileDescriptor {
            return serviceDescriptor.file
        }

        override fun getServiceDescriptor(): Descriptors.ServiceDescriptor {
            return desc.getServiceDescriptor(serviceName)
        }
    }

    companion object {
        private val serviceDescriptor: ServiceDescriptor by lazy {
            ServiceDescriptor.newBuilder("demo.UserService")
                .setSchemaDescriptor(SchemaDescriptor("demo.UserService"))
                .addMethod(createMethod)
                .addMethod(getMethod)
                .build()
        }

        private val createMethod: MethodDescriptor<User, StringValue> by lazy {
            MethodDescriptor.newBuilder<User, StringValue>()
                .setType(MethodDescriptor.MethodType.UNARY)
                .setFullMethodName(generateFullMethodName("demo.UserService", "Create"))
                .setSampledToLocalTracing(true)
                .setRequestMarshaller(PbandkMarshaller(User.Companion))
                .setResponseMarshaller(PbandkMarshaller(StringValue.Companion))
                .build()
        }

        private val getMethod: MethodDescriptor<StringValue, User> by lazy {
            MethodDescriptor.newBuilder<StringValue, User>()
                .setType(MethodDescriptor.MethodType.UNARY)
                .setFullMethodName(generateFullMethodName("demo.UserService", "Get"))
                .setSampledToLocalTracing(true)
                .setRequestMarshaller(PbandkMarshaller(StringValue.Companion))
                .setResponseMarshaller(PbandkMarshaller(User.Companion))
                .build()
        }
    }
}
