plugins {
    id("java")
}

group = "org.qiuhua.qiuhuacustomcollect"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()  //加载本地仓库
    mavenCentral()  //加载中央仓库
    maven {
        name = "spigotmc-repo"
        url = uri ("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }  //SpigotMC仓库
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    compileOnly ("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")  //仅在编译时可用
    compileOnly (fileTree("src/libs/ItemsAdder_3.5.0.jar"))  //仅在编译时可用
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
tasks.withType<Jar>().configureEach {
    archiveFileName.set("QiuhuaCustomCollect-测试插件.jar")
    destinationDirectory.set(File ("D:/测试服务端/plugins"))
}

tasks.withType<JavaCompile>{
    options.encoding = "UTF-8"
}