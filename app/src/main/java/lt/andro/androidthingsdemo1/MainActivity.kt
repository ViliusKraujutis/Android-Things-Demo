package lt.andro.androidthingsdemo1

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import java.util.*


/**
 * Skeleton of an Android Things activity.
 *
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 *
 * <pre>`PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
`</pre> *
 *
 *
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.

 * @see [https://github.com/androidthings/contrib-drivers.readme](https://github.com/androidthings/contrib-drivers.readme)
 */
class MainActivity : Activity() {
    private val TAG = "MainActivity"
    private val LED_GPIO = "BCM6"

    private val handler = Handler()
    private var mLedGpio: Gpio = PeripheralManagerService().openGpio(LED_GPIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Configuring GPIO pins")
        mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        setLedValue();
    }

    private fun setLedValue() {
        mLedGpio.value = !mLedGpio.value
        handler.postDelayed({ setLedValue() }, 100 + Random().nextInt(500).toLong());
    }
}
