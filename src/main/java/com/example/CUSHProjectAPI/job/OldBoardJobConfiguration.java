package com.example.CUSHProjectAPI.job;

import com.example.CUSHProjectAPI.entity.BoardCountEntity;
import com.example.CUSHProjectAPI.entity.BoardEntity;
import com.example.CUSHProjectAPI.enums.Status;
import com.example.CUSHProjectAPI.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Configuration
public class OldBoardJobConfiguration {

    private final BoardRepository boardRepository;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private static final int chunkSize = 10;


    @Bean
    public Job oldBoardJob() {
        return jobBuilderFactory.get("oldBoardJob")
                .start(oldBoardJobStep())
                .next(cntByStatusStep())
                .build();
    }

    @Bean
    @JobScope
    public Step oldBoardJobStep(){
        return stepBuilderFactory.get("oldBoardJobStep")
                .<BoardEntity, BoardEntity>chunk(chunkSize)
                .reader(oldBoardReader()) //구현해야함 게시물을 올린지 1주일 이상된 보드를 읽어온다
                .processor(oldBoardProcessor()) //구현해야함 판매완료가 아닐경우 ~~~을 수행한다
                .writer(oldBoardWriter()) //구현해야함 처리된 게시물을 DB에 저장한다
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<BoardEntity> oldBoardReader() {
        return new RepositoryItemReaderBuilder<BoardEntity>()
                .repository(boardRepository)
                .methodName("findByUpdatedDateBeforeAndStatusEquals")
                .arguments(LocalDateTime.now().minusWeeks(1), Status.sell)
                .pageSize(chunkSize)
                .sorts(Collections.singletonMap("updatedDate", Sort.Direction.ASC))
                .name("oldBoardReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<BoardEntity, BoardEntity> oldBoardProcessor(){
        return boardEntity -> {
            boardEntity.setStatus(Status.old);
            return boardEntity;
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<BoardEntity> oldBoardWriter(){
        return new RepositoryItemWriterBuilder<BoardEntity>()
                .repository(boardRepository)
                .build();
    }


    @Bean
    @JobScope
    public Step cntByStatusStep() {
        return stepBuilderFactory
                .get("cntByStatusStep")
                .<BoardEntity, BoardCountEntity>chunk(chunkSize)
                .reader(cntByStatusReader()) //구현해야함 게시물을 올린지 1주일 이상된 보드를 읽어온다
                .processor(processor())
                .writer(cntByStatusWriter()) //구현해야함 처리된 게시물을 DB에 저장한다
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<BoardEntity> cntByStatusReader() {
        return new JpaPagingItemReaderBuilder<BoardEntity>()
                .queryString("select b.status as status ,count(b.status) as count from BoardEntity b group by b.status")
                .pageSize(chunkSize)
                .entityManagerFactory(entityManagerFactory)
                .name("cntByStatusReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Object, BoardCountEntity> processor() {
        return items -> {
            Object[] objects = (Object[]) items;
            Iterator<Object> iterator = Arrays.stream(objects).iterator();
            List<String> list = new ArrayList<>();

            while(iterator.hasNext()){
                String value = iterator.next().toString();
                list.add(value);
            }

            return BoardCountEntity.builder()
                    .statusName(list.get(0))
                    .statusCount(Long.parseLong(list.get(1)))
                    .batchDate(LocalDate.now())
                    .build();
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<BoardCountEntity> cntByStatusWriter() {
        return new JpaItemWriterBuilder<BoardCountEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}



