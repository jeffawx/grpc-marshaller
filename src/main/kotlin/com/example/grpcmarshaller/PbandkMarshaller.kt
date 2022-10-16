package com.example.grpcmarshaller

import io.grpc.MethodDescriptor.Marshaller
import java.io.InputStream
import pbandk.Message
import pbandk.decodeFromByteArray
import pbandk.encodeToByteArray

class PbandkMarshaller<T : Message>(private val companion : Message.Companion<T>) : Marshaller<T> {

    override fun stream(value: T): InputStream {
        return value.encodeToByteArray().inputStream()
    }

    override fun parse(stream: InputStream): T {
        return companion.decodeFromByteArray(stream.readAllBytes())
    }
}
