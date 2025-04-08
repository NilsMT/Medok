import org.jsoup.Jsoup
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URL

//proxy detection
fun isProxyNeeded(): Boolean {
    return try {
        //essaye sans proxy de se co à google.com
        Jsoup.connect("http://www.google.com").get()
        false
    } catch (e: Exception) {
        true
    }
}

fun getPdfLink(proxy: Proxy?): String? {
    val urlMain = "https://ansm.sante.fr"
    val url = "$urlMain/documents/reference/thesaurus-des-interactions-medicamenteuses-1"

    val connection = if (proxy != null) {
        Jsoup.connect(url).proxy(proxy)
    } else {
        Jsoup.connect(url)
    }

    val document = connection.get()
    val pageHTML = document.body().toString()

    val regex = Regex("""\/uploads\/\d{4}\/\d{2}\/\d{2}\/\d{8}-thesaurus-interactions-medicamenteuses-[a-zA-Z]+-\d{4}\.pdf""")
    val matches = regex.findAll(pageHTML).toList()

    if (matches.isNotEmpty()) {
        println("Le scrape a trouvé un/des liens vers le pdf: ")
        for (m in matches) {
            println(m.value)
        }
    } else {
        println("Le scrape n'a pas trouvé de lien vers le pdf")
    }

    return if (matches.isNotEmpty()) "$urlMain${matches[0].value}" else null
}

fun downloadPdf(proxy: Proxy?, pdfDownloadLink: String) {
    val localFilePath = "./src/main/kotlin/interactions-medicamenteuses.pdf"

    try {
        val url = URL(pdfDownloadLink)
        val conn = if (proxy != null) {
            url.openConnection(proxy)
        } else {
            url.openConnection()
        }

        val inputStream = BufferedInputStream(conn.getInputStream())
        val outputStream = FileOutputStream(localFilePath)

        val buffer = ByteArray(1024)
        var bytesRead: Int

        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }

        outputStream.close()
        inputStream.close()

        println("PDF téléchargé en local: $localFilePath")
    } catch (e: Exception) {
        println("Erreur: ${e.message}")
    }
}

fun main(args: Array<String>) {
    //auto proxy
    val proxy: Proxy? = if (isProxyNeeded()) {
        Proxy(Proxy.Type.HTTP, InetSocketAddress("srv-proxy-etu-2.iut-nantes.univ-nantes.prive", 3128))
    } else {
        null
    }

    val pdfLink = getPdfLink(proxy)

    if (pdfLink != null) {
        downloadPdf(proxy, pdfLink)
    } else {
        println("Pas de lien vers le pdf")
    }
}