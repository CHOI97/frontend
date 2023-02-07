package com.example.simya.signupFragment


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.lifecycle.ViewModelProvider
import com.example.simya.R
import com.example.simya.activity.SignupActivity
import com.example.simya.databinding.ActivitySignupBinding
import com.example.simya.databinding.FragmentSignupEmailBinding
import com.example.simya.server.account.SignupDTO
import com.example.simya.signUpViewModel.SignUpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import kotlin.math.sign

class SignupEmailFragment: Fragment(), SignupActivity.onBackPressedListener {
    private lateinit var binding: FragmentSignupEmailBinding
    private val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private lateinit var textWatcher: TextWatcher
    private lateinit var bindingMain: ActivitySignupBinding
    private lateinit var viewModel: SignUpViewModel

    var signupActivity: SignupActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signupActivity = context as SignupActivity

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupEmailBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[SignUpViewModel::class.java]

        return binding.root

    }


    // 다시 상속받는
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pbValue.value = 25
        FalseButton()

        initTW()

        binding.tietEmailSignupInput.addTextChangedListener(textWatcher)



        binding.btnSignupNextEmail.setOnClickListener {
            if (emailCheck()) {
                // pw프래그먼트로 데이터 전달
                val email = binding.tietEmailSignupInput.text.toString()
                setFragmentResult("email", bundleOf("bundleKeyEmail" to email))

                // progress bar 값 변경
                viewModel.pbValue.value = 50

                signupActivity!!.nextFragmentSignUp(3)
            }
        }
    }




    private fun emailCheck() : Boolean {
        var email = binding.tietEmailSignupInput.text.toString().trim()
        val pattern = Pattern.matches(emailValidation, email)

        return if (pattern){
            binding.tilEmailSignupInput.error = null
            true
        } else {
            binding.tilEmailSignupInput.error = "올바른 이메일 형식을 입력해주세요."
            false
        }
    }


    private fun initTW() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val emailInput = binding.tietEmailSignupInput!!.text.toString()

                if (emailInput.isNotEmpty()) {
                    TrueButton()
                }
                if (emailInput.isEmpty()) {
                    FalseButton()
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        }
    }

    private fun TrueButton() {
        binding.btnSignupNextEmail.isEnabled = true
        binding.btnSignupNextEmail.isClickable = true
        binding.btnSignupNextEmail.setBackgroundResource(R.drawable.low_radius_button_on)
        binding.btnSignupNextEmail.setTextColor(resources.getColor(R.color.Gray_03))

    }

    private fun FalseButton() {
        binding.btnSignupNextEmail.isEnabled = false
        binding.btnSignupNextEmail.isClickable = false
        binding.btnSignupNextEmail.setBackgroundResource(R.drawable.low_radius_button_off)
        binding.btnSignupNextEmail.setTextColor(resources.getColor(R.color.Gray_10))

    }

    override fun onBackPressed() {
        signupActivity!!.nextFragmentSignUp(1)
    }

}


