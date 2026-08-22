class Result<T> {
    private final boolean success;
    private final String message;
    private final T data;

    Result(boolean success, String message, T data) {
        this.success = success;
        this.message = (message == null || message.isBlank()) ? "" : message.trim();
        this.data = success ? data : null;
    }

    boolean isSuccess() {
        return success;
    }

    String getMessage() {
        return message;
    }

    T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{success=" + success + ", message='" + message + "', data=" + data + "}";
    }
}

public class GenericResultDemo {
    public static void main(String[] args) {
        Result<String> strSuccess = new Result<>(true, "Token generated", "AUTH_TOKEN_XYZ");
        Result<String> strFail = new Result<>(false, "User not found", "should_be_ignored");

        if (strSuccess.isSuccess()) {
            String token = strSuccess.getData();
            System.out.println("String 成功: " + token.toLowerCase());
        }

        System.out.println("String 失敗: message=" + strFail.getMessage() + ", data=" + strFail.getData());

        Result<Integer> intSuccess = new Result<>(true, "Score loaded", 95);
        Result<Integer> intFail = new Result<>(false, "Network error", null);

        if (intSuccess.isSuccess()) {
            int score = intSuccess.getData();
            System.out.println("Integer 成功: score + 5 = " + (score + 5));
        }

        System.out.println("Integer 失敗: message=" + intFail.getMessage() + ", data=" + intFail.getData());
    }
}