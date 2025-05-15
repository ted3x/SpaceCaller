plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.space.mylibrary"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    productFlavors {
        flavorDimensionList.add("store")
        create("google") {
            dimension = "store"
        }
        create("huawei") {
            dimension = "store"
        }
    }

    buildTypes {
        create("qa") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    publishing {
        singleVariant("googleRelease") {
            withSourcesJar()
        }
        singleVariant("huaweiRelease") {
            withSourcesJar()
        }
        singleVariant("googleQa") {
            withSourcesJar()
        }
        singleVariant("huaweiQa") {
            withSourcesJar()
        }
    }
}
dependencies {

}
afterEvaluate {
    publishing {
        publications {
            // 💡 Name doesn't matter — artifactId does
            create<MavenPublication>("default") {
                groupId = "com.my-company"
                artifactId = "mylibrary" // ✅ shared name for all variants
                version = "1.0"
                // 👇 dynamically match the variant
                from(components["googleQa"]) // change per publish step
            }
        }
    }
}