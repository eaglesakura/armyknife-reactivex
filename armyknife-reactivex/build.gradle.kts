apply(from = "../dsl/android-library.gradle")
apply(from = "../dsl/ktlint.gradle")
apply(from = "../dsl/bintray.gradle")

dependencies {
    "api"("com.eaglesakura.armyknife.armyknife-jetpack:armyknife-jetpack:1.3.0")

    /**
     * Reactive Extensions
     */
    "api"("io.reactivex.rxjava2:rxkotlin:2.3.0")
    "api"("io.reactivex.rxjava2:rxandroid:2.1.1")
}