package com.theapache64.abcd.ui.activities.draw


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.ActivityDrawBinding
import com.theapache64.abcd.models.Brush
import com.theapache64.abcd.ui.fragments.dialogfragments.brushes.BrushesFragment
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import javax.inject.Inject

class DrawActivity : BaseAppCompatActivity(), BrushesFragment.Callback {

    companion object {
        const val ID = R.id.MAIN_ACTIVITY_ID

        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, DrawActivity::class.java)
            return intent
        }
    }

    private lateinit var binding: ActivityDrawBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: DrawViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        this.binding = bindContentView(R.layout.activity_draw)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        this.viewModel = ViewModelProviders.of(this, factory).get(DrawViewModel::class.java)
        binding.viewModel = viewModel

        showBrushesFragment()

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
                binding.iContentDraw.spadeCanvas.undo()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBrushChooser() {

    }

    private fun showBrushesFragment() {
        val brushesFragment = BrushesFragment.newInstance()
        brushesFragment.show(supportFragmentManager, BrushesFragment.TAG)
    }

    override fun onBrushSelected(brush: Brush) {
        supportActionBar!!.subtitle = "Brush : ${brush.name}"
    }

}
