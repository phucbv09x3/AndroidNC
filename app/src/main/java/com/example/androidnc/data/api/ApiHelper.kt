package com.example.androidnc.data.api

import com.rx2androidnetworking.Rx2ANRequest
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

fun <T> Observable<T>.runOnMain(): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun ApiEndPoint.postRequest(): Rx2ANRequest.PostRequestBuilder {
    return Rx2AndroidNetworking.post(url())
        .addHeaders("Content-Type", "application/json")
}

fun ApiEndPoint.getRequest(): Rx2ANRequest.GetRequestBuilder {
    return Rx2AndroidNetworking.get(url())
        .addHeaders("Content-Type", "application/json")
}

fun <T> Rx2ANRequest.PostRequestBuilder.get(clazz: Class<T>): Observable<T> {
    return this.build()
        .getObjectObservable(clazz)
}

fun <T> Rx2ANRequest.GetRequestBuilder.get(clazz: Class<T>): Observable<T> {
    return this.build()
        .getObjectObservable(clazz)
}


enum class ApiEndPoint(private val url: String) {

    GetRepoGit("search/repositories"),
    Login("");

    fun url(): String {
        return "https://api.github.com/$url"
    }
}

class ApiHelper @Inject constructor(private val apiHeader: AppHeader) : IApiHelper {
    override fun get(): String {
        return ""
    }
}


