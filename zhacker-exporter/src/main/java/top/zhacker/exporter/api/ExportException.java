package top.zhacker.exporter.api;

/**
 * DATE: 16/11/21 下午3:45 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
public class ExportException extends RuntimeException {

    private String message = "export exception";

    public ExportException() {
    }

    public ExportException(String message) {
        this.message = message;
    }


    public ExportException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public ExportException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}