package util.returnMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {
    private String message;
    private Object data;

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}