package com.theapache64.abcd.ui.activities.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.ActivityResultBinding
import com.theapache64.abcd.models.Style
import com.theapache64.twinkill.network.utils.Resource
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import java.io.File
import javax.inject.Inject

class ResultActivity : BaseAppCompatActivity() {

    companion object {

        private const val KEY_MAP_FILE = "map_file"
        private const val KEY_STYLE = "style"
        private const val KEY_ARTISTIC_STYLE = "artistic_style"

        fun getStartIntent(context: Context, mapFile: File, style: Style, artisticStyle: Style): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                // data goes here
                putExtra(KEY_MAP_FILE, mapFile)
                putExtra(KEY_STYLE, style)
                putExtra(KEY_ARTISTIC_STYLE, artisticStyle)
            }
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivityResultBinding>(R.layout.activity_result)
        setSupportActionBar(binding.toolbar)

        val viewModel = ViewModelProviders.of(this, factory).get(ResultViewModel::class.java)
        binding.viewModel = viewModel

        // Getting params
        val mapFile = intent.getSerializableExtra(KEY_MAP_FILE) as File
        val style = intent.getSerializableExtra(KEY_STYLE) as Style
        val artisticStyle = intent.getSerializableExtra(KEY_ARTISTIC_STYLE) as Style

        viewModel.init(mapFile, style, artisticStyle)

        val lvReceiveImage = binding.iContentResult.lvReceiveImage

        // Get bitmap
        viewModel.getGauganOutput().observe(this, Observer { bitmap ->

            when (bitmap.status) {
                Resource.Status.LOADING -> {
                    lvReceiveImage.showLoading(R.string.message_loading_image)
                }
                Resource.Status.SUCCESS -> {
                    lvReceiveImage.hideLoading()

                    binding.iContentResult.ivGauganOutput.setImageBitmap(bitmap.data)
                }
                Resource.Status.ERROR -> {
                    lvReceiveImage.showError(bitmap.message!!)
                }
            }
        })

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

}
