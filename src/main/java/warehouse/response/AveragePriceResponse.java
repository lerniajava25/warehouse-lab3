package warehouse.response;

public record AveragePriceResponse(
        String category,
        double averagePrice
) {
}
