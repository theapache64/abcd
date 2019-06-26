package com.theapache64.abcd.ui.fragments.dialogfragments.brushes

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
import com.theapache64.abcd.databinding.FragmentBrushesBinding
import com.theapache64.abcd.models.Brush
import com.theapache64.abcd.ui.adapters.BrushesAdapter
import com.theapache64.abcd.ui.base.BaseDialogFragment
import com.theapache64.twinkill.logger.info
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class BrushesDialogFragment private constructor() : BaseDialogFragment(),
    BrushesHandler {


    companion object {

        val TAG = BrushesDialogFragment::class.java.simpleName

        fun newInstance(): BrushesDialogFragment {
            val fragment = BrushesDialogFragment()
            val bundle = Bundle().apply {
                // add data here
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    // To get callback from BrushesDialogFragment
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
            DataBindingUtil.inflate<FragmentBrushesBinding>(inflater, R.layout.fragment_brushes, container, false)

        val viewModel = ViewModelProviders.of(this, factory).get(BrushesViewModel::class.java)

        binding.viewModel = viewModel
        binding.handler = this

        // Watching for brushes data
        viewModel.getBrushes().observe(this, Observer { brushes ->

            info("Brushes loaded $brushes")

            // Brushes ready
            val brushesAdapter = BrushesAdapter(activity!!, brushes) { position ->
                this.callback.onBrushSelected(brushes[position])
                dismiss()
            }

            binding.rvBrushes.adapter = brushesAdapter
        })


        return binding.root
    }


    override fun onDismissClicked() {
        dismiss()
    }

    interface Callback {
        fun onBrushSelected(brush: Brush)
    }

}
