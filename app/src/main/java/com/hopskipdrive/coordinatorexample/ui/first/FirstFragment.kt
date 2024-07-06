package com.hopskipdrive.coordinatorexample.ui.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hopskipdrive.coordinatorexample.databinding.FragmentFirstBinding
import com.hopskipdrive.coordinatorexample.util.hostedViewModel
import com.hopskipdrive.coordinatorexample.util.DialogUtil

/**
 * Created by Mark Miller.
 */
class FirstFragment : Fragment() {

    //Using a nifty Kotlin property delegate to get the ViewModel.
    private val vm: FirstViewModel by hostedViewModel()
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            vm.goToSecondFragment()
        }
        binding.dialogButton.setOnClickListener {
            DialogUtil.getDialog(vm.getPopupMessage(), requireActivity()).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
