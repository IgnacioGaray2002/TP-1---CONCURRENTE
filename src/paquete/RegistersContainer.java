package paquete;

public class RegistersContainer {
    private final OrderRegister inPreparation = new OrderRegister();
    private final OrderRegister inTransit = new OrderRegister();
    private final OrderRegister delivered = new OrderRegister();
    private final OrderRegister failed = new OrderRegister();
    private final OrderRegister verified = new OrderRegister();
 public RegistersContainer(){}
    public OrderRegister getRegister(OrderState state) {
        return switch (state) {
            case ORDER_IN_PREPARATION -> inPreparation;
            case ORDER_IN_TRANSIT -> inTransit;
            case ORDER_DELIVERED -> delivered;
            case FAILED_ORDER -> failed;
            case VERIFIED_ORDER -> verified;
        };
    }

    public OrderRegister getInPreparation() { return inPreparation; }
    public OrderRegister getInTransit() { return inTransit; }
    public OrderRegister getDelivered() { return delivered; }
    public OrderRegister getFailed() { return failed; }
    public OrderRegister getVerified() { return verified; }
}