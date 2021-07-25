package com.example.batch.config

import com.example.batch.tasks.RealTasklet
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.logging.Logger


@Configuration
class TestJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    /*
    * JOB안에는 여러개의 Step을 둘 수 있음.
    * Step 안에 tasklet혹은 (reader, processor, writer)의 묶음이 존재함
    * takelet or reader, processor ,wirter를 이용 같은 레벨이
    * */

    /*
    * tasklet
    *
    * itemReader, itemProcessor, itemWriter
    * Reader는 데이터를 하나씩 읽어서 processor에게 전달함 ( 1건 단위 )
    * processor는 읽어온 데이터를 가공을함 ( 1건 단위 )
    * processor를 모 은후 chunk단위로 쌓이게 되면
    * writer로 전달하여서 일괄 저장함 ( 청크 단위 )
    *
    * */

    @Bean
    fun testJob() {
        jobBuilderFactory.get("test")
            .start(firstStep())
            .build()
    }

    /*
    * Batch Step을 생성
    * tasklet step안에서 수행될 기능들을 명시함
    *
    * */
    @Bean
    fun firstStep() =
        stepBuilderFactory.get("step1")
            .tasklet { contribution, chunkContext ->
                return@tasklet RepeatStatus.FINISHED
            }.build()
    
    
    //// tasklet

    @Bean
    fun sampleTasklet() {
        jobBuilderFactory.get("sampleTaskletJob")
            .incrementer(RunIdIncrementer())
            .flow(sampleTaskletStep())
    }

    private fun sampleTaskletStep() =
        stepBuilderFactory.get("sampleTaskletStep")
            .tasklet { contribution, chunkContext ->
                println("===================")
                return@tasklet RepeatStatus.FINISHED
            }.build()
}

