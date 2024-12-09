import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class MultipartRequest(
    url: String,
    private val headers: Map<String, String>,
    private val params: Map<String, String>,
    private val filePart: File,
    private val responseListener: Response.Listener<String>,
    private val errorListener: Response.ErrorListener
) : Request<String>(Method.POST, url, errorListener) {

    private val boundary = "----VolleyBoundary${System.currentTimeMillis()}"

    override fun getHeaders(): MutableMap<String, String> {
        return headers.toMutableMap()
    }

    override fun getBodyContentType(): String {
        return "multipart/form-data; boundary=$boundary"
    }

    override fun getBody(): ByteArray {
        val bos = ByteArrayOutputStream()
        val dos = DataOutputStream(bos)

        try {
            // Add text parameters
            for ((key, value) in params) {
                dos.writeBytes("--$boundary\r\n")
                dos.writeBytes("Content-Disposition: form-data; name=\"$key\"\r\n\r\n")
                dos.writeBytes("$value\r\n")
            }

            // Add file part
            dos.writeBytes("--$boundary\r\n")
            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"${filePart.name}\"\r\n")
            dos.writeBytes("Content-Type: application/octet-stream\r\n\r\n")
            val fileInputStream = FileInputStream(filePart)
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (fileInputStream.read(buffer).also { bytesRead = it } != -1) {
                dos.write(buffer, 0, bytesRead)
            }
            fileInputStream.close()

            dos.writeBytes("\r\n")
            dos.writeBytes("--$boundary--\r\n")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bos.toByteArray()
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
        return try {
            val result = String(response?.data ?: byteArrayOf())
            Response.success(result, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }
}
