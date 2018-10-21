package com.project.word.data;
import java.util.ArrayList;

public class Data {
	String label;
	ArrayList<String> backgroundColor;
	ArrayList<String> borderColor;
	String borderWidth;
	ArrayList<String> data;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public ArrayList<String> getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(ArrayList<String> backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public ArrayList<String> getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(ArrayList<String> borderColor) {
		this.borderColor = borderColor;
	}
	public String getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(String borderWidth) {
		this.borderWidth = borderWidth;
	}
	public ArrayList<String> getData() {
		return data;
	}
	public void setData(ArrayList<String> data) {
		this.data = data;
	}
	}