package com.example.grpcmarshaller

import com.google.protobuf.DescriptorProtos.FileDescriptorSet
import com.google.protobuf.Descriptors.FileDescriptor
import com.google.protobuf.Descriptors.ServiceDescriptor
import java.io.FileInputStream

class DescriptorLoader {

    private val descriptors: FileDescriptorSet

    init {
        val desc =
            FileInputStream("/Users/jeff.fang/Downloads/grpc-marshaller/build/generated/source/proto/main/descriptor_set.desc")
        descriptors = desc.use(FileDescriptorSet::parseFrom)
    }

    fun getServiceDescriptor(serviceName: String) : ServiceDescriptor {
        val files : MutableMap<String, FileDescriptor>  = mutableMapOf()

        descriptors.fileList.forEach {
            val deps = it.dependencyList.mapNotNull { files[it] }
            files[it.name] = FileDescriptor.buildFrom(it, deps.toTypedArray())
        }


        val services = files.values.flatMap { it.services }

        return services.first { it.fullName == serviceName }
    }
}
