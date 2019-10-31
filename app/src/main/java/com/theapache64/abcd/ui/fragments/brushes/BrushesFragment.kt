package com.theapache64.abcd.ui.fragments.brushes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.theapache64.abcd.R
import com.theapache64.abcd.databinding.FragmentBrushesBinding
import com.theapache64.abcd.models.Brush
import com.theapache64.abcd.ui.adapters.BrushesAdapter
import com.theapache64.twinkill.utils.extensions.toast

/**
 * A simple [Fragment] subclass.
 */
class BrushesFragment : Fragment(), BrushesHandler {

    companion object {

        private const val KEY_BRUSHES = "brushes"

        fun newInstance(brushes: ArrayList<Brush>): BrushesFragment {

            return BrushesFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_BRUSHES, brushes)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentBrushesBinding>(
            inflater,
            R.layout.fragment_brushes,
            container,
            false

        )

        val brushes = arguments!!.getSerializable(KEY_BRUSHES) as List<Brush>
        binding.rvBrushes.adapter = BrushesAdapter(activity!!, brushes) {
            toast("Clicked on a brush")
        }

        return binding.root
    }


}
