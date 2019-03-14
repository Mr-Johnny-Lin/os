package com.lin.os.data;

public class TestResult  {
    private String id;
    private String message;
    private long time;
    public TestResult(String id,String message ,long time){
        this.id=id;
        this.message=message;
        this.time=time/20;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time/20;
    }
}
