package com.cfs.Weather_App.dto;

public class Condition {
	 public String text;
	    public String icon;
	    public int code;
		public Condition(String text, String icon, int code) {
			super();
			this.text = text;
			this.icon = icon;
			this.code = code;
		}
		public Condition() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
	    
}
