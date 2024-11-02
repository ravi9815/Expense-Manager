pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven (  url ="https://jitpack.io" )
        maven(
            "https://maven.google.com"
        )
    }
}

rootProject.name = "Easy Khatabahi"
include(":app")
 