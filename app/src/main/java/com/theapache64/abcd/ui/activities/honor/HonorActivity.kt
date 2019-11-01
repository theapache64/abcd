package com.theapache64.`abcd`.ui.activities.honor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.`abcd`.R
import com.theapache64.`abcd`.databinding.ActivityHonorBinding
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import javax.inject.Inject

class HonorActivity : BaseAppCompatActivity(), HonorHandler {

    companion object {

        const val BMAC_URL = "https://www.buymeacoff.ee/theapache64"
        const val PAYPAL_URL = "https://www.paypal.me/theapache64"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, HonorActivity::class.java).apply {
                // data goes here
            }
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: ActivityHonorBinding
    private lateinit var viewModel: HonorViewModel

    private val data = """
        
         <p>
             <span>👋 Hi from `abcd` <span> <span>We hope you enjoy using this app!</span>
         </p>
         
         <div>`abcd` is honor-ware, which means that we <b>trust each other</b> to be nice:</div>
         <div>
             <ul>
                <li>You <b>pay what you can afford</b> for `abcd`. We let <b>you</b> choose the price.</li>
                <li>We'll send you a <b>thank you note</b> for paying.</li>
                <li>You get a <b>refund</b> - and you can <b>keep `abcd`</b> - if you don't <i>love</i> it.</li>
             </ul>
         </div>
         
         <p>
             `abcd` uses <b>powerful servers </b> to render your output images. The survival of this app depends on your contributions and support.  We're
             trusting you a <i>lot</i>, but we think it's the decent thing to do.
         </p>
         
         <p>
            <span>Please pay as much as you can, since if you change your mind you have <b>60 days</b> to get a refund!</span>
         </p>
         
         <p>
            <span>You can support me by simply <b>buy me a coffee</b> or If you want to donate something big, you can choose <b>PayPal</b>.</span>
         </p>
         
         <p>
         
         
         Peace
         -theapache64
         </p>

    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = bindContentView(R.layout.activity_honor)
        viewModel = ViewModelProviders.of(this, factory).get(HonorViewModel::class.java)

        binding.handler = this
        binding.viewModel = viewModel

        binding.tvContent.text = HtmlCompat.fromHtml(
            data,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    override fun onDonateClicked() {
        browse(PAYPAL_URL)
    }


    override fun onBuyMeACoffeeClicked() {
        browse(BMAC_URL)
    }

    private fun browse(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }

        startActivity(intent)
        finish()
    }


}
