package paquete;

public class FlowControl {
    private int totalPedidos;
    private int pedidosVerificados=0;
    private int pedidosPreparados=0;
    private int pedidosEntregados=0;
    private int pedidosDespachados=0;
   private boolean finGeneracion=false;


   public FlowControl(int totalPedidos) {
       this.totalPedidos = totalPedidos;
   }
   public synchronized void marcarPedidosVerificados() {
      pedidosVerificados++;
   }
   public synchronized void marcarPedidosPreparados() {pedidosPreparados++;}
    public synchronized void marcarPedidosEntregados() {pedidosEntregados++;}
    public synchronized void marcarPedidosDespachados() {pedidosDespachados++;}
    public synchronized void marcarFinGeneracion() {finGeneracion=true;}
    public synchronized boolean estadoCompleto() {
       return finGeneracion && pedidosPreparados >= totalPedidos && pedidosDespachados >= totalPedidos && pedidosEntregados >= totalPedidos;
    }
    public synchronized int getPedidosPreparados() {return pedidosPreparados;}
    public synchronized int  getPedidosVerificadoss() {return pedidosVerificados;}
    public synchronized int getPedidosEntregados() {return pedidosEntregados;}
    public synchronized int getPedidosDespachados() {return pedidosDespachados;}
}
