import com.google.protobuf.gradle.GenerateProtoTask.PluginOptions
import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.4"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	kotlin("jvm") version "1.7.20"
	kotlin("kapt") version "1.7.20"
	kotlin("plugin.spring") version "1.7.20"
	id("com.google.protobuf") version "0.9.1"
	idea
	kotlin("plugin.serialization") version "1.7.20"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("io.grpc:grpc-netty-shaded")
	implementation("io.grpc:grpc-protobuf")
	implementation("io.grpc:grpc-kotlin-stub")
	implementation("io.grpc:grpc-services")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
	implementation("com.google.protobuf:protobuf-kotlin")
	implementation("com.airwallex.grpc:grpc-core")
	implementation(platform("com.airwallex.grpc:grpc-bom:1.4.0-SNAPSHOT"))
	implementation("pro.streem.pbandk:pbandk-runtime")
	protobuf("com.airwallex.grpc:protobuf-types:1.4.0-SNAPSHOT") {
		isTransitive = false
	}
	kapt(platform("com.airwallex.grpc:grpc-bom:1.4.0-SNAPSHOT"))
	kapt("com.airwallex.grpc:annotation-processor")
//	implementation("pro.streem.pbandk:protoc-gen-pbandk-lib:0.14.1")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("com.airwallex.grpc:grpc-spring-boot-starter")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.4.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.4.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.21.7"
	}
	plugins {
		id("pbandk") {
			artifact = "pro.streem.pbandk:protoc-gen-pbandk-jvm:0.14.1:jvm8@jar"
		}
	}
	generateProtoTasks {
		all().configureEach {
			generateDescriptorSet = true
			// descriptorSetOptions.path = "$projectDir/build/classes/kotlin/${sourceSet.name}/proto.desc"
			descriptorSetOptions.includeImports = true

			builtins {
				remove(PluginOptions("java"))
			}

			plugins {
				id("pbandk")
			}
		}
	}
}
