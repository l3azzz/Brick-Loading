package com.simplified.qr_barcode_scanner.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken // <-- Use this one
import com.simplified.qr_barcode_scanner.data.db.dao.QrScanDataDao
import com.simplified.qr_barcode_scanner.data.db.entity.QrScanData
import com.simplified.qr_barcode_scanner.utils.mlkit.QrContentType
import com.simplified.qr_barcode_scanner.utils.mlkit.QrFunction

@Database(
    entities = [QrScanData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AppDatabase.Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun qrScanDataDao(): QrScanDataDao

    object Converters {
        private val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun fromQrContentTypeList(value: List<QrContentType>?): String? {
            return gson.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toQrContentTypeList(value: String?): List<QrContentType>? {
            if (value == null) return null
            // This now correctly uses the TypeToken from Gson
            val listType = object : TypeToken<List<QrContentType>>() {}.type
            return gson.fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromQrFunctionList(value: List<QrFunction>?): String? {
            return gson.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toQrFunctionList(value: String?): List<QrFunction>? {
            if (value == null) return null
            // This now correctly uses the TypeToken from Gson
            val listType = object : TypeToken<List<QrFunction>>() {}.type
            return gson.fromJson(value, listType)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "qr_scan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
