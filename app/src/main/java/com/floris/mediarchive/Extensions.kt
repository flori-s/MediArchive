package com.floris.mediarchive

import java.security.MessageDigest
import java.util.regex.Pattern

/**
 * Converts a string to a SHA-256 hash.
 *
 * This function first converts the string to a byte array. Then, it gets an instance of the SHA-256 MessageDigest,
 * and uses it to digest the byte array. Finally, it joins the resulting byte array into a string, with each byte
 * represented as a two-digit hexadecimal number.
 *
 * @return The SHA-256 hash of the string.
 */
fun String.toSHA256(): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.joinToString("") { "%02x".format(it) }
}

/**
 * Checks if a string is a valid email address.
 *
 * This function uses a regular expression to check if the string matches the general structure of an email address.
 * The regular expression used checks for:
 * - Between 1 and 256 alphanumeric characters, including +, ., _, %, -, and +, before the @ symbol.
 * - An @ symbol.
 * - Between 1 and 64 alphanumeric characters, including -, after the @ symbol and before a period.
 * - A period followed by between 1 and 25 alphanumeric characters, including -, after the @ symbol.
 * - This pattern can be repeated one or more times.
 *
 * @return Boolean value indicating whether the string is a valid email address.
 */
fun String.isValidEmail(): Boolean {
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" + // Between 1 and 256 alphanumeric characters, including +, ., _, %, -, and +, before the @ symbol.
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + // Between 1 and 64 alphanumeric characters, including -, after the @ symbol and before a period.
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + // A period followed by between 1 and 25 alphanumeric characters, including -, after the @ symbol.
                ")+" // This pattern can be repeated one or more times.
    )
    return emailPattern.matcher(this).matches()
}

/**
 * Checks if a string is a valid password.
 *
 * This function uses a regular expression to check if the string meets the following password requirements:
 * - At least 8 characters long.
 * - Contains at least one digit.
 * - Contains at least one letter.
 * - Contains at least one special character (@, #, $, %, ^, &, +, =).
 *
 * @return Boolean value indicating whether the string is a valid password.
 */
fun String.isValidPassword(): Boolean {
    val passwordPattern = Pattern.compile(
        "^(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-zA-Z])" +  //at least 1 letter
                "(?=.*[@#$%^&+=!])" +  //at least 1 special character
                ".{8,}"               //at least 8 characters
    )
    return passwordPattern.matcher(this).matches()
}