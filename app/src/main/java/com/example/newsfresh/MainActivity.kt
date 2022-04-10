package com.example.newsfresh

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), newsItemClicked {

    lateinit var mAdapter:newsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv=findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager=LinearLayoutManager(this)
        mAdapter = newsAdapter(this)
        rv.adapter=mAdapter
        fetchdata()
    }
    fun fetchdata(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://saurav.tech/NewsAPI/everything/cnn.json"

        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray=it.getJSONArray("articles")
                val newsArray=ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject=newsJsonArray.getJSONObject(i)
                    val news=News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.update(newsArray)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            })
        queue.add(JsonObjectRequest)
    }

    override fun onitemClicked(item: News) {
        val builder=CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}