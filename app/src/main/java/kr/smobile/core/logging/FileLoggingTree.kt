package kr.smobile.core.logging

import android.content.Context
import android.os.Environment
import android.util.Log
import kr.smobile.core.extension.beforeDay
import kr.smobile.core.extension.getCalendar
import kr.smobile.core.extension.timeClear
import kr.smobile.BuildConfig
import timber.log.Timber
import java.io.File
import java.io.File.separator
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*


class FileLoggingTree(private val context: Context) : Timber.DebugTree() {

    private val LOG_TAG = FileLoggingTree::class.java.simpleName
    private val FILE_MAINTAIN_DAYS = 5
    private val log_file_foramt_pattern = "yyyy-MM-dd"


    override fun log(priority: Int, tag: String?, message: String, t: Throwable? ) {
        //debug log level 이하는 file 에 저장하지 않음
        if (priority <= Log.DEBUG) return

        try {
            val path = "logger"
            val fileNameTimeStamp = SimpleDateFormat(
                log_file_foramt_pattern,
                Locale.getDefault()
            ).format(Date())
            val logTimeStamp = SimpleDateFormat(
                "E MMM dd yyyy 'at' hh:mm:ss:SSS aaa",
                Locale.getDefault()
            ).format(Date())
            //val fileName = fileNameTimeStamp + ".html"
            val fileName = fileNameTimeStamp + ".txt"

            // Create file
            val file = generateFile(path, fileName)

            // If file created or exists save logs
            if (file != null) {
                Log.e(LOG_TAG,"file logging path:${file.absolutePath}")
                val writer = FileWriter(file, true)

                writer.append("[${logTimeStamp}] : ")
                    .append(tag ?: LOG_TAG)
                    .append(" - ")
                    .append(message)
                    .append("\n")
                writer.flush()
                writer.close()
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error while logging into file : $e")
        }

    }

    override fun createStackElementTag(element: StackTraceElement): String {
        // Add log statements line number to the log
        return super.createStackElementTag(element) + " - " + element.lineNumber
    }

    /*  Helper method to create file*/
    private fun generateFile(path: String, fileName: String): File? {
        var file: File? = null
        if ( isExternalStorageAvailable() ) {
            val diskPath = context.getExternalFilesDir(null)!!.absolutePath
            val root = File(
                diskPath,separator + path
            )

            var dirExists = true

            if (!root.exists()) {
                dirExists = root.mkdirs()
            }

            if (dirExists) {
                val isFileNotExist = root.listFiles { file -> file.nameWithoutExtension == fileName }.isEmpty()
                //유지 날짜 이전 로깅 데이터 삭제
                if( isFileNotExist ) {
                    val listFiles = root.listFiles { file ->
                        file.nameWithoutExtension.getCalendar(log_file_foramt_pattern)
                                ?.after(Calendar.getInstance().beforeDay(FILE_MAINTAIN_DAYS).timeClear()) != true
                    }
                    try {
                        listFiles.forEach { it.delete() }
                    } catch (e: Exception) {
                    }
                }
                file = File(root, fileName)
            }
        }
        return file
    }

    /* Helper method to determine if external storage is available*/
    private fun isExternalStorageAvailable(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }
}