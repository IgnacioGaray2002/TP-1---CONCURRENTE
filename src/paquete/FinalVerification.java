package paquete;

public class FinalVerification implements Runnable {
    private final RegistersContainer registers;
private final FlowControl control;

    public FinalVerification(RegistersContainer registers,FlowControl control) {
        this.registers = registers;
  this.control=control;

    }

    @Override
    public void run() {

        while (true) {
            Order order;
            synchronized (registers.getDelivered()){
                while((order=registers.getDelivered().getRandomOrderAndRemove()) == null) {
                    try {
                        registers.getDelivered().wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        continue;
                    }
                }
            }

                boolean verified = Math.random() < 0.95;
            if (verified) {
                order.setState(OrderState.VERIFIED_ORDER);
                registers.getVerified().addOrder(order);
                control.marcarPedidosVerificados();
                Logger.logRegister("VERIFICADO", order, control.getPedidosVerificadoss());

            } else {
                order.setState(OrderState.FAILED_ORDER);
                registers.getFailed().addOrder(order);
                Logger.logRegister("FALLIDO", order,registers.getFailed().size());

            }



            try {
                Thread.sleep((long) (80 + Math.random() * 100));
            } catch (InterruptedException e) {Thread.currentThread().interrupt(); continue;}

        }

    }

}
