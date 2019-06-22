package com.theapache64.abcd.ui.activities.result

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.R
import com.theapache64.abcd.data.remote.receiveimage.ReceiveImageRequest
import com.theapache64.abcd.data.repositories.StyleRepository
import com.theapache64.abcd.databinding.ActivityResultBinding
import com.theapache64.abcd.models.Style
import com.theapache64.abcd.ui.fragments.dialogfragments.artstyles.ArtStylesDialogFragment
import com.theapache64.twinkill.logger.info
import com.theapache64.twinkill.network.utils.Resource
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.ui.widgets.LoadingView
import com.theapache64.twinkill.utils.extensions.bindContentView
import com.theapache64.twinkill.utils.extensions.toast
import dagger.android.AndroidInjection
import java.io.File
import javax.inject.Inject

class ResultActivity : BaseAppCompatActivity(), ArtStylesDialogFragment.Callback {


    companion object {

        private const val KEY_MAP_FILE = "map_file"
        private const val KEY_STYLE = "style"

        fun getStartIntent(context: Context, mapFile: File, style: Style): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                // data goes here
                putExtra(KEY_MAP_FILE, mapFile)
                putExtra(KEY_STYLE, style)
            }
        }
    }

    private lateinit var viewModel: ResultViewModel
    private lateinit var imageRequest: ReceiveImageRequest
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var stylesRepository: StyleRepository


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivityResultBinding>(R.layout.activity_result)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Getting params
        val mapFile = intent.getSerializableExtra(KEY_MAP_FILE) as File
        val style = intent.getSerializableExtra(KEY_STYLE) as Style
        this.imageRequest = ReceiveImageRequest(mapFile, style, stylesRepository.getNoArtStyle())


        this.viewModel = ViewModelProviders.of(this, factory).get(ResultViewModel::class.java)
        viewModel.init(mapFile, style)

        binding.viewModel = viewModel

        val lvReceiveImage = binding.iContentResult.lvReceiveImage
        lvReceiveImage.setTextColor(android.R.color.white)
        lvReceiveImage.setRetryCallback(object : LoadingView.RetryCallback {
            override fun onRetryClicked() {
                viewModel.loadOutput(imageRequest)
            }
        })


        val ivGauganOutput = binding.iContentResult.ivGauganOutput

        // Get bitmap
        viewModel.getGauganOutput().observe(this, Observer { bitmap ->

            when (bitmap.status) {

                Resource.Status.LOADING -> {
                    ivGauganOutput.visibility = View.GONE
                    lvReceiveImage.showLoading(R.string.message_loading_image)
                }
                Resource.Status.SUCCESS -> {
                    ivGauganOutput.visibility = View.VISIBLE
                    lvReceiveImage.hideLoading()

                    ivGauganOutput.setImageBitmap(bitmap.data)
                }
                Resource.Status.ERROR -> {
                    ivGauganOutput.visibility = View.GONE
                    lvReceiveImage.showError(bitmap.message!!)
                }
            }
        })

        // Setting refresh listener
        binding.iContentResult.srlOutput.setOnRefreshListener {
            viewModel.loadOutput(imageRequest)
        }

        binding.fab.setOnTouchListener { v, event ->

            info(event.toString())

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    info("Showing input")
                    viewModel.setInputVisible(true)
                }

                MotionEvent.ACTION_UP -> {
                    info("Hiding input")
                    viewModel.setInputVisible(false)
                }
            }

            false
        }

        // Finally
        viewModel.loadOutput(imageRequest)

        Handler().postDelayed({
            updateSubtitle(style, null)
        }, 100)
    }

    private fun updateSubtitle(style: Style, artStyle: Style?) {
        val subTitle = if (artStyle == null || artStyle.code == StyleRepository.CODE_NONE) {
            style.name
        } else {
            "${style.name} + ${artStyle.name}"
        }

        supportActionBar!!.subtitle = subTitle
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {

            R.id.action_art_styles -> {
                showArtStyleDialogFragment()
                return true
            }

            R.id.action_share -> {
                showShareDialog()
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showShareDialog() {
        toast("coming soon")
    }

    private fun showArtStyleDialogFragment() {
        ArtStylesDialogFragment.newInstance().show(supportFragmentManager, ArtStylesDialogFragment.TAG)
    }

    override fun onArtStyleSelected(artStyle: Style) {
        this.imageRequest.artStyle = artStyle
        viewModel.loadOutput(imageRequest)

        updateSubtitle(viewModel.style, artStyle)
    }

}
