package com.siddhiApi.entity;

import java.util.Objects;

public class Pattern {
	private String patternName, outputStreamName, patternCode;

	public String[] getInputStreamNames() {
		return inputStreamNames;
	}

	public void setInputStreamNames(String[] inputStreamNames) {
		this.inputStreamNames = inputStreamNames;
	}

	//private List<String> inputStreamNames;
	private String[] inputStreamNames;

	public Pattern(String patternName, /*List<String>*/ String[] inputStreamNames, String outputStreamName, String patternCode) {
		this.patternName = patternName;
		this.inputStreamNames = inputStreamNames;
		this.outputStreamName = outputStreamName;
		this.patternCode = patternCode;
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public String getOutputStreamName() {
		return outputStreamName;
	}

	public void setOutputStreamName(String outputStreamName) {
		this.outputStreamName = outputStreamName;
	}

	public String getPatternCode() {
		return patternCode;
	}

	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pattern pattern = (Pattern) o;
		return patternName.equals(pattern.patternName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(patternName);
	}

	/*public List<String> getInputStreamNames() {
		return inputStreamNames;
	}

	public void setInputStreamNames(List<String> inputStreamNames) {
		this.inputStreamNames = inputStreamNames;
	}*/
}
