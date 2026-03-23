package com.example.marathon.Domain

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import android.util.Log

class authorisation(private val context: Context) {
    companion object {
        private const val TAG = "AuthorizationDebug"
    }
    fun getTextFromEditText(editText: EditText): String {
        val text = editText.text.toString().trim()
        Log.d(TAG, "Получен текст из EditText: '$text'")
        Log.d(TAG, "EditText hash: ${editText.hashCode()}")
        return text
    }
    fun validateEmail(email: String): Boolean {
        Log.d(TAG, "Валидация email: '$email'")
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        Log.d(TAG, "Email валиден: $isValid")
        return isValid
    }

    fun validatePassword(password: String): Boolean {
        // Добавьте валидацию пароля при необходимости
        return password.length >= 6 // например, минимум 6 символов
    }

    fun showToast(message: String) {
        Log.d(TAG, "Показываем Toast: $message")
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun validateFields(email: String, password: String): String? {
        return when {
            email.isEmpty() || password.isEmpty() -> "Заполните все поля"
            !validateEmail(email) -> "Неверный формат email"
            !validatePassword(password) -> "Пароль должен содержать минимум 6 символов"
            else -> null // null означает успешную валидацию
        }
    }
}