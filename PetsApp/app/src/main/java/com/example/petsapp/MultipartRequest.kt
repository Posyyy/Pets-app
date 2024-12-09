import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class MultipartRequest(
    method: Int,
    url: String,
    private val listener: Response.Listener<String>,
    private val errorListener: Response.ErrorListener,
    private val params: Map<String, String>,
    private val fileParam: Pair<String, File>? = null // For image file
) : Request<String>(method, url, errorListener) {

    override fun getBodyContentType(): String {
        return "multipart/form-data; boundary=boundary"
    }

    override fun getBody(): ByteArray {
        val boundary = "--boundary"
        val body = StringBuilder()

        // Add form fields
        for ((key, value) in params) {
            body.append(boundary).append("\r\n")
            body.append("Content-Disposition: form-data; name=\"$key\"\r\n\r\n")
            body.append(value).append("\r\n")
        }

        // Add the image file if provided
        fileParam?.let {
            val (paramName, file) = it
            val fileBytes = file.readBytes()
            body.append(boundary).append("\r\n")
            body.append("Content-Disposition: form-data; name=\"$paramName\"; filename=\"${file.name}\"\r\n")
            body.append("Content-Type: image/jpeg\r\n\r\n")
            body.append(String(fileBytes)).append("\r\n")
        }

        body.append(boundary).append("--\r\n")
        return body.toString().toByteArray(Charsets.UTF_8)
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
        return try {
            val responseString = String(response?.data ?: byteArrayOf(), charset(HttpHeaderParser.parseCharset(response?.headers)))
            Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: Exception) {
            Response.error(VolleyError(e))
        }
    }

    override fun deliverResponse(response: String) {
        listener.onResponse(response)
    }
}
