package servlet;

public class State {
    boolean success;
    String message;

    public State(boolean success) {
        this.success = success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }
}
