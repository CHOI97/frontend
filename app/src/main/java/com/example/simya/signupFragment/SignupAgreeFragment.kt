package com.example.simya.signupFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.simya.R
import com.example.simya.databinding.FragmentSignupAgreeBinding
import com.example.simya.signUpViewModel.SignUpViewModel

class SignupAgreeFragment: Fragment() {
    private lateinit var binding: FragmentSignupAgreeBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupAgreeBinding.inflate(layoutInflater)

        // ViewModel으로 Activiy와 통신
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(SignUpViewModel::class.java)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        agreeCheck()

        binding.btnSignupNext.setOnClickListener {

            if (agreeCheck()) {
                // progress bar 값 변경
                viewModel.pbValue.value = 25

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fm_signup, SignupEmailFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            } else {

                Toast.makeText(this.activity, "동의하지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun agreeCheck() : Boolean {
        val agreeAll = binding.cbSignupAgreeAll
        val agreeService = binding.cbSignupAgreeService
        val agreeInfo = binding.cbSignupAgreeInfo


        agreeAll.setOnClickListener {
            if (agreeAll.isChecked) {
                agreeService.isChecked = true
                agreeInfo.isChecked = true
            } else {
                agreeService.isChecked = false
                agreeInfo.isChecked = false
            }
        }

        agreeInfo.setOnClickListener {
            agreeAll.isChecked = agreeInfo.isChecked && agreeService.isChecked
        }

        agreeService.setOnClickListener {
            agreeAll.isChecked = agreeInfo.isChecked && agreeService.isChecked
        }



        return agreeAll.isChecked

    }
}