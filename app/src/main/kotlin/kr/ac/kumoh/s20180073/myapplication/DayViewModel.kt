package kr.ac.kumoh.s20180073.myapplication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import kr.ac.kumoh.s20180073.myapplication.ui.MySingleton
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class DayViewModel (application: Application) : AndroidViewModel(application) {
    data class dayList(var date: String, var starthour: String, var startminute: String, var endhour: String, var endminute: String, var gaphour: String, var gapminute: String, var work: String, var color: String)

    companion object{
        const val QUEUE_TAG = "VolleyRequest"
        val SERVER_URL = "https://friendlyquokka-faezq.run.goorm.io/day"
    }

    val list = MutableLiveData<ArrayList<dayList>>()
    private val day = ArrayList<dayList>()
    private var mQueue: RequestQueue
    init {
        list.value = day
        mQueue = MySingleton.getInstance(application).requestQueue
    }

    fun getList(i :Int) = day[i]

    fun getSize() = day.size

    override fun onCleared(){
        super.onCleared()
        mQueue.cancelAll(QUEUE_TAG)
    }

    fun requestList(){
        var request = JsonArrayRequest(
                Request.Method.GET,
                SERVER_URL,
                null,
                {
                    day.clear()
                    parseJson(it)
                    list.value = day
                },
                {
                    Toast.makeText(getApplication(),
                            it.toString(),
                            Toast.LENGTH_LONG).show()
                }
        )

        request.tag = QUEUE_TAG
        MySingleton.getInstance(getApplication()).addToRequestQueue(request)
    }
    private fun parseJson(items: JSONArray) {
        for (i in 0 until items.length()) {
            val item: JSONObject = items[i] as JSONObject
            val date = item.getString("date")
            val starthour = item.getString("starthour")
            val startminute = item.getString("startminute")
            val endhour = item.getString("endhour")
            val endminute = item.getString("endminute")
            val gaphour = item.getString("gaphour")
            val gapminute = item.getString("gapminute")
            val work = item.getString("work")
            val color = item.getString("color")

            day.add(dayList( date, starthour, startminute, endhour, endminute, gaphour, gapminute, work, color))
        }
    }
}