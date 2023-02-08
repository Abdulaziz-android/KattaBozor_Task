object Dependencies {

    object Retrofit {
        private const val version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converter_gson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Room {
        private const val version = "2.5.0"

        const val room = "androidx.room:room-ktx:$version"
        const val kapt = "androidx.room:room-compiler:$version"
    }

    object Hilt {
        private const val version = "2.44.2"

        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val kapt_compiler = "com.google.dagger:hilt-android-compiler:$version"
    }
}