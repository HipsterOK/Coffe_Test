package com.latop.coffetest.registration

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
import com.latop.coffetest.databinding.FragmentRegistrationBinding
import org.json.JSONException
import org.json.JSONObject

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val registrationViewModel: RegistrationViewModel by lazy {
        ViewModelProvider(
            this, RegistrationViewModelFactory(RegistrationRepository(ApiService.create()))
        )[RegistrationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToLogin.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        binding.register.setOnClickListener {
            val login = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val repeatPassword = binding.repeatPasswordInput.text.toString()

            if (repeatPassword != password) {
                Toast.makeText(requireContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show()
            } else {
                if (login.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
                    registrationViewModel.register(login, password)
                } else {
                    Toast.makeText(requireContext(), "Заполнены не все поля!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        registrationViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    try {
                        val jsonObject = JSONObject(responseBody.string())
                        val token = jsonObject.getString("token")

                        Toast.makeText(
                            requireContext(), "Регистрация прошла успешно!", Toast.LENGTH_SHORT
                        ).show()
                        Navigation.findNavController(view)
                            .navigate(
                                R.id.action_registrationFragment_to_locationsFragment,
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