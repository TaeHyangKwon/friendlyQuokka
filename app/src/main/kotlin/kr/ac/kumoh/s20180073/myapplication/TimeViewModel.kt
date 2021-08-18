package kr.ac.kumoh.s20180073.myapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kr.ac.kumoh.s20180073.myapplication.ui.MySingleton
import java.net.URLEncoder
import java.util.*

class TimeViewModel(application: Application) : AndroidViewModel(application)  {

    companion object{
        const val QUEUE_TAG = "VolleyRequest"
        val SERVER_URL = "https://friendlyquokka-faezq.run.goorm.io/time"
    }

    private var mQueue: RequestQueue
    init {
        mQueue = MySingleton.getInstance(application).requestQueue
    }

    override fun onCleared(){
        super.onCleared()
        mQueue.cancelAll(QUEUE_TAG)
    }

    fun requestList(date: String, starthour: String, startminute: String, endhour: String, endminute: String, gaphour: String, gapminute: String, work: String, color: String){
        val postRequest = object : StringRequest(Method.POST, SERVER_URL, Response.Listener<String>
        { response ->
            Log.d(QUEUE_TAG, date + starthour + startminute + endhour + endminute + gaphour + gapminute + work + color)
        }, Response.ErrorListener {Log.d(QUEUE_TAG, "에러")})
        {
            // 서버에 데이터 전달
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("date", date)
                params.put("starthour", starthour)
                params.put("startminute", startminute)
                params.put("endhour", endhour)
                params.put("endminute", endminute)
                params.put("gaphour", gaphour)
                params.put("gapminute", gapminute)
                params.put("work", work)
                params.put("color", color)
                return params
            }
        }
        mQueue.add(postRequest)
    }
}