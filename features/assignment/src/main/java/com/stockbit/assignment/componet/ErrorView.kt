package com.stockbit.assignment.componet

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.stockbit.assignment.R
import com.stockbit.assignment.databinding.ViewErrorStateBinding
import com.stockbit.common.utils.ErrorState

class ErrorView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(ctx, attrs, defStyleAttr) {

    private val binding = ViewErrorStateBinding.inflate(LayoutInflater.from(ctx), this, false)

    lateinit var retryListener: () -> Unit

    init {
        ctx.obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, 0).apply {
            try {
                binding.btnRetry.setOnClickListener { if (::retryListener.isInitialized) retryListener.invoke() }
                initialize()
            } finally {
                recycle()
            }
        }
    }

    private fun initialize() {
        binding.tvMessage.text = resources.getString(R.string.error_something_wrong)
    }

    fun setError(
        error: ErrorState,
        message: String
    ) = with(binding) {
        ivError.setImageResource(
            when (error) {
                ErrorState.ERROR -> R.drawable.img_error
                ErrorState.EMPTY -> R.drawable.img_empty
                ErrorState.NO_INTERNET -> R.drawable.img_no_connection
            }
        )
        tvDescription.text = message
    }

}