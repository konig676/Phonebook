package com.example.myapplication
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var employeeDirectory: EmployeeDirectory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        employeeDirectory = EmployeeDirectory.loadFromFile(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAddEmployeeClicked(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Добавить сотрудника")

        val dialogLayout = inflater.inflate(R.layout.add_employee_dialog, null)
        val editTextName = dialogLayout.findViewById<EditText>(R.id.editTextName)
        val editTextPhone = dialogLayout.findViewById<EditText>(R.id.editTextPhone)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Добавить") { dialogInterface, i ->
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            // Получите значения из других полей ввода

            val newEmployee = Employee(
                name = name,
                phoneNumber = phone,
                position = "Разработчик",
                email = "email@example.com",
                address = "Неизвестно",
                birthDate = LocalDate.now().toString()
            )
            employeeDirectory.addEmployee(newEmployee)
            employeeDirectory.saveToFile(this)
        }
        builder.setNegativeButton("Отмена", null)
        builder.show()
    }

    fun onListEmployeesClicked(view: View) {
        val employees = employeeDirectory.listEmployees()
        val employeeNames = employees.joinToString(separator = "\n") { it.name }
        Toast.makeText(this, employeeNames, Toast.LENGTH_LONG).show()
    }

}