package lt.andro.androidthingsdemo1.mvp

import android.os.Handler
import android.util.Log
import lt.andro.androidthingsdemo1.api.OpenWeatherMapApi
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

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
        clearSubscriptions()
        setLedValue(true)

        add(api.getForecast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { flashLed(50) }
                .doOnSuccess({
                    val mostRain = it.list?.take(5)?.map { Pair(it, it.rain?.period3h) }?.maxBy { it.second ?: 0.0 }?.first
                    Log.d("Forecast", "Weather forecast is $mostRain in Vilnius")

                    val rain = mostRain!!.rain!!.period3h ?: 0.0

                    when (rain) {
                        in 0.0..0.3 -> flashLed(100)
                        in 0.3..1.5 -> flashLed(500)
                        else -> flashLed(800)
                    }
                })
                .doOnError({
                    flashLed(300)
                    it.printStackTrace()
                })
                .toCompletable()
                .doOnCompleted {
                    reloadWeatherData(15000)
                }
                .subscribe())
    }

    private var flashHeartBeat: Subscription? = null
    private fun flashLed(speed: Long) {
        flashHeartBeat?.unsubscribe()
        flashHeartBeat = Observable
                .interval(500, speed, TimeUnit.MILLISECONDS)
                .doOnNext {
                    println("flashLed $this : $it")
                    toggleLed()
                }
                .subscribe()

        add(flashHeartBeat)
    }

    private fun toggleLed() {
        val isOnNow = !view.isLedOn()
        println("$this LED now is ${if (isOnNow) "ON" else "OFF"}")
        setLedValue(isOnNow)
    }

    private fun reloadWeatherData(delay: Long) {
        if (alive)
            handler.postDelayed(this::loadWeatherData, delay)
    }

    fun setLedValue(isOn: Boolean) {
        view.switchLed(isOn)
    }

    override fun onDetach() {
        alive = false
    }
}

interface MainPresenter : BasePresenter

interface MainView {
    fun switchLed(isOn: Boolean)
    fun isLedOn(): Boolean
}