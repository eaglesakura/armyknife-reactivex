package com.eaglesakura.armyknife.android.reactivex

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.extensions.assertUIThread
import com.eaglesakura.armyknife.android.junit4.TestDispatchers
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import com.eaglesakura.armyknife.runtime.extensions.use
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RxStreamTest {

    @Test
    fun channel_background() = compatibleBlockingTest(TestDispatchers.Default) {
        val stream = RxStream.create<String>()
        val channel = stream.toChannel()
        stream.next("send1")
        assertThat(channel.receive()).apply {
            isEqualTo("send1")
        }
        channel.close()
    }

    @Test
    fun channel_ui() = compatibleBlockingTest(TestDispatchers.Main) {
        val stream = RxStream.create<String>()
        val channel = stream.toChannel()
        stream.next("send1")
        yield()
        assertThat(channel.receive()).apply {
            isEqualTo("send1")
        }
        channel.close()
    }

    @Test
    fun channel_multi() = compatibleBlockingTest(TestDispatchers.Main) {
        val stream = RxStream.create<String>()
        stream.toChannel().use {
            stream.next("send1")
            assertEquals("send1", it.receive())

            stream.next("send2")
            assertEquals("send2", it.receive())

            stream.next("send3")
            assertEquals("send3", it.receive())
        }
    }

    @Test
    fun subscribe() = compatibleBlockingTest(TestDispatchers.Default) {
        val stream = RxStream.create<String>()
        val channel = Channel<Unit>()
        stream.subscribe { }
        val disposable = stream.subscribe {
            assertUIThread()
            assertThat(it).apply {
                startsWith("send")
            }

            if (it.endsWith("finish")) {
                GlobalScope.launch { channel.send(Unit) }
            }
        }

        stream.next("send1")
        stream.next("send2")
        stream.next("send3")
        stream.next("send_finish")

        channel.receive() // await.
        disposable.dispose()
    }

    @Test
    fun builder_transform() = compatibleBlockingTest(Dispatchers.Main) {
        val stream = RxStream.Builder<String>().apply {
            observableTransform = { origin ->
                origin.distinctUntilChanged()
            }
        }.build()

        stream.toChannel().use {
            stream.next("value1")
            assertEquals("value1", it.receive())

            stream.next("value2")
            assertEquals("value2", it.receive())

            // Do not receive
            stream.next("value2")
            try {
                withTimeout(TimeUnit.SECONDS.toMillis(1)) {
                    it.receive()
                }
                fail("Do not receive.")
            } catch (err: TimeoutCancellationException) {
                // ok, do not receive.
            }

            // receive.
            stream.next("value1")
            assertEquals("value1", it.receive())
        }
    }
}