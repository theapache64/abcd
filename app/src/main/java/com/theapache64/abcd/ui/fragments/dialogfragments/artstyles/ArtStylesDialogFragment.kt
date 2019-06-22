package com.theapache64.abcd.ui.fragments.dialogfragments.artstyles


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.FragmentArtStylesDialogBinding
import com.theapache64.abcd.models.Style
import com.theapache64.abcd.ui.adapters.StylesAdapter
import com.theapache64.abcd.ui.base.BaseDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**f
 * To select art styles
 */
class ArtStylesDialogFragment : BaseDialogFragment(), ArtStylesHandler {

    private lateinit var callback: Callback

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    companion object {

        val TAG = ArtStylesDialogFragment::class.java.simpleName

        fun newInstance(): ArtStylesDialogFragment {
            val fragment = ArtStylesDialogFragment()
            val bundle = Bundle().apply {
                // add data here
            }
            fragment.arguments = bundle
            return fragment
        }
    }

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
        val binding = DataBindingUtil.inflate<FragmentArtStylesDialogBinding>(
            inflater,
            R.layout.fragment_art_styles_dialog,
            container,
            false
        )

        binding.handler = this

        val viewModel = ViewModelProviders.of(this, factory).get(ArtStylesViewModel::class.java)
        viewModel.getArtStyles().observe(this, Observer { artStyles ->
            binding.rvArtStyles.adapter = StylesAdapter(activity!!, artStyles) { position ->
                onArtStyleClicked(artStyles[position])
            }
        })

        return binding.root
    }

    private fun onArtStyleClicked(style: Style) {
        callback.onArtStyleSelected(style)
        dismiss()
    }

    override fun onDismissClicked() {
        dismiss()
    }

    interface Callback {
        fun onArtStyleSelected(style: Style)
    }

}
