package lt.andro.androidthingsdemo1

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import lt.andro.androidthingsdemo1.mvp.MainPresenter
import lt.andro.androidthingsdemo1.mvp.MainPresenterImpl
import lt.andro.androidthingsdemo1.mvp.MainView


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
class MainActivity : Activity(), MainView {
    private val TAG = "MainActivity"
    private val LED_GPIO = "BCM6"
    val presenter: MainPresenter by lazy { MainPresenterImpl(this) }

    private var mLedGpio: Gpio = PeripheralManagerService().openGpio(LED_GPIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Configuring GPIO pins")
        mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun setLedValue(value: Boolean) {
        mLedGpio.value = value
    }
}
