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


        int cantidadClientes = leerArchivoClientes(listaNombresClientes,
                                                   listaApellidosClientes,
                                                   listaRutsClientes,
                                                   listaContraseñasClientes,
                                                   listaSaldosClientes);

        leerArchivoStatus(listaRutsClientes,
                          listaEstadoPaseMovilidadClientes,
                          cantidadClientes);

        int cantidadPeliculas = leerArchivoPeliculas(listaNombresPeliculas,
                listaTipoPeliculas,
                listaRecaudacionesPeliculas,
                matrizHorariosFuncionesSalas);
    }


    public static int[][] generarSalaCine() {
        int NUMERO_FILAS = 10;
        int NUMERO_COLUMNAS = 30;
        int[][] matrizSala = new int[NUMERO_FILAS][NUMERO_COLUMNAS];
        for (int f = 0; f < NUMERO_FILAS - 6; f++) {
            for (int c = 5; c < NUMERO_COLUMNAS - 5; c++) {
                matrizSala[f][c] = -1;
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

    public static void leerArchivoStatus(String[] listaRutsClientes,
                                         String[] listaEstadoPaseMovilidadClientes,
                                         int cantidadClientes) {

        File archivoStatus= new File("archivos/status.txt");
        try (Scanner scannerFile = new Scanner(archivoStatus)) {
            while (scannerFile.hasNext()) {
                String linea = scannerFile.nextLine();
                String[] partes = linea.split(",");
                String rut = partes[0];
                String status = partes[1];
                int posicionCliente = obtenerPosicionCliente(rut, listaRutsClientes, cantidadClientes);
                if (posicionCliente != -1) {
                    listaEstadoPaseMovilidadClientes[posicionCliente] = status;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static int leerArchivoPeliculas(String[] listaNombresPeliculas,
                                           String[] listaTipoPeliculas,
                                           int[] listaRecaudacionesPeliculas,
                                           int[][] matrizHorariosFuncionesSalas) {
        File archivoPeliculas = new File("archivos/peliculas.txt");
        int cantidadPeliculas = 0;
        try (Scanner scannerFile = new Scanner(archivoPeliculas)) {
            while (scannerFile.hasNext()) {
                String linea = scannerFile.nextLine();
                String[] partes = linea.split(",", 4);
                String nombre = partes[0];
                String tipo = partes[1];
                int recaudacionActual = Integer.parseInt(partes[2]);
                String[] partesFunciones = partes[3].split(",");

                listaNombresPeliculas[cantidadPeliculas] = nombre;
                listaTipoPeliculas[cantidadPeliculas] = tipo;
                listaRecaudacionesPeliculas[cantidadPeliculas] = recaudacionActual;

                for (int f = 0; f < partesFunciones.length; f+=2) {
                    int numeroSala = Integer.parseInt(partesFunciones[f]);
                    String horario = partesFunciones[f + 1];
                    if (matrizHorariosFuncionesSalas[cantidadPeliculas][numeroSala - 1] != 0) {
                        matrizHorariosFuncionesSalas[cantidadPeliculas][numeroSala - 1] = 2;
                    }
                    else if (horario.equalsIgnoreCase("M")) {
                        matrizHorariosFuncionesSalas[cantidadPeliculas][numeroSala - 1] = -1;
                    }
                    else if (horario.equalsIgnoreCase("T")) {
                        matrizHorariosFuncionesSalas[cantidadPeliculas][numeroSala - 1] = 1;
                    }
                }

                cantidadPeliculas++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cantidadPeliculas;
    }

    public static int obtenerPosicionCliente(String rut,
                                             String[] listaRutsClientes,
                                             int cantidadClientes) {
        int i;
        for (i = 0; (i < cantidadClientes) && !listaRutsClientes[i].equals(rut); i++);

        if (i < cantidadClientes && listaRutsClientes[i].equals(rut)) {
            return i;
        }

        return -1;
    }
}