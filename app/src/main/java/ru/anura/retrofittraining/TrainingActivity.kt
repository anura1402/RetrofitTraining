package ru.anura.retrofittraining

import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.anura.retrofittraining.adapter.ProductAdapter
import ru.anura.retrofittraining.databinding.LayoutWithRvBinding
import ru.anura.retrofittraining.retrofit.AuthRequest
import ru.anura.retrofittraining.retrofit.MainApi
import ru.anura.retrofittraining.retrofit.User

class TrainingActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    //private lateinit var binding: AuthBinding
    private lateinit var binding: LayoutWithRvBinding
    private lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.auth)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //binding = AuthBinding.inflate(layoutInflater)
        binding = LayoutWithRvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter

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

        var user: User? = null

        CoroutineScope(Dispatchers.IO).launch {
            user = mainApi.authSimpleUser(
                AuthRequest(
                    "emilys",
                    "emilyspass"
                )
            )
            runOnUiThread {
                supportActionBar?.title = user?.firstName
            }
        }


        binding.sv.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(text: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    //val list = text?.let { mainApi.getProductsByName(it) }
                    val list = text?.let { mainApi.getProductsByNameAuth(user?.accessToken ?: "", it) }
                    runOnUiThread {
                        binding.apply {
                            adapter.submitList(list?.products)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return true
            }
        })

//        binding.button.setOnClickListener {
//            Log.d("CheckingProduct","CLICKED")
//            CoroutineScope(Dispatchers.IO).launch {
//                val user = mainApi.auth(
//                    AuthRequest(
//                        binding.username.text.toString(),
//                        binding.password.text.toString()
//                    )
//                )
//                //val product = mainApi.getProductById(3)
//                runOnUiThread {
//                    //binding.tv.text = product.title
//
//                    binding.apply {
//                        Picasso.get().load(user.image).into(iv)
//                        firstName.text = user.firstName
//                        lastName.text = user.lastName
//                    }
//                }
//            }
//        }

    }
}