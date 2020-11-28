package com.sitiaisyah.idn.retrofitcrud_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sitiaisyah.idn.retrofitcrud_kotlin.R
import com.sitiaisyah.idn.retrofitcrud_kotlin.adapter.ProductAdapter
import com.sitiaisyah.idn.retrofitcrud_kotlin.model.PersonItem
import com.sitiaisyah.idn.retrofitcrud_kotlin.remote.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var btnAddUser: Button
    lateinit var btnGetUser: Button
    var rv: ListView? = null
    var productService: ProductService? = null
    var list: List<PersonItem> = ArrayList<PersonItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAddUser = findViewById(R.id.btnAddUser)
        btnGetUser = findViewById(R.id.btnGetUserList)
        rv = findViewById(R.id.rv)

        productService = APIUtils.productService
        btnAddUser.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@MainActivity,
                ProductActivity::class.java
            )
            intent.putExtra("name", "")
            intent.putExtra("price", "")
            intent.putExtra("desc", "")
            startActivity(intent)
        })
        btnGetUser.setOnClickListener(View.OnClickListener { userList })
    }

    private val userList: Unit
        get() {
            val call: Call<List<PersonItem>>? = productService?.product
            call?.enqueue(object : Callback<List<PersonItem>> {
                override fun onResponse(
                    call: Call<List<PersonItem>>,
                    response: Response<List<PersonItem>>
                ) {
                    if (response.isSuccessful) {
                        list = response.body()!!
                        rv!!.adapter = ProductAdapter(
                            this@MainActivity,
                            R.layout.list_item, list
                        )
                    }
                }

                override fun onFailure(
                    call: Call<List<PersonItem>>,
                    t: Throwable
                ) {
                    Log.e("ERROR: ", t.message!!)
                }
            })
        }
}