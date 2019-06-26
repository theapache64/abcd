package com.theapache64.abcd.ui.activities.styles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.ActivityStylesBinding
import com.theapache64.abcd.ui.activities.result.ResultActivity
import com.theapache64.abcd.ui.adapters.StylesAdapter
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import java.io.File
import javax.inject.Inject

class StylesActivity : BaseAppCompatActivity() {


    private lateinit var mapFile: File
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    companion object {

        private const val KEY_MAP_FILE = "map_file"


        fun getStartIntent(context: Context, mapFile: File): Intent {
            return Intent(context, StylesActivity::class.java).apply {
                // data goes here
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
        this.mapFile = intent.getSerializableExtra(KEY_MAP_FILE) as File

        val viewModel = ViewModelProviders.of(this, factory).get(StylesViewModel::class.java)

        // Watching for styles
        viewModel.getStyles().observe(this, Observer { styles ->
            binding.rvStyles.adapter = StylesAdapter(this, styles) { position ->
                // style clicked
                val style = styles[position]
                startActivity(
                    ResultActivity.getStartIntent(this, mapFile, style)
                )
            }
        })

    }

}
