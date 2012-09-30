package com.uphea.service;

import com.uphea.domain.Answer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class StatsServiceTest {

	@Test
	public void calcAnswerDistribution1() {
		Answer a1 = new Answer();
		a1.setVotes(2);

		Answer a2 = new Answer();
		a2.setVotes(2);

		List<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		answers.add(a2);

		StatsService statsService = new StatsService();
		statsService.calcAnswerDistribution(answers);

		Assert.assertEquals(5000, a1.getVotesPercent());
		Assert.assertEquals(5000, a2.getVotesPercent());
	}

	@Test
	public void calcAnswerDistribution2() {
		Answer a1 = new Answer();
		a1.setVotes(2);

		Answer a2 = new Answer();
		a2.setVotes(1);

		List<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		answers.add(a2);

		StatsService statsService = new StatsService();
		statsService.calcAnswerDistribution(answers);

		Assert.assertEquals(6667, a1.getVotesPercent());
		Assert.assertEquals(3333, a2.getVotesPercent());
	}

	@Test
	public void calcAnswerDistribution3() {
		Answer a1 = new Answer();
		a1.setVotes(1342);

		Answer a2 = new Answer();
		a2.setVotes(1783);

		List<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		answers.add(a2);

		StatsService statsService = new StatsService();
		statsService.calcAnswerDistribution(answers);

		Assert.assertEquals(4294, a1.getVotesPercent());
		Assert.assertEquals(5706, a2.getVotesPercent());
	}

	@Test
	public void calcAnswerDistribution4() {
		Answer a1 = new Answer();
		a1.setVotes(0);

		Answer a2 = new Answer();
		a2.setVotes(1);

		Answer a3 = new Answer();
		a3.setVotes(4);

		Answer a4 = new Answer();
		a4.setVotes(1);

		List<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		answers.add(a2);
		answers.add(a3);
		answers.add(a4);

		StatsService statsService = new StatsService();
		statsService.calcAnswerDistribution(answers);

		Assert.assertEquals(0, a1.getVotesPercent());
		Assert.assertEquals(1666, a2.getVotesPercent());
		Assert.assertEquals(6667, a3.getVotesPercent());
		Assert.assertEquals(1667, a4.getVotesPercent());
	}


}
