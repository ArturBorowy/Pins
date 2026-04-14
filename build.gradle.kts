plugins {
    id("com.android.application") version "9.0.0" apply false
    id("com.android.library") version "9.0.0" apply false
    id("org.jetbrains.kotlin.android") version "2.3.10" apply false
    id("com.google.dagger.hilt.android") version "2.59.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.10" apply false
    id("com.google.devtools.ksp") version "2.3.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    source.setFrom(files(projectDir))
    config.setFrom("$projectDir/detekt/detekt.yml")
}
