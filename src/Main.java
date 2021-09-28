import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] listaNombresClientes = new String[999];
        String[] listaApellidosClientes = new String[999];
        String[] listaRutsClientes = new String[999];
        String[] listaContraseñasClientes = new String[999];
        int[] listaSaldosClientes = new int[999];
        String[] listaEstadoPaseMovilidadClientes = new String[999];

        String[] listaNombresPeliculasEntradasCompradas = new String[999];
        int[] listaHorariosEntradasCompradas = new int[999];
        String[] listaAsientosEntradasCompradas = new String[999];

        String[] listaFilasSalas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
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

        iniciarSesion(input);
        cerrarSesion();
        input.close();
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


    public static String obtenerRutFormateado(String rut) {
        Pattern PATRON_RUT = Pattern.compile("([0-9]{1,2})\\.?([0-9]{3})\\.?([0-9]{3})-?([0-9]|k)",
                Pattern.CASE_INSENSITIVE);
        Matcher rutMatcher = PATRON_RUT.matcher(rut);
        String rutFormateado = "";
        if (rutMatcher.matches()) {
            rutFormateado = rutMatcher.group(1);
            rutFormateado += "." + rutMatcher.group(2);
            rutFormateado += "." + rutMatcher.group(3);
            rutFormateado += "-" + rutMatcher.group(4);
        }

        return rutFormateado;
    }

    public static void iniciarSesion(Scanner input) {
        boolean sesionActiva = true;
        while (sesionActiva) {
            System.out.print("Ingrese su RUT: ");
            String rut = input.next();
            input.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String contraseña = input.nextLine();
            if (rut.equals("ADMIN") && contraseña.equals("ADMIN")) {
                //menuAdmin();
                sesionActiva = false;
            }
            else {
                String rutFormateado = obtenerRutFormateado(rut);
                boolean esRutValido = !rutFormateado.equals("");
                if (esRutValido) {
                    //menuUsuario();
                    sesionActiva = false;
                    System.out.println(rutFormateado);
                }
                else {
                    System.out.println("Rut y/o contraseña incorrectos.");
                }
            }

            if (sesionActiva) {
                System.out.println("[1] Iniciar sesión nuevamente.");
                System.out.println("[2] Cerrar sistema.");
                System.out.print("Ingrese una opcion: ");
                int opcion = input.nextInt();
                while (opcion < 1 || opcion > 2) {
                    System.out.print("Error, ingrese una opcion nuevamente: ");
                    opcion = input.nextInt();
                }

                if (opcion == 2) {
                    sesionActiva = false;
                }
            }
        }
    }

    public static void menuUsuario(Scanner input) {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println("Bienvenido al Menu Usuario");
            System.out.println("[1] Comprar entrada");
            System.out.println("[2] Informacion usuario");
            System.out.println("[3] Devolver entrada");
            System.out.println("[4] Cartelera");
            System.out.println("[5] Cerrar Sesion");
            System.out.print("Ingrese una opcion: ");
            opcion = input.nextInt();
            while (opcion < 1 || opcion > 5) {
                System.out.print("Error, Ingrese una opcion nuevamente: ");
                opcion = input.nextInt();
            }

            switch (opcion) {
                case 1:
                    //comprarEntrada();
                    break;
                case 2:
                    //desplegarInformacionUsuario();
                    break;
                case 3:
                    //devolverEntrada();
                    break;
                case 4:
                    //desplegarInformacionCartelera();
                    break;
                case 5:
                    break;
            }
        }
    }


    public static void menuAdmin(Scanner input) {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println("Bienvenido al Menu Admin");
            System.out.println("[1] Información taquilla");
            System.out.println("[2] Informacion cliente");
            System.out.println("[3] Cerrar Sesion");

            System.out.print("Ingrese una opcion: ");
            opcion = input.nextInt();
            while (opcion < 1 || opcion > 5) {
                System.out.print("Error, Ingrese una opcion nuevamente: ");
                opcion = input.nextInt();
            }
        }
    }

    public static void cerrarSesion() {


    }
}