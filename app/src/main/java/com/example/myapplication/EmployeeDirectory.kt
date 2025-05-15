package com.example.myapplication

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
class EmployeeDirectory(private val employees: MutableList<Employee> = mutableListOf()) {

    fun addEmployee(employee: Employee) {
        employees.add(employee)
    }

    fun removeEmployee(employeeName: String) {
        employees.removeAll { it.name == employeeName }
    }

    fun findEmployee(employeeName: String): Employee? {
        return employees.find { it.name == employeeName }
    }

    fun listEmployees(): List<Employee> = employees

    fun saveToFile(context: Context) {
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).use { output ->
            val jsonString = Json.encodeToString(this)
            output.write(jsonString.toByteArray())
        }
    }

    companion object {
        private const val FILENAME = "employees.json"

        fun loadFromFile(context: Context): EmployeeDirectory {
            val file = File(context.filesDir, FILENAME)
            if (!file.exists()) return EmployeeDirectory()

            return context.openFileInput(FILENAME).use { input ->
                val jsonString = input.bufferedReader().use { it.readText() }
                Json.decodeFromString(jsonString)
            }
        }
    }
}