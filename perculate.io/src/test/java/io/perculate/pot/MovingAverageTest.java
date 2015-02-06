package io.perculate.pot;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class MovingAverageTest {

	@Test
	public void getAverageValueWithNoPointsIsNaN() throws Exception {
		MovingAverage movingAverage = new MovingAverage(1);

		Double averageValue = movingAverage.getAverageValue();

		assertThat(averageValue, is(equalTo(Double.NaN)));
	}

	@Test
	public void getAverageValueWhenAtCapacityIsAverage() throws Exception {
		MovingAverage movingAverage = new MovingAverage(3);
		movingAverage.addValue(3);
		movingAverage.addValue(4);
		movingAverage.addValue(9);
		double sum = 3 + 4 + 9;
		double avg = sum / 3;

		Double averageValue = movingAverage.getAverageValue();

		assertThat(averageValue, is(equalTo(avg)));
	}

	@Test
	public void elementsAreEvictedFifoWhenCapacityIsMet() throws Exception {
		MovingAverage movingAverage = new MovingAverage(1);
		movingAverage.addValue(3);
		movingAverage.addValue(4);

		Double averageValue = movingAverage.getAverageValue();

		assertThat(averageValue, is(equalTo(Double.valueOf(4))));
	}

	@Test
	public void elementsAreEvictedFifoWhenCapacityIsMetForCapacityOne() throws Exception {
		MovingAverage movingAverage = new MovingAverage(1);
		movingAverage.addValue(3);
		movingAverage.addValue(4);
		movingAverage.addValue(5);

		Double averageValue = movingAverage.getAverageValue();

		assertThat(averageValue, is(equalTo(Double.valueOf(5))));
	}

	@Test
	public void averageOfTenNumbers() throws Exception {
		double[] numbers = { 42, 16, 16, 88, 5, 421, 45, 3, 7, 8 };
		double expectedAverage = sum(numbers) / numbers.length;

		MovingAverage movingAverage = new MovingAverage(10);
		for (double number : numbers) {
			movingAverage.addValue(number);
		}
		Double averageValue = movingAverage.getAverageValue();

		assertThat(averageValue, is(equalTo(expectedAverage)));
	}

	@Test
	public void averageWithEvictedElements() throws Exception {
		double[] lastFive = { 421, 45, 3, 7, 8 };
		double[] allTen = { 42, 16, 16, 88, 5, 421, 45, 3, 7, 8 };
		double expectedAverage = sum(lastFive) / lastFive.length;

		MovingAverage movingAverage = new MovingAverage(5);
		for (double number : allTen) {
			movingAverage.addValue(number);
		}
		Double averageValue = movingAverage.getAverageValue();

		assertThat(averageValue, is(equalTo(expectedAverage)));
	}

	private double sum(double[] doubles) {
		double sum = 0D;
		for (double value : doubles) {
			sum += value;
		}
		return sum;
	}

}
