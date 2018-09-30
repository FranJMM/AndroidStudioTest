package martinezmencias.app.androidstudiotest

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.arch.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_jet_pack.*


class JetPackFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jet_pack, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProviders.of(this).get(JetPackViewModel::class.java)
        model.message.observe(this, getMessageObserver())
    }

    private fun getMessageObserver():Observer<String> {
        return Observer{ message ->
            welcomeJetPackMessage.text = message
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = JetPackFragment()
    }
}
