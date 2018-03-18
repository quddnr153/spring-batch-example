package bw.batch.example.job;

import bw.batch.example.blog.model.Post;
import bw.batch.example.blog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Queue;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Byungwook Lee on 2018-03-18
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Configuration
public class PostingStatistics {
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private PostRepository postRepository;

	@Scheduled(cron = "* 20 * * * ?")
	public void launch(@Qualifier("postingStatisticsJob") Job job) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		jobLauncher.run(job, new JobParametersBuilder().addLong("run.id", System.currentTimeMillis()).toJobParameters());
	}

	@Bean
	public Job postingStatisticsJob(@Qualifier("postingsStep") Step step) {
		return jobBuilderFactory.get("postingStatisticsJob")
				.start(step)
				.build();
	}

	@Bean
	protected Step postingsStep(
			@Qualifier("postingReader") ItemReader<Post> reader,
			@Qualifier("postingProcessor") ItemProcessor<? super Post, ? extends Post> processor,
			@Qualifier("postingWriter") ItemWriter<Post> writer) {
		return stepBuilderFactory.get("postingsStep")
				.<Post, Post>chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	@Bean
	protected ItemReader<Post> postingReader() {
		Queue<Post> posts = new ArrayDeque<>(postRepository.findAll());

		return posts::poll;
	}

	@Bean
	protected ItemProcessor<? super Post, ? extends Post> postingProcessor() {
		return (post) -> {
			post.setContent(post.getContent() + ", updated: " + LocalDateTime.now().toString());

			return post;
		};
	}

	@Bean
	protected ItemWriter<Post> postingWriter() {
		return posts -> posts.forEach(post -> postRepository.updatePostContent(post.getContent(), post.getId()));
	}
}
