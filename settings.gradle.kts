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
    }
}

rootProject.name = "PizzeriaApp"

// Estructura modular del proyecto
include(":app")         // Interfaz de usuario (Jetpack Compose) y MainActivity
include(":datos")       // Cliente, Producto, Pedido y repositorio
include(":utilidades")  // Validaciones y formato reutilizables
