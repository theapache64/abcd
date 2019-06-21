package com.theapache64.abcd.ui.activities.styles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.ActivityStylesBinding
import com.theapache64.abcd.ui.adapters.StylesAdapter
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import java.io.File
import javax.inject.Inject

class StylesActivity : BaseAppCompatActivity() {

    enum class Mode {
        STYLE, ARTISTIC_STYLE
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    companion object {

        private const val KEY_MODE = "mode"
        private const val KEY_MAP_FILE = "map_file"


        fun getStartIntent(context: Context, mode: Mode, mapFile: File): Intent {
            return Intent(context, StylesActivity::class.java).apply {
                // data goes here
                putExtra(KEY_MODE, mode)
                putExtra(KEY_MAP_FILE, mapFile)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivityStylesBinding>(R.layout.activity_styles)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Getting param
        val mapFile = intent.getSerializableExtra(KEY_MAP_FILE) as File
        val mode = intent.getSerializableExtra(KEY_MODE) as Mode

        val viewModel = ViewModelProviders.of(this, factory).get(StylesViewModel::class.java)

        val titleRes: Int = when (mode) {

            Mode.STYLE -> {
                watchForStyles(viewModel, binding, mapFile)
                R.string.title_styles_activity
            }

            Mode.ARTISTIC_STYLE -> {
                watchForArtisticStyles(viewModel, binding)
                R.string.title_artistic_styles_activity
            }
        }

        supportActionBar!!.title = getString(titleRes)

    }

    private fun watchForArtisticStyles(
        viewModel: StylesViewModel,
        binding: ActivityStylesBinding
    ) {
        viewModel.getArtisticStyles().observe(this, Observer {
            binding.rvStyles.adapter = StylesAdapter(this, it) { position ->
                // artistic style clicked

            }
        })
    }

    private fun watchForStyles(
        viewModel: StylesViewModel,
        binding: ActivityStylesBinding,
        mapFile: File
    ) {
        viewModel.getStyles().observe(this, Observer {
            binding.rvStyles.adapter = StylesAdapter(this, it) { position ->
                // style clicked
                startActivity(getStartIntent(this, Mode.ARTISTIC_STYLE, mapFile))
            }
        })
    }
}
