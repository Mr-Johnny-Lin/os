package com.lin.os.data;

public class TestData {
    private String id;
    private String role;
    private long starttime;
    private long runningtime;

    public TestData(String id, String role, long starttime, long runningtime) {
        this.id = id;
        this.role = role;
        this.starttime = starttime*20;
        this.runningtime = runningtime*20;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime*20;
    }

    public long getRunningtime() {
        return runningtime;
    }

    public void setRunningtime(long runningtime) {
        this.runningtime = runningtime*20;
    }
}
