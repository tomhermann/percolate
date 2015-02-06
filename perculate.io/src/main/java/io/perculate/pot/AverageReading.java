package io.perculate.pot;

import java.util.Date;

public class AverageReading {
	private final Date creationDate;
	private final Double weight;

	public static AverageReading of(Date creationDate, Double weight) {
		return new AverageReading(creationDate, weight);
	}

	public AverageReading(Date creationDate, Double weight) {
		this.creationDate = creationDate;
		this.weight = weight;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Double getWeight() {
		return weight;
	}
}
