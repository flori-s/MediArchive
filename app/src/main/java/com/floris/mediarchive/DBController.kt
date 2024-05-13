package com.floris.mediarchive

import android.os.StrictMode
import android.util.Log
import java.sql.DriverManager
import java.sql.Connection
import java.sql.ResultSet

/**
 * This is the DBController class.
 * It handles the database operations of the application.
 * @author Floris
 */
class DBController private constructor() {
    // Database connection details
    private var host = "10.0.2.2" // Use IP address for localhost on emulator
    private var port = "8889" // MAMP's MySQL server runs on port 8889
    private var dbname = "hospitalDB" // Your database name
    private var user = "root" // Your username
    private var pass = "root" // Your password
    var conn: Connection? = null

    /**
     * This is the initializer block.
     * It sets up the database connection.
     */
    init {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            conn = DriverManager.getConnection(
                "jdbc:mysql://$host:$port/$dbname",
                user,
                pass
            )
            Log.d("DBController", "Connected to database")
        } catch (ex: Exception) {
            Log.e("DBController", "Failed to connect to database")
            ex.printStackTrace()
        }
    }

    /**
     * This function checks if a user exists in the database.
     * @param username The username of the user.
     * @param hasedPassword The hashed password of the user.
     * @return True if the user exists, false otherwise.
     */
    fun checkUser(username: String, hasedPassword: String): Boolean {
        val query = "SELECT * FROM account WHERE username = '$username' AND password = '$hasedPassword'"
        val result = select(query)
        return result?.next() ?: false
    }

    /**
     * This function executes a SELECT query on the database.
     * @param query The SELECT query to execute.
     * @return The result set of the query.
     */
    fun select(query: String): ResultSet? {
        return try {
            val stmt = conn?.createStatement()
            stmt?.executeQuery(query)
        } catch (ex: Exception) {
            null
        }
    }

    /**
     * This function executes an INSERT query on the database.
     * @param query The INSERT query to execute.
     * @return True if the insertion was successful, false otherwise.
     */
    fun insert(query: String): Boolean {
        return try {
            val stmt = conn?.createStatement()
            (stmt?.executeUpdate(query) ?: 0) > 0
        } catch (ex: Exception) {
            false
        }
    }

    /**
     * This function executes an UPDATE query on the database.
     * @param query The UPDATE query to execute.
     * @return True if the update was successful, false otherwise.
     */
    fun update(query: String): Boolean {
        return try {
            val stmt = conn?.createStatement()
            (stmt?.executeUpdate(query) ?: 0) > 0
        } catch (ex: Exception) {
            false
        }
    }

    /**
     * This function executes a DELETE query on the database.
     * @param query The DELETE query to execute.
     * @return True if the deletion was successful, false otherwise.
     */
    fun delete(query: String): Boolean {
        return try {
            val stmt = conn?.createStatement()
            (stmt?.executeUpdate(query) ?: 0) > 0
        } catch (ex: Exception) {
            false
        }
    }

    companion object {
        /**
         * The singleton instance of DBController.
         */
        @Volatile
        private var INSTANCE: DBController? = null

        /**
         * This function returns the singleton instance of DBController.
         * @return The singleton instance of DBController.
         */
        fun getInstance(): DBController {
            return INSTANCE ?: synchronized(this) {
                val instance = DBController()
                INSTANCE = instance
                instance
            }
        }
    }
}