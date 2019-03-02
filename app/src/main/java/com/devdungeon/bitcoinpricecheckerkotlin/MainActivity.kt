package com.devdungeon.bitcoinpricecheckerkotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            Snackbar.make(view, getString(R.string.checking_now), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            val mainText = findViewById<TextView>(R.id.mainText)
            val queue = Volley.newRequestQueue(this)
            val url = "https://api.coindesk.com/v1/bpi/currentprice/BTC.json"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    val jsonData = JSONObject(response)
                    val price = jsonData.getJSONObject("bpi")
                                        .getJSONObject("USD")
                                        .getString("rate")
                    mainText.text = getString(R.string.price_text, price)
                },
                Response.ErrorListener { mainText.text = "N/A" })
            queue.add(stringRequest)

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
