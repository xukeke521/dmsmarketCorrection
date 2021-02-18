package com.trinamota.domain;

public class condition {
private String name;
	
    private String value;
	
    private String opt;
    
    private String type;
    
	public condition(String name, String value, String opt, String type) {
		super();
		this.name = name;
		this.value = value;
		this.opt = opt;
		this.type = type;
	}

	public condition(String name, String value, String opt) {
		super();
		this.name = name;
		this.value = value;
		this.opt = opt;
		this.type = "and";
	}
	
	public condition(String name, String value) {
		super();
		this.name = name;
		this.value = value;
		this.opt = "=";
		this.type = "and";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
