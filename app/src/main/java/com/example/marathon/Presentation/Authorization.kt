package com.example.marathon.Presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.marathon.Domain.authorisation
import com.example.marathon.R

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest

class Authorization : AppCompatActivity() {
    private lateinit var inputHelper: authorisation
    private var editTextPassword: EditText? = null
    private var editTextEmail: EditText? = null

    val supabaseUrl = "https://supabase.com/dashboard/project/ficoowdsbnoklbqioohl" // Замените на URL вашего проекта
    val supabaseAnonKey = "sb_publishable_CERFwAVC3RcF-N-_p62IsA_jT2WAmgK" // Замените на ваш Anon Key

    val client = createSupabaseClient(
        supabaseUrl = supabaseUrl,
        supabaseKey = supabaseAnonKey
    ) {
        install(Postgrest)
        install(Auth) // Установка модуля аутентификации
    }

    companion object {
        private const val TAG = "AuthorizationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorisation_activity)

        inputHelper = authorisation(this)

        // Используем безопасный вызов
        editTextPassword = findViewById(R.id.inputpassword)
        editTextEmail = findViewById(R.id.inputemail)

        Log.d(TAG, "Найден editTextEmail: ${editTextEmail != null}")
        Log.d(TAG, "Найден editTextPassword: ${editTextPassword != null}")
    }

    fun onLoginButtonClick(view: View){
        Log.d(TAG, "Кнопка нажата")

        // Пример: получение данных
        lifecycleScope.launch {
            val response = client.postgrest["your_table_name"].select()
            // Обработайте ответ (например, распарсите JSON в список объектов)
            Log.d(TAG, "Запрос отправлен")
        }

        // Проверяем, что элементы не null
        if (editTextEmail == null || editTextPassword == null) {
            Log.e(TAG, "Элементы не найдены!")
            inputHelper.showToast("Ошибка инициализации элементов")
            return
        }

        val email = inputHelper.getTextFromEditText(editTextEmail!!)
        val password = inputHelper.getTextFromEditText(editTextPassword!!)

        Log.d(TAG, "Email: '$email', Password: '$password'")

        if (password.isEmpty() || email.isEmpty()) {
            inputHelper.showToast("Заполните все поля")
        } else if (!inputHelper.validateEmail(email)) {
            inputHelper.showToast("Неверный email")
        } else {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }
}