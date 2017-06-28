package lt.andro.androidthingsdemo1.mvp

import android.os.Handler
import java.util.*

/**
 * @author Vilius Kraujutis
 * @since 2017-06-28
 */
class MainPresenterImpl(val view: MainView) : MainPresenter {
    private val handler = Handler()
    private var alive = true

    override fun onAttach() {
        alive = true
        setLedValue(true)
    }

    fun setLedValue(value: Boolean) {
        view.setLedValue(value)

        if (alive)
            handler.postDelayed({ setLedValue(!value) }, 100 + Random().nextInt(500).toLong())
    }

    override fun onDetach() {
        alive = false
    }

}

interface MainPresenter : BasePresenter {
}

interface MainView {
    fun setLedValue(value: Boolean)
}