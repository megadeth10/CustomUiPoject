package com.example.utils

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentUris
import android.content.Context
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Build.*
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.Size
import android.util.TypedValue
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        val TAG = Util::class.java.simpleName

        fun ratioSize(activity: Activity, width: Float, height: Float) : Point {
            return Util.ratioSize(activity.baseContext, width, height)
        }

        /**
         * Width 기준으로 height를 scale 계산 함수
         * @param context
         * @param width 디자인 가이드의 값
         * @param height 디자인 가이드의 값
         * @return 스케일한 크기
         */
        fun ratioSize(context: Context, width: Float, height: Float) : Point {
            val scaledPoint = Point(0, 0)
            try {
                val point = Util.windowSize(context)
                val scale = point.x / width
                val scaleHeight = scale * height
                scaledPoint.x = point.x
                scaledPoint.y = scaleHeight.toInt()
            } catch (e: Exception){
                Log.e(TAG, String.format("ratioSize() %s", e.message))
            }
            return scaledPoint
        }

        /**
         * width, height scale 계산 함수
         * @param context
         * @param designSize 비율 계산을 위한 디자인 가이드 화면 크기
         * @param guideSize 적용할 뷰 크기
         * @return
         */
        fun ratioSize(context: Context,
                      designSize: Size,
                      guideSize: Size
        ) : Size {
            var scaledSize = Size(0, 0)
            try {
                val point = Util.windowSize(context)
                val widthScale = guideSize.width * (point.x / designSize.width)
                val heightScale = guideSize.height * (point.y / designSize.height)
                scaledSize = Size(widthScale, heightScale)
            } catch (e: Exception){
                Log.e(TAG, String.format("ratioSize() %s", e.message))
            }
            return scaledSize
        }

        /**
         * 디자인 기준치로 스케일된 값 계산
         * @param context
         * @param dpDesignSize dp 기준 디자인 화면 사이즈
         * @param dpGuideSize dp 기준 가이드 사이즈
         * @return
         */
        fun ratioSize(context: Context,
                      dpDesignSize: Int,
                      dpGuideSize: Int
        ) : Float {
            var pixelGuideSize = pxFromDp(context, dpGuideSize.toFloat())
            try {
                val point = Util.windowSize(context)
                val pixelDesignSize = pxFromDp(context, dpDesignSize.toFloat())
                pixelGuideSize *= (point.x / pixelDesignSize)
            } catch (e: Exception) {
                Log.e(TAG, String.format("ratioSize() %s", e.message))
            }
            return pixelGuideSize
        }

        /**
         * 출력되는 뷰의 너비로 높이를 계산해 주는 함수
         * @param displayWidth
         * @param designedWidth
         * @param designedHeight
         * @return
         */
        fun ratioSize(displayWidth: Float,
                      designedWidth: Float,
                      designedHeight: Float
        ): Float {
            var scaleHeight = 0f
            try {
                val scaleSize = displayWidth / designedWidth
                scaleHeight = scaleSize * designedHeight
            } catch (e: Exception) {
                Log.e(TAG, String.format("ratioSize() %s", e.message))
            }

            return scaleHeight
        }

        fun windowSize(context: Context?) : Point {
            val point = Point()
            context?.let { context ->
                var surfaceRotation = Surface.ROTATION_0
                val softNavigationHeight = navigationHeight(context)
//                if (VERSION.SDK_INT >= VERSION_CODES.R) {
//                    val display = context.display
//                    surfaceRotation = display?.rotation ?: Surface.ROTATION_0
//                    display?.getRealSize(point)
//                } else {
                    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    val display = wm.defaultDisplay
                    surfaceRotation = display.rotation
                    display.getRealSize(point)
//                }

                when (surfaceRotation) {
                    Surface.ROTATION_180,
                    Surface.ROTATION_0 -> {
                        point.y = point.y - softNavigationHeight
                    }
                    Surface.ROTATION_270,
                    Surface.ROTATION_90 -> {
                        point.x = point.x - softNavigationHeight
                    }
                }
            }

            return point
        }

        /**
         * 화면 rotation 위치값 확인
         * @param context
         * @return Surface.ROTATION_0 ...
         */
        fun getScreenOrientation(context: Context) : Int {
            val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val orientation = display.rotation
            return orientation
        }

        /**
         * soft navigation bar Height
         * @param context
         * @return int pixel value
         */
        fun navigationHeight(context: Context) : Int {
            var navigationBarHeight = 0
            val resourceId = context.resources.getIdentifier(
                    "navigation_bar_height",
                    "dimen",
                    "android"
            )
            if (resourceId > 0) {
                navigationBarHeight = context.resources.getDimensionPixelSize(resourceId)
            }
            return navigationBarHeight
        }

        /**
         * status bar Height
         * @param context
         * @return int pixel value
         */
        fun statusBarHeight(context: Context?) : Int {
            var result = 0
            context?.let { context ->
                val resourceId = context.resources.getIdentifier(
                        "status_bar_height",
                        "dimen",
                        "android"
                )
                if (resourceId > 0) {
                    result = context.resources.getDimensionPixelSize(resourceId)
                }
            }
            return result
        }

        /**
         * pixel 값을 해당 화면의 density로 변경해줌
         * @param context
         * @param px pixel size
         * @return dp value
         */
        fun dpFromPx(context: Context, px: Float) : Float {
            return px / context.resources.displayMetrics.density
        }

        /**
         * dp값을 pixel값으로 변경 해준다.
         * @param context
         * @param dp
         * @return
         */
        fun pxFromDp(context: Context, dp: Float) : Float {
            return dp * context.resources.displayMetrics.density
        }

        /**
         * sp값을 pixel값으로 변경 해준다.
         * @param context
         * @param sp
         * @return
         */
        fun pxFromSp(context: Context, sp: Float) : Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
        }

        /**
         * format된 문자로 변형
         * @param format: format String
         * @param date: calendar Date
         * @return date format string
         */
        fun simpleDateFormat(format: String, date: Date?) : String {
            return SimpleDateFormat(format).format(date)
        }

        /**
         * 문자를 date로 변형
         * @param format: format String
         * @param date: utc date string
         * @return gmt date
         */
        fun parseSimpleDateFormat(format: String, date: String): Date {
            return try {
                val df = SimpleDateFormat(format, Locale.ENGLISH)
                df.timeZone = TimeZone.getTimeZone("UTC")
                df.parse(date)
            } catch (e: ParseException) {
                Date()
            }
        }

        /**
         * visible 상태를 체크해서 속성이 다르면 변경하는 함수
         * @param view
         * @param newVisible
         */
        fun setCheckVisible(view: View, newVisible: Int) {
            val currentVisible = view.visibility
            if (currentVisible != newVisible) {
                view.visibility = newVisible
            }
        }

        /**
         * keyboard show
         * @param context
         * @param view activity.currentFocus
         */
        fun showKeyboard(context: Context?, view: View?) {
            if (context != null && view != null) {
                val inputManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        /**
         * keyboard close
         * @param context
         * @param view activity.currentFocus
         */
        fun hideKeyboard(context: Context?, view: View?) {
            if (context != null && view != null) {
                val inputManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        /**
         * 소수점 format
         * @param format format ex) "%.1f"
         * @param num float number
         */
        fun setFloatFormat(format: String?, num: Float): String? {
            return try {
                String.format(format!!, num)
            } catch (e: Exception) {
                Log.e(TAG, "setFloatFormat Exception : ", e)
                ""
            }
        }

        /**
         * view visible 설정
         */
        fun setVisible(view: View?, isVisible: Boolean) {
            view?.let {
                var newVisible = View.GONE
                if (isVisible) {
                    newVisible = View.VISIBLE
                }
                if (it.visibility != newVisible) {
                    it.visibility = newVisible
                }
            }
        }

        /**
         * view enable 설정
         */
        fun setEnable(view: View?, isEnable: Boolean) {
            view?.let {
                if (it.isEnabled != isEnable) {
                    it.isEnabled = isEnable
                }
            }
        }

        /**
         * getColor
         */
        fun getColor(context: Context, @ColorRes color: Int): Int {
            return if (VERSION.SDK_INT >= VERSION_CODES.M) {
                context.resources.getColor(color, context.theme)
            } else {
                context.resources.getColor(color)
            }
        }

        /**
         * getDrawable
         */
        fun getDrawable(context: Context, @DrawableRes drawable: Int
        ): Drawable? {
            return if (VERSION.SDK_INT >= VERSION_CODES.M) {
//                var theme = context.theme
//                if(context.theme == null){
//
//                }
                context.resources.getDrawable(drawable, null)
            } else {
                context.resources.getDrawable(drawable)
            }
        }

        /**
         * 갤러리로 이미지 선택시 file path
         * @param ctx
         * @param uri
         * @return file://제거된 path
         * @throws FileNotFoundException
         */
        fun getUriPath(context: Context, uri: Uri?) : String {
//            // check here to KITKAT or new version
//            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
//
//            // DocumentProvider
//            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//                // ExternalStorageProvider
//                if (RealPathUtil.isExternalStorageDocument(uri)) {
//                    val docId = DocumentsContract.getDocumentId(uri)
//                    val split = docId.split(":".toRegex()).toTypedArray()
//                    val type = split[0]
//                    if ("primary".equals(type, ignoreCase = true)) {
//                        return (Environment.getExternalStorageDirectory().toString() + "/"
//                                + split[1])
//                    }
//                } else if (RealPathUtil.isDownloadsDocument(uri)) {
//                    val id = DocumentsContract.getDocumentId(uri)
//                    val contentUri = ContentUris.withAppendedId(
//                            Uri.parse("content://downloads/public_downloads"),
//                            java.lang.Long.valueOf(id))
//                    return RealPathUtil.getDataColumn(context, contentUri, null, null)
//                } else if (RealPathUtil.isMediaDocument(uri)) {
//                    val docId = DocumentsContract.getDocumentId(uri)
//                    val split = docId.split(":".toRegex()).toTypedArray()
//                    val type = split[0]
//                    var contentUri: Uri? = null
//                    if ("image" == type) {
//                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                    } else if ("video" == type) {
//                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//                    } else if ("audio" == type) {
//                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//                    }
//                    val selection = "_id=?"
//                    val selectionArgs = arrayOf(split[1])
//                    return RealPathUtil.getDataColumn(context, contentUri, selection,
//                            selectionArgs)
//                }
//            } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
//                // Return the remote address
//                return if (RealPathUtil.isGooglePhotosUri(uri))
//                    uri.lastPathSegment!!
//                else
//                    RealPathUtil.getDataColumn(context, uri, null, null)
//            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
//                return uri.path!!
//            }
//
            return ""
        }

        /**
         * drawableLeft...등에 tint color 추가해야 할때
         * @param textView
         * @param color
         */
        fun setTintColor(textView: TextView, @ColorRes color: Int) {
            for (drawable in textView.compoundDrawablesRelative) {
                drawable?.let {
                    it.colorFilter = PorterDuffColorFilter(
                            ContextCompat.getColor(textView.context, color),
                            PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }

        /**
         * 정사각형 비율 계산
         * @param context
         * @param designSize
         * @param guideSize
         * @return
         */
        fun ratioQuadRateSize(
                context: Context,
                designSize: Int,
                guideSize: Int
        ): Size {
            var scaledSize = Size(0, 0)
            try {
                val point = windowSize(context)
                val widthScale = guideSize * (point.x.toFloat() / designSize)
                scaledSize = Size(widthScale.toInt(), widthScale.toInt())
            } catch (e: Exception) {
                Log.e(TAG, String.format("ratioSize() %s", e.message))
            }
            return scaledSize
        }

        /**
         * 하나의 TextView에 폰트 사이즈 다르게 표현 하는 함수
         * @param text 원문 텍스트
         * @param afterChar 폰트 크기 변경 기준 문자
         * @param reduceBy scaleSize
         * @return
         */
        fun spannableStringBuilder(
                text: String,
                afterChar: Char,
                reduceBy: Float
        ): SpannableStringBuilder {
            val smallSizeText = RelativeSizeSpan(reduceBy)
            val ssBuilder = SpannableStringBuilder(text)
            ssBuilder.setSpan(
                    smallSizeText,
                    text.indexOf(afterChar),
                    text.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return ssBuilder
        }

        /**
         * 파일 쓰기
         */
        fun writeToFile(
                folderName: String,
                fileName: String,
                data: String
        ): Boolean {
            try {
                val folder = File(folderName)
                if (!folder.exists()) {
                    folder.mkdirs()
                }
                val file = File(folderName + fileName)
                if (!file.exists()) {
                    file.createNewFile()
                }
                val stream = FileOutputStream("$folderName/$fileName")
                stream.write(data.toByteArray())
                stream.close()
                return true
            } catch (e: Exception) {
                Log.e(TAG, "writeToFile File write failed: $e")
            }
            return false
        }

        /**
         * 파일 읽기
         */
        fun readToFile(folderName: String, fileName: String): String? {
            try {
                val folder = File(folderName)
                if (!folder.exists()) {
                    return null
                }
                val file = File(folderName + fileName)
                if (!file.exists()) {
                    return null
                }
                val bytes = ByteArray(file.length().toInt())
                val stream =
                    FileInputStream("$folderName/$fileName")
                stream.read(bytes)
                stream.close()
                return String(bytes)
            } catch (e: Exception) {
                Log.e(TAG, "writeToFile File write failed: $e")
            }
            return null
        }

        /**
         * 두 목록의 일치 여부 확인
         */
        fun checkDifferentData(
                currentData: ArrayList<Int>,
                newData: ArrayList<Int>
        ): Boolean {
            val differentItem: HashMap<Int, Int> = HashMap(0)
            for (i in currentData.indices) {
                val item = currentData[i]
                differentItem[item] = item
            }
            for (i in newData.indices) {
                val newItem = newData[i]
                if (differentItem[newItem] != null) {
                    differentItem.remove(newItem)
                } else {
                    differentItem[newItem] = newItem
                }
            }
            return differentItem.size > 0
        }

        /**
         * Y/N return String
         */
        fun booleanToYN(yn : Boolean) : String {
            return if (yn) "Y" else "N"
        }

        /**
         * Y/N return Boolean
         */
        fun ynToBoolean(yn : String) : Boolean {
            return yn == "Y"
        }

        /**
         * GMT를 포함한 Calender
         */
        fun getCalender() = Calendar.getInstance(TimeZone.getDefault())

        /**
         * RecyclerView delete animator
         * 아이템 업데이트 시 반짝임 효과 제거
         */
        fun deleteRecyclerAnimator(recyclerView : RecyclerView) {
            val animator = recyclerView.itemAnimator
            if (animator != null && animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }
        }

        /**
         * Notification State
         */
        fun checkNotification(context : Context) : HashMap<String, Boolean> {
            val map = HashMap<String, Boolean>()

            if (VERSION.SDK_INT >= VERSION_CODES.O) {
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val listNotificationChannel =
                    notificationManager.notificationChannels as ArrayList<NotificationChannel>

                listNotificationChannel.forEach {
                    map.put(it.id, it.importance != NotificationManager.IMPORTANCE_NONE)
                }
            }

            return map
        }
    }
}