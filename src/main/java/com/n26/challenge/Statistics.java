package com.n26.challenge;

public class Statistics {

	private Double sum;
	private Double avg;
	private Double max;
	private Double min;
	private Double count;
	
	public Double getSum() {
		
		return sum;
	}
	
	public void setSum(Double sum) {
		
		this.sum = sum;
	}
	
	public Double getAvg() {
		
		return avg;
	}
	
	public void setAvg(Double avg) {
		
		this.avg = avg;
	}
	
	public Double getMax() {
		
		return max;
	}
	
	public void setMax(Double max) {
		
		this.max = max;
	}
	
	public Double getMin() {
		
		return min;
	}
	
	public void setMin(Double min) {
		
		this.min = min;
	}
	
	public Double getCount() {
		
		return count;
	}
	
	public void setCount(Double count) {
		
		this.count = count;
	}

	@Override
	public String toString() {
		
		return "Statistics [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count + "]";
	}

}
