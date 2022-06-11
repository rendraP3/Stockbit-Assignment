package com.stockbit.assignment.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.stockbit.assignment.R
import com.stockbit.assignment.databinding.FragmentAuthBinding
import com.stockbit.assignment.presentation.viewmodel.AuthViewModel
import com.stockbit.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val viewModel by viewModel<AuthViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAuthBinding =
        FragmentAuthBinding.inflate(inflater, container, false)

    override fun initView() = with(binding) {
        observeNavigation(viewModel)
        btnLoginFacebook.setOnClickListener { showToast() }
        btnLoginGoogle.setOnClickListener { showToast() }
        btnRegister.setOnClickListener { showToast() }
        btnLoginWithFingerprint.setOnClickListener { showToast() }
        btnLogin.setOnClickListener { doOnLoginClicked() }
        tilEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) tilEmail.error = null
        }
        tilPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) tilPassword.error = null
        }

    }

    private fun doOnLoginClicked() = with(binding) {
        val username = etEmail.text.toString()
        val password = etPassword.text.toString()

        when {
            username.isEmpty() -> tilEmail.error = getString(R.string.error_field_empty)
            password.isEmpty() -> tilPassword.error = getString(R.string.error_field_empty)
            username != USERNAME -> tilEmail.error = getString(R.string.error_username_not_matched)
            password != PASSWORD -> tilPassword.error = getString(R.string.error_password_not_matched)
            else -> {
                tilEmail.error = null
                tilPassword.error = null
                viewModel.navigateToMarket()
            }
        }
    }

    private fun showToast() =
        Toast.makeText(context, getString(R.string.nothing_happened_label), Toast.LENGTH_SHORT).show()

    companion object {
        const val USERNAME = "assignment"
        const val PASSWORD = "123456"
    }
}