package paquete;

public class LockerGrid {
    private final int rows = 10;
    private final int cols = 20;
    private final Locker[][] lockers = new Locker[rows][cols];
    private int FailedCounter = 0;
    public LockerGrid() {//Genera los casillas 
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                lockers[i][j] = new Locker(i,j,null);

            }
        }
    }

    public synchronized Locker getAvailableLocker() {//recorre la matriz encuentra vacia y la marca ocupada
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (lockers[i][j].isAvailable()) {
                    lockers[i][j].occupy();
                    return lockers[i][j];
                }
            }
        }
        return null; // No hay casilleros disponibles
    }

    public Locker[][] getAllLockers() {//Obtiene las casillas ------------00000000000000000------------------------Metodo que no se usa
        return lockers;
    }
    public void outService(){//Aumenta en contador de casillas en fuera de servicio
        FailedCounter++;
    }
    public int getFailed()//Devuelve la cantidad de casillas averiadas
    {return FailedCounter; }
    public int getRows(){return rows;}
    public int getCols(){return cols;}
    public Locker getLocker(int i, int j){return lockers[i][j];}
}