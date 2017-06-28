package lt.andro.androidthingsdemo1.mvp

import android.os.Handler
import android.util.Log
import lt.andro.androidthingsdemo1.api.OpenWeatherMapApi
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author Vilius Kraujutis
 * @since 2017-06-28
 */
class MainPresenterImpl(val view: MainView) : BasePresenterImpl(), MainPresenter {
    private val handler = Handler()
    private var alive = true
    val api = OpenWeatherMapApi.api

    override fun onAttach() {
        alive = true
        loadWeatherData()
    }

    private fun loadWeatherData() {
        setLedValue(true)

        add(api.getForecast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { flashLed(50) }
                .doOnCompleted {
                    stopFlashingLed()
                    reloadWeatherData(5000)
                }
                .subscribe(
                        {
                            Log.d("ForecastJava", "Weather forecast is $it in Vilnius")
                        },
                        {
                            flashLed(500)
                            it.printStackTrace()
                        }
                ))
    }

    private fun stopFlashingLed() {
        flashLed(0)
    }

    private var flashSpeed: Long = 0

    private fun flashLed(speed: Long) {
        flashSpeed = speed
        flashLed()
    }

    private fun flashLed() {
        if (flashSpeed == 0L) {
            setLedValue(false)
            return
        }

        setLedValue(!view.isLedOn())

        handler.postDelayed({ flashLed() }, flashSpeed)
    }

    private fun reloadWeatherData(delay: Long) {
        if (alive)
            handler.postDelayed({ loadWeatherData() }, delay)
    }

    fun setLedValue(isOn: Boolean) {
        view.switchLed(isOn)
    }

    override fun onDetach() {
        alive = false
    }
}

interface MainPresenter : BasePresenter {
}

interface MainView {
    fun switchLed(isOn: Boolean)
    fun isLedOn(): Boolean
}