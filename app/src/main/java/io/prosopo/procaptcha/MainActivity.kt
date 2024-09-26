package io.prosopo.procaptcha

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        // TODO don't forget to put key here
        private const val SITE_KEY = ""
    }

    private class ProcaptchaWebViewClient : WebViewClient() {

        // TODO impl methods

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false;
        }
        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            Log.e("error", error.toString());
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.i("onPageStarted", url.toString());
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.i("onPageFinished", url.toString());
        }

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
//        Log.i("onLoadResource", url.toString());
        }

    }

    private class ProcaptchaWebChromeClient : WebChromeClient() {
        // TODO impl methods
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)
        WebView.setWebContentsDebuggingEnabled(false)
        webView.settings.javaScriptEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.allowFileAccess = true
        webView.webViewClient = ProcaptchaWebViewClient()
        webView.webChromeClient = ProcaptchaWebChromeClient()
        val encodedHtml = generateEncodedHtml()
        webView.loadData(encodedHtml, "text/html", "base64")
    }

    private fun generateEncodedHtml(): String {
        val html = """
            <html>
                <head>
                    <title>Procaptcha Demo</title>
                    <script type="module" src="https://js.prosopo.io/js/procaptcha.bundle.js" async defer></script>
                </head>
                <body>
                    <form action="" method="POST">
                        <input type="text" name="email" placeholder="Email" />
                        <input type="password" name="password" placeholder="Password" />
                        <div class="procaptcha" data-sitekey="${MainActivity.Companion.SITE_KEY}"></div>
                        <br />
                        <input type="submit" value="Submit" />
                    </form>
                </body>
            </html>
        """.trimIndent()
        return Base64.encodeToString(html.toByteArray(), Base64.NO_PADDING)
    }
}