package lt.andro.androidthingsdemo1.mvp

import rx.Subscription
import rx.subscriptions.CompositeSubscription

interface BasePresenter {
    fun onAttach()
    fun onDetach()
}

open class BasePresenterImpl : BasePresenter {
    private val compositeDisposable: CompositeSubscription = CompositeSubscription()

    fun add(subscribe: Subscription) {
        compositeDisposable.add(subscribe)
    }

    override fun onAttach() {}

    override fun onDetach() {
        compositeDisposable.clear()
    }

}