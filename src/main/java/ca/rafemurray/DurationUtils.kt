package ca.rafemurray

import com.mojang.serialization.Codec
import java.time.Duration

object DurationUtils {
    @JvmField
    val CODEC: Codec<Duration> = Codec.LONG.xmap(
        { millis: Long? -> Duration.ofMillis(millis!!) },
        { obj: Duration -> obj.toMillis() }
    )
}
