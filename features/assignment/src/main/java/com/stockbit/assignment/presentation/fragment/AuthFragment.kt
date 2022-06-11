package com.stockbit.assignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stockbit.assignment.R
import com.stockbit.assignment.databinding.FragmentAuthBinding
import com.stockbit.assignment.presentation.viewmodel.AuthViewModel
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.base.BaseViewModel

class AuthFragment : BaseFragment() {

    override fun getViewModel() = AuthViewModel()

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        btnLoginFacebook.setOnClickListener { showToast() }
        btnLoginGoogle.setOnClickListener { showToast() }
        btnRegister.setOnClickListener { showToast() }
        btnLoginWithFingerprint.setOnClickListener { showToast() }
        btnLogin.setOnClickListener { doOnLoginClicked() }

    }

    private fun doOnLoginClicked() = with(binding) {
        val username = etEmail.text.toString()
        val password = etPassword.text.toString()

        when {
            username != USERNAME -> tilEmail.error = getString(R.string.error_username_not_matched)
        }
    }

    private fun showToast() =
        Toast.makeText(context, getString(R.string.nothing_happened_label), Toast.LENGTH_SHORT).show()

    companion object {
        const val USERNAME = "assignment"
        const val PASSWORD = "123456"
    }
}