package ru.anura.retrofittraining.appExample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.anura.retrofittraining.adapter.ProductAdapter
import ru.anura.retrofittraining.databinding.FragmentProductsBinding
import ru.anura.retrofittraining.retrofit.MainApi

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ProductsFragment : Fragment() {
    private lateinit var adapter: ProductAdapter
    private lateinit var mainApi: MainApi

    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        initRcView()
        viewModel.token.observe(viewLifecycleOwner) { token ->
            CoroutineScope(Dispatchers.IO).launch {
                val list = mainApi.getAllProductsAuth(token)
                requireActivity().runOnUiThread {
                    adapter.submitList(list.products)
                }
            }
            binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    CoroutineScope(Dispatchers.IO).launch {
                        val list = text?.let { mainApi.getProductsByNameAuth(token, it) }
                        requireActivity().runOnUiThread {
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
        }
    }

    private fun initRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        mainApi = retrofit.create(MainApi::class.java)
    }

    private fun initRcView() = with(binding) {
        adapter = ProductAdapter()
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}