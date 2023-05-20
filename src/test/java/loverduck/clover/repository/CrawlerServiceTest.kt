package loverduck.clover.repository

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.concurrent.Executors


@Slf4j
@SpringBootTest
open class CrawlerServiceTest {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Test
    fun crawl() {
        try {

            val executor = Executors.newFixedThreadPool(16)
            // GET 요청을 보낼 URL
            val urls = ArrayDeque<URL>()
            var line: String?
//            /*
            for (c in 'A'..'A') {
                val str = "https://www.catch.co.kr/api/v1.0/comp/compMajor/getMainCompanyList?" +
                        "NowPage=1&PageSize=20&Sort=profit&IsInHiring=0&CName=&AreaSido=&" +
                        "JCode=$c&Size=&ThemeName=&Salary=0,0&IsNewList=1"
                log.info(str)
                val connection: HttpURLConnection = URL(str).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                log.debug(connection.responseCode.toString())
//                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
//                    continue
//                }
                val reader = connection.inputStream.bufferedReader()
                val response = StringBuffer()
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                val mapper = ObjectMapper()
                val data = mapper.readValue(response.toString(), Map::class.java)
                if (data["totalCount"] == null) {
                    continue
                }
                val count = data["totalCount"] as Int / 20 + 1
                repeat(count) { x ->
                    urls.add(
                        URL(
                            "https://www.catch.co.kr/api/v1.0/comp/compMajor/getMainCompanyList?" +
                                    "NowPage=${x + 1}&PageSize=20&Sort=profit&IsInHiring=0&CName=&AreaSido=&" +
                                    "JCode=${c}&Size=&ThemeName=&Salary=0,0&IsNewList=1"
                        )
                    )
                }
                connection.disconnect()
//            */
//            urls.add(URL("https://www.catch.co.kr/api/v1.0/comp/compMajor/getMainCompanyList?NowPage=1&PageSize=20&Sort=profit&IsInHiring=0&CName=&AreaSido=&JCode=A&Size=&ThemeName=&Salary=0,0&IsNewList=1"))

                while (urls.isNotEmpty()) {
                    val u = urls.removeFirst()
                    log.info(u.toString())
                    val httpURLConnection: HttpURLConnection = u.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "GET"
                    if (httpURLConnection.responseCode != HttpURLConnection.HTTP_OK) {
                        continue
                    }
                    val bufferedReader = httpURLConnection.inputStream.bufferedReader()
                    val stringBuffer = StringBuffer()
                    while (bufferedReader.readLine().also { line = it } != null) {
                        stringBuffer.append(line)
                    }
                    bufferedReader.close()
                    val objectMapper = ObjectMapper()
                    val anyMap = objectMapper.readValue(stringBuffer.toString(), Map::class.java)
                    var file: File

                    executor.submit {
                        if (anyMap.containsKey("companyList") && anyMap["companyList"] != null) {
                            for (map in (anyMap["companyList"] as ArrayList<*>)) {
                                log.info("$map ${map::class}")
                                val now: HashMap<String, Any> = map as HashMap<String, Any>
                                if (!now.containsKey("CompID")) continue
                                val path = "src/test/resources/json/companylist/${now["CompID"]}.json"
                                file = File(path)
                                if (file.exists()) continue
                                Files.write(
                                    file.toPath(),
                                    objectMapper.writeValueAsString(now).toByteArray(),
                                    StandardOpenOption.CREATE
                                )
                            }
                        }
                    }
                    httpURLConnection.disconnect()
                }
            }
            executor.shutdown()
            while (!executor.isTerminated) {
                Thread.sleep(100)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun crawlingCompInfo() {
        val urlPrefix = "https://www.catch.co.kr/api/v1.0/comp/"
        val pathPrefix = "src/test/resources/json/"
        val urlMap = mapOf(
            "financial" to urlPrefix + "compSummary/getFinancial/",
            "commonTop" to urlPrefix + "compSummary/commonTop/",
            "info" to urlPrefix + "compInfo/info/",
        )

        val executor = Executors.newFixedThreadPool(urlMap.size)

        File(pathPrefix + "companylist/").walk().onEnter { _ -> true }.forEach {
//            log.info(it.name)
            if (!it.isDirectory) {
                val data = File(pathPrefix + "companylist/" + it.name).bufferedReader().readLine()
                val objectMapper = ObjectMapper()
                val map = objectMapper.readValue(data, Map::class.java)
//                log.info(map.toString())
                for (url in urlMap) {
                    executor.submit {
                        log.info("${url.value}${map["CompID"]}")
                        val connection: HttpURLConnection =
                            URL("${url.value}${map["CompID"]}").openConnection() as HttpURLConnection
                        connection.requestMethod = "GET"
//                        log.debug(connection.responseCode.toString())
                        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                            return@submit
                        }
                        val reader = connection.inputStream.bufferedReader()
                        val response = StringBuffer()
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            response.append(line)
                        }
                        reader.close()
                        Files.write(
                            File("${pathPrefix}${url.key}/${map["CompID"]}.json").toPath(),
                            response.toString().toByteArray(),
                            StandardOpenOption.CREATE
                        )
//                        log.info(value.toString())
                        log.info("${pathPrefix}${url.key}/${map["CompID"]}.json")
                        connection.disconnect()
                    }
                }
            }
        }
        executor.shutdown()
        while (!executor.isTerminated) {
            Thread.sleep(100)
        }
    }
}

//https://www.catch.co.kr/api/v1.0/comp/compMajor/getMainCompanyList?NowPage=1&PageSize=20&Sort=profit&IsInHiring=0&CName=&AreaSido=&JCode=A&Size=&ThemeName=&Salary=0,0&IsNewList=1
//A 1~15
//B 1~3
//C01
//D 1~8
//E 1~5
//F 1~2
//G 1~4
//H 1~2
//I01
