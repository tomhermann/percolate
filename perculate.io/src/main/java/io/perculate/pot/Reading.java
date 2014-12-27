package io.perculate.pot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class Reading {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String deviceId;
	@CreatedDate
	private DateTime timestamp;
	private Double weight;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Reading [id=" + id + ", deviceId=" + deviceId + ", timestamp=" + timestamp + ", weight=" + weight + "]";
	}
}
