package martinezmencias.app.androidstudiotest.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import org.reactivestreams.Subscriber
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NetworkSimpleClient {

    //https://baconipsum.com/api/?type=all-meat&format=text

    fun getBaconText(observer: SingleObserver<String>) {
        makeAsyncRequest("https://baconipsum.com/api/?type=all-meat&format=text", observer)
    }

    private fun makeAsyncRequest(url: String, observer: SingleObserver<String>) {
        val single:Single<String> = Single.create { emitter ->
            val result = request("https://baconipsum.com/api/?type=all-meat&format=text")
            emitter.onSuccess(result ?: "")
        }
        single.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    private fun request(url: String): String? {
        var connection: HttpURLConnection? = null
        var response: String? = null
        var inputStream: InputStream? = null

        try {
            val imgUrl = URL(url)
            connection = imgUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            inputStream = connection.inputStream
            response = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                    connection?.disconnect()
                    inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return response
    }
}