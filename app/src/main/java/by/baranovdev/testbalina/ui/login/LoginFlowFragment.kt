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

        binding.tabLoginFragment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
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

        binding.btnLogin.setOnClickListener{
            onClickSignIn()
        }

        binding.btnSignup.setOnClickListener{
            onClickSignUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClickSignIn() {
        viewModel.signIn(login = binding.tilLogin.editText?.text.toString(), password = binding.tilPassword.editText?.text.toString())
    }

    private fun onClickSignUp() {
        viewModel.signUp(login = binding.tilLogin.editText?.text.toString(), password = binding.tilPassword.editText?.text.toString())
    }

    fun setSignInState(){
        with(binding){
            tilPasswordConfirmation.isVisible = false
            btnLogin.isVisible = true
            btnSignup.isVisible= false
        }
    }

    fun setSignUpState(){
        with(binding){
            tilPasswordConfirmation.isVisible = true
            btnLogin.isVisible = false
            btnSignup.isVisible= true
        }
    }
}