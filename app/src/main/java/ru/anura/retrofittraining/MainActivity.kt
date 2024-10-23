package ru.anura.retrofittraining

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.anura.retrofittraining.databinding.AuthBinding
import ru.anura.retrofittraining.retrofit.AuthRequest
import ru.anura.retrofittraining.retrofit.MainApi

class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    private lateinit var binding: AuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.auth)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        binding = AuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")//url откуда будут идти данные
            .client(client) //подключение okhttp
            .addConverterFactory(GsonConverterFactory.create())//фабрика будет преобразовывать в data class
            .build()
        val mainApi = retrofit.create(MainApi::class.java)



        binding.button.setOnClickListener {
            Log.d("CheckingProduct","CLICKED")
            CoroutineScope(Dispatchers.IO).launch {
                val user = mainApi.auth(
                    AuthRequest(
                        binding.username.text.toString(),
                        binding.password.text.toString()
                    )
                )
                //val product = mainApi.getProductById(3)
                runOnUiThread {
                    binding.apply {
                        Picasso.get().load(user.image).into(iv)
                        firstName.text = user.firstName
                        lastName.text = user.lastName
                    }
                }
            }
        }

    }
}