package com.example.managingtransactions

import mu.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Suppress("SqlResolve")
@Component
class BookingService(private val jdbcTemplate: JdbcTemplate) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @Transactional
    fun book(vararg persons: String?) {
        persons.forEach {
            logger.info { "Booking $it in a seat..." }
            jdbcTemplate.update("INSERT INTO bookings(first_name) VALUES (?)", it)
        }
    }

    fun findAllBookings(): List<String> {
        val rowMapper = RowMapper<String> { rs, _ -> rs.getString("first_name") }
        return jdbcTemplate.query("SELECT first_name FROM bookings",rowMapper)
    }
}