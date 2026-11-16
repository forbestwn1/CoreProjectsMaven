package com.nosliw.core.application.entity.datacollection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ApplicationHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String app;

    private String parm;
    
	private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getParm() {
		return parm;
	}

	public void setParm(String parm) {
		this.parm = parm;
	}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}