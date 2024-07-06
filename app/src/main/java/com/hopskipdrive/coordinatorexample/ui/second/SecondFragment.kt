package com.hopskipdrive.coordinatorexample.ui.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hopskipdrive.coordinatorexample.databinding.FragmentSecondBinding
import com.hopskipdrive.coordinatorexample.hostedViewModel
import com.hopskipdrive.coordinatorexample.util.DialogUtil

/**
 * Created by Mark Miller.
 */
class SecondFragment : Fragment() {

    //Using a nifty Kotlin property delegate to get the ViewModel.
    private val vm: SecondViewModel by hostedViewModel()
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            vm.goBackToFirstFragment()
        }
        binding.dialogButton.setOnClickListener {
            DialogUtil.getDialog(vm.getPopupMessage(), requireActivity()).show()
            vm.clickedDialogButton()
        }
        vm.buttonTapStatus.observe(viewLifecycleOwner) { text ->
            //This is the state being restored by the coordinator.
            //Even though the fragment and ViewModel are destroyed when popped off the back stack
            //the state is still maintained by the coordinator (currently only in memory).
            binding.clickedDialogTv.text = text
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
