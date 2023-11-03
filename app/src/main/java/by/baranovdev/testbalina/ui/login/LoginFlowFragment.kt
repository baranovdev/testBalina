package by.baranovdev.testbalina.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.baranovdev.testbalina.R
import by.baranovdev.testbalina.databinding.FragmentLoginFlowBinding
import by.baranovdev.testbalina.utils.BaseUtils.doIfNotNull
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFlowFragment : Fragment() {

    private var _binding: FragmentLoginFlowBinding? = null

    private val binding get() = _binding!!

    private val viewModel: LoginFlowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentLoginFlowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setSignInState()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabLoginFragment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> setSignInState()
                    1 -> setSignUpState()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //JustDoNothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //JustDoNothing
            }

        })

        binding.btnLogin.setOnClickListener {
            onClickSignIn()
        }

        binding.btnSignup.setOnClickListener {
            onClickSignUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClickSignIn() {
        clearInputErrors()
        when (signInInputValidator()) {
            ValidationOutput.PASSWORD_IS_SHORT -> binding.tilPassword.error = "Password is short"
            ValidationOutput.EMPTY_FIELD_LOGIN -> binding.tilLogin.error = "Should not be empty"
            ValidationOutput.EMPTY_FIELD_PASSWORD -> binding.tilPassword.error =
                "Should not be empty"

            ValidationOutput.VALID -> viewModel.signIn(
                login = binding.tilLogin.editText?.text.toString(),
                password = binding.tilPassword.editText?.text.toString()
            )

            else -> {}
        }
    }

    private fun onClickSignUp() {
        clearInputErrors()
        when (signUpInputValidator()) {
            ValidationOutput.PASSWORD_IS_SHORT -> {
                binding.tilPassword.error = "Password is short"
                return
            }

            ValidationOutput.PASSWORDS_NOT_EQUAL -> {
                binding.tilPassword.error = "Passwords are not equal"
                binding.tilPasswordConfirmation.error = "Passwords are not equal"
                return
            }

            ValidationOutput.EMPTY_FIELD_LOGIN -> {
                binding.tilLogin.error =
                    "Should not be empty"
                return
            }

            ValidationOutput.EMPTY_FIELD_PASSWORD -> {
                binding.tilPassword.error =
                    "Should not be empty"
                return
            }

            ValidationOutput.VALID -> viewModel.signUp(
                login = binding.tilLogin.editText?.text.toString(),
                password = binding.tilPassword.editText?.text.toString()
            )
        }

    }

    private fun signInInputValidator(): ValidationOutput {
        when {
            binding.tilLogin.editText?.text?.isEmpty()
                ?: true -> return ValidationOutput.EMPTY_FIELD_LOGIN

            binding.tilPassword.editText?.text?.isEmpty()
                ?: true -> return ValidationOutput.EMPTY_FIELD_PASSWORD

            binding.tilPassword.editText?.text?.length?.let { it < 8 }
                ?: true -> return ValidationOutput.PASSWORD_IS_SHORT

            !binding.tilPasswordConfirmation.editText?.text.contentEquals(binding.tilPassword.editText?.text) -> ValidationOutput.PASSWORDS_NOT_EQUAL
        }
        return ValidationOutput.VALID
    }

    private fun signUpInputValidator(): ValidationOutput {
        when {
            binding.tilLogin.editText?.text?.isEmpty()
                ?: true -> return ValidationOutput.EMPTY_FIELD_LOGIN

            binding.tilPassword.editText?.text?.isEmpty()
                ?: true -> return ValidationOutput.EMPTY_FIELD_PASSWORD

            binding.tilPassword.editText?.text?.length?.let { it < 8 }
                ?: true -> return ValidationOutput.PASSWORD_IS_SHORT

            binding.tilPasswordConfirmation.editText?.text?.equals(binding.tilPassword.editText?.text)?.not() ?: true -> return ValidationOutput.PASSWORDS_NOT_EQUAL
        }
        return ValidationOutput.VALID
    }

    fun setSignInState() {
        with(binding) {
            tilPasswordConfirmation.isVisible = false
            btnLogin.isVisible = true
            btnSignup.isVisible = false
        }
    }

    fun setSignUpState() {
        with(binding) {
            tilPasswordConfirmation.isVisible = true
            btnLogin.isVisible = false
            btnSignup.isVisible = true
        }
    }

    private fun clearInputErrors() {
        binding.tilPassword.error = null
        binding.tilPasswordConfirmation.error = null
        binding.tilLogin.error = null
    }

    enum class ValidationOutput {
        PASSWORD_IS_SHORT,
        PASSWORDS_NOT_EQUAL,
        EMPTY_FIELD_LOGIN,
        EMPTY_FIELD_PASSWORD,
        VALID
    }
}