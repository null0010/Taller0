import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] listaNombresClientes = new String[999];
        String[] listaApellidosClientes = new String[999];
        String[] listaRutsClientes = new String[999];
        String[] listaContraseñasClientes = new String[999];
        int[] listaSaldosClientes = new int[999];
        String[] listaEstadoPaseMovilidadClientes = new String[999];

        String[] listaNombresPeliculasEntradasCompradas = new String[999];
        int[] listaHorariosEntradasCompradas = new int[999];
        String[] listaAsientosEntradasCompradas = new String[999];

        String[] listaFilasSalas = {"A",
                                    "B",
                                    "C",
                                    "D",
                                    "E",
                                    "F",
                                    "G",
                                    "H",
                                    "I",
                                    "J"};
        int[][] matrizSalaCine1 = generarSalaCine();
        int[][] matrizSalaCine2 = generarSalaCine();
        int[][] matrizSalaCine3 = generarSalaCine();
        int[][] matrizHorariosFuncionesSalas = new int[999][3];

        String[] listaNombresPeliculas = new String[999];
        String[] listaTipoPeliculas = new String[999];
        int[] listaRecaudacionesPeliculas = new int[999];
        int[] listaRecaudacionesMañanaPeliculas = new int[999];
        int[] listaRecaudacionesTardePeliculas = new int[999];
        int[] listaRecaudacionesDuranteDiaPeliculas = new int[999];
        int c = 1000;
    }


    public static int[][] generarSalaCine() {
        int NUMERO_FILAS = 10;
        int NUMERO_COLUMNAS = 30;
        int[][] matrizSala = new int[NUMERO_FILAS][NUMERO_COLUMNAS];
        for (int f = 0; f < NUMERO_FILAS - 6; f++) {
            for (int c = 5; c < NUMERO_COLUMNAS - 5; c++) {
                matrizSala[f][c] = -1;
            System.out.println("esto esta creeado en la rama aaaaaa");
            
            }
        }

        for (int f = 4; f < NUMERO_FILAS; f++) {
            for (int c = 0; c < NUMERO_COLUMNAS; c++) {
                matrizSala[f][c] = -1;
            }
        }

        return matrizSala;
    }


    public static int leerArchivoClientes(String[] listaNombresClientes,
                                          String[] listaApellidosClientes,
                                          String[] listaRutsClientes,
                                          String[] listaContraseñasClientes,
                                          int[] listaSaldosClientes) {

        File archivoCliente = new File("archivos/clientes.txt");
        int cantidadClientes = 0;
        try (Scanner scannerFile = new Scanner(archivoCliente)) {
            while (scannerFile.hasNext()) {
                String linea = scannerFile.next();
                String[] partes = linea.split(",");
                String nombre = partes[0];
                String apellido = partes[1];
                String rut = partes[2];
                String contraseña = partes[3];
                int saldo = Integer.parseInt(partes[4]);

                listaNombresClientes[cantidadClientes] = nombre;
                listaApellidosClientes[cantidadClientes] = apellido;
                listaRutsClientes[cantidadClientes] = rut;
                listaContraseñasClientes[cantidadClientes] = contraseña;
                listaSaldosClientes[cantidadClientes] = saldo;
                cantidadClientes++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cantidadClientes;
    }
    
}