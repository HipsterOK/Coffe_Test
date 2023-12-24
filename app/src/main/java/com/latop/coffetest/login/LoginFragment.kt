package com.latop.coffetest.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.latop.coffetest.network.ApiService
import com.latop.coffetest.R
import com.latop.coffetest.databinding.FragmentLoginBinding
import org.json.JSONException
import org.json.JSONObject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this, LoginViewModelFactory(LoginRepository(ApiService.create()))
        )[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToRegistration.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.login.setOnClickListener {
            val login = binding.emailInputLogin.text.toString()
            val password = binding.passwordInputLogin.text.toString()

            if (login.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(login, password)
            } else {
                Toast.makeText(requireContext(), "Заполнены не все поля!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        loginViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    try {
                        val jsonObject = JSONObject(responseBody.string())
                        val token = jsonObject.getString("token")

                        Navigation.findNavController(view)
                            .navigate(R.id.action_loginFragment_to_locationsFragment,
                                Bundle().apply {
                                    putString("token", token)
                                })
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            requireContext(),
                            "Ошибка при обработке ответа сервера",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(), response.errorBody().toString(), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}