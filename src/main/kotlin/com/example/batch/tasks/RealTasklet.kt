package com.example.batch.tasks

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class RealTasklet : Tasklet{
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        println("========================")
        // 업무의 끝을 알려줘야함
        return RepeatStatus.FINISHED
    }
}