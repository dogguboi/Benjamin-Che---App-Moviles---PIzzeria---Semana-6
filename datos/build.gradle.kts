plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.pizzeria.datos"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // El módulo de datos usa las validaciones del módulo de utilidades
    implementation(project(":utilidades"))
    // Coroutines: las funciones del repositorio son suspend
    implementation(libs.kotlinx.coroutines.core)
}
