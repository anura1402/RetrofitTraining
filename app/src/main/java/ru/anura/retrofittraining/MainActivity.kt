package ru.anura.retrofittraining

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.anura.retrofittraining.databinding.ActivityMainBinding
import ru.anura.retrofittraining.retrofit.ProductApi

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        val productApi = retrofit.create(ProductApi::class.java)



        binding.button.setOnClickListener {
            Log.d("CheckingProduct","CLICKED")
            CoroutineScope(Dispatchers.IO).launch {
                val product = productApi.getProductById(3)
                Log.d("CheckingProduct","product: $product")
                runOnUiThread {
                    binding.tv.text = product.title
                }
            }
        }

    }
}