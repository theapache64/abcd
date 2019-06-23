package com.theapache64.abcd.ui.fragments.dialogfragments.share


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
import com.theapache64.abcd.databinding.FragmentShareDialogBinding
import com.theapache64.abcd.ui.base.BaseDialogFragment
import com.theapache64.abcd.ui.fragments.dialogfragments.brushsize.BrushSizeDialogFragment
import dagger.android.support.AndroidSupportInjection
import java.io.File
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class ShareDialogFragment private constructor() : BaseDialogFragment(), ShareHandler {

    private lateinit var callback: Callback
    private lateinit var viewModel: ShareViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    companion object {

        private const val KEY_MAP_FILE = "map_file"
        private const val KEY_OUTPUT_FILE = "output_file"

        val TAG = BrushSizeDialogFragment::class.java.simpleName

        fun newInstance(mapFile: File, outputFile: File): ShareDialogFragment {
            val fragment = ShareDialogFragment()
            val bundle = Bundle().apply {
                // add data here
                putSerializable(KEY_MAP_FILE, mapFile)
                putSerializable(KEY_OUTPUT_FILE, outputFile)
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
        val binding = DataBindingUtil.inflate<FragmentShareDialogBinding>(
            inflater,
            R.layout.fragment_share_dialog,
            container,
            false
        )

        // Getting params
        val mapFile = arguments?.getSerializable(KEY_MAP_FILE) as File
        val outputFile = arguments?.getSerializable(KEY_OUTPUT_FILE) as File

        this.viewModel = ViewModelProviders.of(this, factory).get(ShareViewModel::class.java)
        viewModel.init(mapFile, outputFile)
        binding.handler = this

        // Watch for seg map file
        viewModel.getShareFile().observe(this, Observer { file ->
            // seg map to performShare ready
            performShare(file)
        })



        return binding.root
    }

    private fun performShare(file: File) {
        this.callback.performShare(file)
        dismiss()
    }

    override fun onShareSegMapClicked() {
        viewModel.shareSegMap()
    }

    override fun onShareOutputClicked() {
        viewModel.shareOutputFile()
    }

    override fun onShareSegMapAndOutputClicked() {
        viewModel.shareMapAndOutput()
    }

    override fun onDismissClicked() {
        dismiss()
    }

    interface Callback {
        fun performShare(file : File)
    }

}
