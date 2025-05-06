package paquete;

public class CustomerDelivery implements Runnable {
    private final RegistersContainer registers;
private final FlowControl control;

    public CustomerDelivery(RegistersContainer registers,FlowControl control) {
        this.registers = registers;
        this.control=control;

    }

    @Override
    public void run() {
        while (true) {
            Order order;
            synchronized (registers.getInTransit()){
                while((order=registers.getInTransit().getRandomOrderAndRemove()) == null) {
                    try {
                        registers.getInTransit().wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        continue;
                    }
                }
            }
            try {
                Thread.sleep((long) (80 + Math.random() * 120));
            } catch (InterruptedException e){Thread.currentThread().interrupt(); continue;}
            boolean delivered = Math.random() < 0.90;

            if (delivered) {
                order.setState(OrderState.ORDER_DELIVERED);

                synchronized (registers.getDelivered()){
                    registers.getDelivered().addOrder(order);
                    registers.getDelivered().notifyAll();
                    control.marcarPedidosEntregados();
                }
                Logger.logRegister("ENTREGADO", order,control.getPedidosEntregados());
            } else {
                order.setState(OrderState.FAILED_ORDER);
                registers.getFailed().addOrder(order);
                Logger.logRegister("FALLIDOS", order,registers.getFailed().size());
            }

        }

    }


}
