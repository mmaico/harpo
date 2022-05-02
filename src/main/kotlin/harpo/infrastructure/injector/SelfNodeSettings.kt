package harpo.infrastructure.injector

import java.util.concurrent.TimeUnit

data class SelfNodeSettings(val alpha: Int = 5,
                              val identifierSize: Int = 128,
                              val bucketSize: Int = 3,
                              val findNodeSize: Int = 20,
                              val maximumLastSeenAgeToConsiderAlive: Int = 20,
                              val pingScheduleTimeValue: Int = 10,
                              val pingScheduleTimeUnit: TimeUnit = TimeUnit.SECONDS,
                              val dhtExecutorPoolSize: Int = 20,
                              val dhtScheduleExecutorPoolSize: Int = 5,
                              val maximumStoreAndLookupTimeoutValue: Int = 1,
                              val maximumStoreAndLookupTimeoutTimeUnit: TimeUnit = TimeUnit.MINUTES,
                              val enabledFirstStoreRequestForcePass: Boolean = true) {
    companion object {
        fun getDefault(): SelfNodeSettings = SelfNodeSettings()
    }
}