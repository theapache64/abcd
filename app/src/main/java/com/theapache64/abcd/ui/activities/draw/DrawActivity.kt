package com.theapache64.abcd.ui.activities.draw


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.ActivityDrawBinding
import com.theapache64.abcd.models.Brush
import com.theapache64.abcd.ui.fragments.dialogfragments.brushes.BrushesDialogFragment
import com.theapache64.abcd.ui.fragments.dialogfragments.brushsize.BrushSizeDialogFragment
import com.theapache64.abcd.ui.widgets.SpadeCanvas
import com.theapache64.abcd.utils.BrushUtils
import com.theapache64.twinkill.logger.info
import com.theapache64.twinkill.network.utils.Resource
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import javax.inject.Inject

class DrawActivity : BaseAppCompatActivity(),
    DrawHandler,
    BrushesDialogFragment.Callback,
    BrushSizeDialogFragment.Callback {


    companion object {
        const val ID = R.id.MAIN_ACTIVITY_ID

        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, DrawActivity::class.java)
            return intent
        }
    }


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: DrawViewModel
    private lateinit var spadeCanvas: SpadeCanvas

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding = bindContentView<ActivityDrawBinding>(R.layout.activity_draw)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // View refs
        this.spadeCanvas = binding.iContentDraw.spadeCanvas
        val lvSubmitMap = binding.iContentDraw.lvSubmitMap

        this.viewModel = ViewModelProviders.of(this, factory).get(DrawViewModel::class.java)
        binding.viewModel = viewModel
        binding.handler = this


        // Watching for map upload response
        viewModel.getSubmitMapResponse().observe(this, Observer {

            info(it.toString())

            when (it.status) {

                Resource.Status.LOADING -> {
                    lvSubmitMap.showLoading(R.string.message_uploading_map)
                }

                Resource.Status.SUCCESS -> {
                    // navigate to styles
                    lvSubmitMap.hideLoading()

                }

                Resource.Status.ERROR -> {
                    lvSubmitMap.showError(
                        getString(R.string.error_uploading_failed, it.message)
                    )
                }
            }
        })

        // Watching for sea and sky color
        viewModel.getSeaAndSky().observe(this, Observer {
            val sea = it.first
            val sky = it.second

            Handler().postDelayed({
                spadeCanvas.drawSkyAndSea(
                    Color.parseColor(sea.color),
                    Color.parseColor(sky.color)
                )
            }, 100)
        })

        // Setting default canvas properties
        spadeCanvas.paintStrokeWidth = BrushUtils.getDefaultBrushSize()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_draw, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            // Choose brush here
            R.id.action_choose_brush -> {
                showBrushesFragment()
                return true
            }

            // Choose brush size
            R.id.action_change_brush_size -> {
                showBrushChooser()
                return true
            }

            // Undo canvas
            R.id.action_undo -> {
                spadeCanvas.undo()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBrushChooser() {
        val brushSizeFragment = BrushSizeDialogFragment.newInstance(spadeCanvas.paintStrokeWidth)
        brushSizeFragment.show(supportFragmentManager, BrushSizeDialogFragment.TAG)
    }

    private fun showBrushesFragment() {
        val brushesFragment = BrushesDialogFragment.newInstance()
        brushesFragment.show(supportFragmentManager, BrushesDialogFragment.TAG)
    }

    override fun onBrushSelected(brush: Brush) {
        supportActionBar!!.subtitle = "Brush : ${brush.name}"
        spadeCanvas.paintStrokeColor = Color.parseColor(brush.color)
    }

    override fun onBrushSizeChanged(brushSize: Float) {
        spadeCanvas.paintStrokeWidth = brushSize
    }

    override fun onNextClicked() {

        // Upload image
        val base64Image = Base64.encodeToString(spadeCanvas.bitmapAsByteArray, Base64.DEFAULT)
        val name = "${System.nanoTime()}abcd${System.currentTimeMillis()}"

        viewModel.submitMap(name, base64Image)

        /*startActivity(
            StylesActivity.getStartIntent(
                this,
                StylesActivity.Mode.STYLE,
                File("")
            )
        )*/
    }

}
