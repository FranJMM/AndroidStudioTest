package martinezmencias.app.androidstudiotest

import android.arch.lifecycle.AndroidViewModel
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.content_main.*
import martinezmencias.app.androidstudiotest.utils.NetworkSimpleClient


class JetPackViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    val message = MutableLiveData<String>()

    init {
        message.value = "Pidiendo datos al backend"

        // El repositorio (NetworkSimpleClient) recibe un SingleObserver pero podría recibir
        // directamente el MutableLiveData message. Al recibir el observer, se añade la posibilidad
        // de hacer un procesado previo de los datos (SE RECIBIÓ).

        val networkSimpleClient = NetworkSimpleClient()
        val observer:SingleObserver<String> = object : SingleObserver<String> {
            override fun onSuccess(result: String) {
                message.value = "SE RECIBIÓ: $result"
            }
            override fun onError(error: Throwable) {
                // Nothing
            }
            override fun onSubscribe(disposable: Disposable) {
                // Nothing
            }
        }
        networkSimpleClient.getBaconText(observer)
    }
}
