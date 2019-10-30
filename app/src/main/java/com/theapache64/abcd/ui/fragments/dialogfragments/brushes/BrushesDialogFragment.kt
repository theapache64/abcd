package com.theapache64.abcd.ui.fragments.dialogfragments.brushes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.FragmentBrushesBinding
import com.theapache64.abcd.models.Brush
import com.theapache64.abcd.models.BrushCategory
import com.theapache64.abcd.ui.adapters.BrushesAdapter
import com.theapache64.abcd.ui.base.BaseDialogFragment
import com.theapache64.twinkill.logger.info
import com.theapache64.twinkill.utils.extensions.toast
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
            DataBindingUtil.inflate<FragmentBrushesBinding>(
                inflater,
                R.layout.fragment_brushes,
                container,
                false
            )

        val viewModel = ViewModelProviders.of(this, factory).get(BrushesViewModel::class.java)

        binding.viewModel = viewModel
        binding.handler = this

        // Brushes ready
        val brushesAdapter = BrushesAdapter(activity!!) { brush ->
            this.callback.onBrushSelected(brush)
            dismiss()
        }
        binding.rvBrushes.adapter = brushesAdapter

        binding.sCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selCat = binding.sCategories.selectedItem as BrushCategory
                toast("Sel cat : $selCat")
                viewModel.onCategoryChanged(selCat)
            }

        }

        // Watching for brush categories
        viewModel.getBrushCategories().observe(this, Observer { brushCategories ->

            val arrayAdapter = ArrayAdapter<BrushCategory>(
                activity!!,
                R.layout.simple_spinner_item,
                brushCategories
            )

            binding.sCategories.adapter = arrayAdapter

        })

        // Watching for brushes data
        viewModel.getBrushes().observe(this, Observer { brushes ->
            brushesAdapter.brushes = brushes
            brushesAdapter.notifyDataSetChanged()
        })

        // On category change
        viewModel.getActiveCategory().observe(this, Observer {
            binding.sCategories.setSelection(it.first)
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
