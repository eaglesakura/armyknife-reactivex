apply(from = "../dsl/android-library.gradle")
apply(from = "../dsl/ktlint.gradle")
apply(from = "../dsl/bintray.gradle")

dependencies {
    "api"("com.eaglesakura.armyknife.armyknife-jetpack:armyknife-jetpack:1.4.4")

    "implementation"("androidx.activity:activity:1.1.0-rc03")
    "implementation"("androidx.activity:activity-ktx:1.1.0-rc03")
    "implementation"("androidx.annotation:annotation:1.1.0")
    "implementation"("androidx.arch.core:core-common:2.1.0")
    "implementation"("androidx.arch.core:core-runtime:2.1.0")
    "implementation"("androidx.core:core:1.1.0")
    "implementation"("androidx.core:core-ktx:1.1.0")
    "implementation"("androidx.collection:collection:1.1.0")
    "implementation"("androidx.collection:collection-ktx:1.1.0")
    "implementation"("androidx.fragment:fragment:1.2.0-rc04")
    "implementation"("androidx.fragment:fragment-ktx:1.2.0-rc04")
    "implementation"("androidx.lifecycle:lifecycle-extensions:2.1.0")
    "implementation"("androidx.lifecycle:lifecycle-viewmodel:2.1.0")
    "implementation"("androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0")
    "implementation"("androidx.lifecycle:lifecycle-runtime:2.1.0")
    "implementation"("androidx.lifecycle:lifecycle-common-java8:2.1.0")
    "implementation"("androidx.lifecycle:lifecycle-reactivestreams:2.1.0")
    "implementation"("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.1.0")

    /**
     * Reactive Extensions
     */
    "api"("io.reactivex.rxjava2:rxkotlin:2.4.0")
    "api"("io.reactivex.rxjava2:rxandroid:2.1.1")
}