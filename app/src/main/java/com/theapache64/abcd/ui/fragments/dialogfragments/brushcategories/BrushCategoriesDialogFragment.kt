package com.theapache64.abcd.ui.fragments.dialogfragments.brushcategories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.FragmentBrushCategoriesBinding
import com.theapache64.abcd.models.Brush
import com.theapache64.abcd.ui.adapters.BrushesPagerAdapter
import com.theapache64.abcd.ui.base.BaseDialogFragment
import com.theapache64.twinkill.logger.info
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class BrushCategoriesDialogFragment private constructor() : BaseDialogFragment(),
    BrushCategoriesHandler {


    companion object {

        val TAG = BrushCategoriesDialogFragment::class.java.simpleName

        fun newInstance(): BrushCategoriesDialogFragment {
            val fragment = BrushCategoriesDialogFragment()
            val bundle = Bundle().apply {
                // add data here
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    // To get callback from BrushCategoriesDialogFragment
    private lateinit var callback: Callback

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.callback = context as Callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =
            DataBindingUtil.inflate<FragmentBrushCategoriesBinding>(
                inflater,
                R.layout.fragment_brush_categories,
                container,
                false
            )

        val viewModel =
            ViewModelProviders.of(this, factory).get(BrushCategoriesViewModel::class.java)

        binding.viewModel = viewModel
        binding.handler = this

        binding.tlBrushCategories.setupWithViewPager(binding.vpBrushes)


        // Watching for brush categories
        viewModel.getBrushCategories().observe(this, Observer { brushCategories ->
            val adapter = BrushesPagerAdapter(childFragmentManager, brushCategories)
            binding.vpBrushes.adapter = adapter
        })

        return binding.root
    }


    override fun onDismissClicked() {
        dismiss()
    }

    interface Callback {
        fun onBrushSelected(brush: Brush)
    }

    override fun onCategorySelected(position: Int) {
        info("Selected $position")
    }


}
