package lt.andro.androidthingsdemo1.mvp

import rx.Subscription
import rx.subscriptions.CompositeSubscription

interface BasePresenter {
    fun onAttach()
    fun onDetach()
}

open class BasePresenterImpl : BasePresenter {
    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    fun add(subscribe: Subscription?) {
        subscribe?.let { compositeSubscription.add(it) }
    }

    override fun onAttach() {}

    override fun onDetach() {
        clearSubscriptions()
    }

    fun clearSubscriptions() {
        compositeSubscription.clear()
    }

}