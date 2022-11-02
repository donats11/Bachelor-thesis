package loginApp.utils;

public class BaseException extends Throwable {
    public String message;
    public int code;

    public BaseException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
