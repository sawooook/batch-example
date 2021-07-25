package com.example.batch.config

import com.example.batch.connect.Billing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import javax.batch.api.chunk.ItemWriter
import javax.sql.DataSource
import kotlin.math.log

@Configuration
class JdbcCursorItemReaderJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val dataSource: DataSource
) {

    private companion object {
        const val chunkSize = 10
    }

    @Bean
    fun jdbcCursorItemReaderJob() {
        jobBuilderFactory.get("jdbcCursorItemReaderJob")
    }

    @Bean
    fun jdbcCursorItemReaderStep() {
        stepBuilderFactory.get("jdbcCursorItemReaderJob")
            .chunk<Billing, Billing>(chunkSize)
            .reader(jdbcCursorItemReader())
            .writer(jdbcCursorItemWriter())
            .build()
    }

    @Bean
    fun jdbcCursorItemReader() =
        JdbcCursorItemReaderBuilder<Billing>()
            .fetchSize(chunkSize)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper(Billing::class.java))
            .sql("select * from Billing")
            .name("jdbcCursorItemReader")
            .build()


    private fun jdbcCursorItemWriter() =
        org.springframework.batch.item.ItemWriter<Billing> { list ->
            for (pay in list) {
                println(pay)
            }
        }
}