package com.theapache64.abcd.ui.fragments.dialogfragments.brushsize


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.FragmentBrushSizeDialogBinding
import com.theapache64.abcd.ui.base.BaseDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class BrushSizeDialogFragment : BaseDialogFragment(), BrushSizeHandler {


    private lateinit var viewModel: BrushSizeViewModel
    private lateinit var callback: Callback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.callback = context as Callback
    }

    companion object {

        const val BRUSH_SIZE_FACTOR = 3f
        const val KEY_CURRENT_BRUSH_SIZE = "current_brush_size"
        val TAG = BrushSizeDialogFragment::class.java.simpleName

        fun newInstance(currentBrushSize: Float): BrushSizeDialogFragment {
            val fragment = BrushSizeDialogFragment()
            val bundle = Bundle().apply {
                // add data here
                putFloat(KEY_CURRENT_BRUSH_SIZE, currentBrushSize)
            }
            fragment.arguments = bundle
            return fragment
        }
    }


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentBrushSizeDialogBinding>(
            inflater,
            R.layout.fragment_brush_size_dialog,
            container,
            false
        )

        // Parsing values
        val currentBrushSize = arguments!!.getFloat(KEY_CURRENT_BRUSH_SIZE)

        this.viewModel = ViewModelProviders.of(this, factory).get(BrushSizeViewModel::class.java)

        // init values
        viewModel.brushSize = (currentBrushSize / BRUSH_SIZE_FACTOR).toInt()

        binding.viewModel = viewModel
        binding.handler = this

        return binding.root
    }

    override fun onDismissClicked() {
        dismiss()
    }

    override fun onApplyClicked() {
        callback.onBrushSizeChanged(viewModel.brushSize * BRUSH_SIZE_FACTOR)
        dismiss()
    }

    interface Callback {
        fun onBrushSizeChanged(brushSize: Float)
    }

}
