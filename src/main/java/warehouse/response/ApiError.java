package warehouse.response;

public record ApiError(
        int status,
        String error,
        String message
) {
}
