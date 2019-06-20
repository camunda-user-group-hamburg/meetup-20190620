plugins {
    base
    idea

}

allprojects {
    group = "de.cughh.meetup"
    version = "20190620"

    apply {
        from("${rootProject.rootDir}/gradle/repositories.gradle.kts")
    }

}


dependencies {
    subprojects.forEach {
        archives(it)
    }
}


