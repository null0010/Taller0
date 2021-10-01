import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Mauricio Alarcón
 *         Francisco Bordones
 */



public class Main {

    /***
     * Se encarga de iniciar el programa
     */
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
        int[][] matrizCineSala1 = generarSalaCine();
        int[][] matrizCineSala2 = generarSalaCine();
        int[][] matrizCineSala3 = generarSalaCine();
        int[][] matrizHorariosFuncionesSalas = new int[999][3];


        String[] listaNombresPeliculas = new String[999];
        String[] listaTipoPeliculas = new String[999];
        int[] listaRecaudacionesTotalPeliculas = new int[999];
        int[][] matrizRecaudacionesPeliculas = new int[999][2];


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
                listaRecaudacionesTotalPeliculas,
                matrizHorariosFuncionesSalas);


        iniciarSesion(input,
                listaNombresClientes,
                listaApellidosClientes,
                listaRutsClientes,
                listaContraseñasClientes,
                listaSaldosClientes,
                listaEstadoPaseMovilidadClientes,
                listaNombresPeliculasEntradasCompradas,
                listaHorariosEntradasCompradas,
                listaAsientosEntradasCompradas,
                listaFilasSalas,
                matrizCineSala1,
                matrizCineSala2,
                matrizCineSala3,
                matrizHorariosFuncionesSalas,
                listaNombresPeliculas,
                listaTipoPeliculas,
                listaRecaudacionesTotalPeliculas,
                matrizRecaudacionesPeliculas,
                cantidadClientes,
                cantidadPeliculas);
        cerrarSesion();
        input.close();
    }

    /***
     * Se encarga de generar una matriz entera que representa una sala de cine
     * @return Retorna una matriz de enteros que representara una sala del cine
     */
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


    /***
     * Se encarga de leer y cargar los datos del archivo de clientes en sus correspondientes arreglos.
     * @param listaNombresClientes Almacenara los nombre de los clientes
     * @param listaApellidosClientes Almacenara los apellidos de los clientes
     * @param listaContraseñasClientes Almacenara las contraseña de los clientes
     * @param listaRutsClientes Almacenara los ruts de los clientes
     * @param listaSaldosClientes Almacenara los saldo de los clientes
     * @return Retorna un entero que representara la cantidad de clientes
     */
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cantidadClientes;
    }


    /***
     * Se encarga de leer y cargar los datos del archivo status en sus correspondientes arreglos
     * @param listaRutsClientes se utilizara para buscar la posicion de cada uno de los clientes
     * @param listaEstadoPaseMovilidadClientes almacenara el estado del pase de movilidad de cada uno de los clientes
     * @param cantidadClientes se utilizara para buscar a un cliente determinado
     */
    public static void leerArchivoStatus(String[] listaRutsClientes,
                                         String[] listaEstadoPaseMovilidadClientes,
                                         int cantidadClientes) {
        File archivoStatus = new File("archivos/status.txt");
        try (Scanner scannerFile = new Scanner(archivoStatus)) {
            while (scannerFile.hasNext()) {
                String linea = scannerFile.nextLine();
                String[] partes = linea.split(",");
                String rut = partes[0];
                String status = partes[1];
                int posicionCliente = obtenerPosicionElementoEnListaString(rut,
                        listaRutsClientes,
                        cantidadClientes);
                if (posicionCliente != -1) {
                    listaEstadoPaseMovilidadClientes[posicionCliente] = status;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * Se encargara de leer y cargar los datos de todas las peliculas en sus correspondientes arreglos
     * @param listaNombresPeliculas Almacenara los nombre de las peliculas
     * @param listaTipoPeliculas Almacenara el tipo de las peliculas
     * @param listaRecaudacionesTotalPeliculas Almacenara la recaudacion total de las peliculas
     * @param matrizHorariosFuncionesSalas  Almacenara el horario de las funciones de las peliculas
     * @return Retorna un entero que representara la cantidad de peliculas
     */
    public static int leerArchivoPeliculas(String[] listaNombresPeliculas,
                                           String[] listaTipoPeliculas,
                                           int[] listaRecaudacionesTotalPeliculas,
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
                listaRecaudacionesTotalPeliculas[cantidadPeliculas] = recaudacionActual;

                for (int f = 0; f < partesFunciones.length; f += 2) {
                    int numeroSala = Integer.parseInt(partesFunciones[f]);
                    String horario = partesFunciones[f + 1];
                    if (matrizHorariosFuncionesSalas[cantidadPeliculas][numeroSala - 1] != 0) {
                        matrizHorariosFuncionesSalas[cantidadPeliculas][numeroSala - 1] = 2;
                    } else if (horario.equalsIgnoreCase("M")) {
                        matrizHorariosFuncionesSalas[cantidadPeliculas][numeroSala - 1] = -1;
                    } else if (horario.equalsIgnoreCase("T")) {
                        matrizHorariosFuncionesSalas[cantidadPeliculas][numeroSala - 1] = 1;
                    }
                }

                cantidadPeliculas++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cantidadPeliculas;
    }

    /***
     * Se encargara de buscar un elemento de tipo String en un lista del mismo tipo.
     * @param elemento Corresponde al elemento que se desea buscar
     * @param lista Corresponde a la lista donde se buscara el elemento
     * @param cantidadElementos Corresponde a la cantidad de elemento
     * @return Retornara un entero que indicara la posicion del elemento, en caso de no encontrarlo retornara un -1
     */
    public static int obtenerPosicionElementoEnListaString(String elemento,
                                                           String[] lista,
                                                           int cantidadElementos) {
        int i;
        for (i = 0; (i < cantidadElementos) && !lista[i].equals(elemento); i++) ;

        if (i < cantidadElementos && lista[i].equals(elemento)) {
            return i;
        }

        return -1;
    }


    /***
     * Se encargara de obtener el rut formateado
     * @param rut Corresponde al rut
     * @return Retornara un String con el rut formateado, en caso contrario retornada un String vacio
     */
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


    /**
     * Se encargara de iniciarSesion
     * @param input Se utilizara para entrada de datos
     * @param listaNombresClientes Corresponde a la lista de nombres de los clientes
     * @param listaApellidosClientes Corresponde a la lista de apellidos de los clientes
     * @param listaRutsClientes Corresponde a la lista de ruts de los clientes
     * @param listaContraseñasClientes Corresponde a la lista de contraseñas de los clientes
     * @param listaSaldosClientes Corresponde a la lista de saldos de los clientes
     * @param listaEstadoPaseMovilidadClientes Corresponde a la lista de estado de pases de movilidad de los clientes
     * @param listaNombresPeliculasEntradasCompradas  Corresponde a la lista de nombres de peliculas de entradas compradas
     * @param listaHorariosEntradasCompradas Corresponde a la lista de horarios de entradas compradas
     * @param listaAsientosEntradasCompradas Corresponde a la lista de asientos de entradas compradas
     * @param listaFilasSalas Corresponde a la lista de filas de las salas
     * @param matrizCineSala1 Corresponde a la matriz sala 1
     * @param matrizCineSala2 Corresponde a la matriz sala 2
     * @param matrizCineSala3 Corresponde a la matriz sala 3
     * @param matrizHorariosFuncionesSalas  Corresponde a la matriz que contiene los horarios de las funciones de cada sala
     * @param listaNombresPeliculas Corresponde a la lista que contiene los nombre de las peliculas
     * @param listaTipoPeliculas Corresponde a la lista que contiene el tipo de cada pelicula
     * @param listaRecaudacionesTotalPeliculas Corresponde a la lista que contiene la recaudacion total  de cada pelicula
     * @param matrizRecaudacionesPeliculas Corresponde a la matriz recaudaciones cada pelicula
     * @param cantidadClientes Corresponde a la cantidad de clientes
     * @param cantidadPeliculas Corresponde a la cantidad de peliculas
     */
    public static void iniciarSesion(Scanner input,
                                     String[] listaNombresClientes,
                                     String[] listaApellidosClientes,
                                     String[] listaRutsClientes,
                                     String[] listaContraseñasClientes,
                                     int[] listaSaldosClientes,
                                     String[] listaEstadoPaseMovilidadClientes,
                                     String[] listaNombresPeliculasEntradasCompradas,
                                     int[] listaHorariosEntradasCompradas,
                                     String[] listaAsientosEntradasCompradas,
                                     String[] listaFilasSalas,
                                     int[][] matrizCineSala1,
                                     int[][] matrizCineSala2,
                                     int[][] matrizCineSala3,
                                     int[][] matrizHorariosFuncionesSalas,
                                     String[] listaNombresPeliculas,
                                     String[] listaTipoPeliculas,
                                     int[] listaRecaudacionesTotalPeliculas,
                                     int[][] matrizRecaudacionesPeliculas,
                                     int cantidadClientes,
                                     int cantidadPeliculas) {
        boolean sesionActiva = true;
        while (sesionActiva) {
            System.out.print("Ingrese su RUT: ");
            String rut = input.next();
            input.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String contraseña = input.nextLine();
            if (rut.equals("ADMIN") && contraseña.equals("ADMIN")) {
                menuAdmin(input,
                          listaRecaudacionesTotalPeliculas,
                          matrizRecaudacionesPeliculas,
                          listaNombresClientes,
                          listaApellidosClientes,
                          listaSaldosClientes,
                          listaHorariosEntradasCompradas,
                          listaAsientosEntradasCompradas,
                          listaNombresPeliculas,
                          cantidadPeliculas,
                          cantidadClientes);
                sesionActiva = false;
            } else {
                String rutFormateado = obtenerRutFormateado(rut);
                boolean esRutValido = !rutFormateado.equals("");
                if (esRutValido) {
                    int posicionUsuario = obtenerPosicionElementoEnListaString(rutFormateado, listaRutsClientes,
                            cantidadClientes);
                    if (posicionUsuario != -1) {
                        int posicionContraseña = obtenerPosicionElementoEnListaString(contraseña,
                                listaContraseñasClientes, cantidadClientes);
                        if (posicionContraseña != -1) {
                            menuUsuario(input,
                                    rutFormateado,
                                    listaNombresClientes,
                                    listaApellidosClientes,
                                    listaRutsClientes,
                                    listaContraseñasClientes,
                                    listaSaldosClientes,
                                    listaEstadoPaseMovilidadClientes,
                                    listaNombresPeliculasEntradasCompradas,
                                    listaNombresPeliculas,
                                    listaTipoPeliculas,
                                    matrizHorariosFuncionesSalas,
                                    listaFilasSalas,
                                    matrizCineSala1,
                                    matrizCineSala2,
                                    matrizCineSala3,
                                    cantidadClientes,
                                    cantidadPeliculas);
                            sesionActiva = false;
                        } else {
                            System.out.println("Rut y/o contraseña invalidos.");
                        }
                    } else {
                        System.out.println("No te encuentras registrado en el sistema.");
                        System.out.print("¿Desea registrase? (Si/No): ");
                        String respuesta = input.next();
                        while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                            System.out.print("¿Desea registrase? (Si/No): ");
                            respuesta = input.next();
                        }

                        if (respuesta.equalsIgnoreCase("si")) {
                            registrarUsuario(input,
                                    listaNombresClientes,
                                    listaApellidosClientes,
                                    listaRutsClientes,
                                    listaContraseñasClientes,
                                    listaSaldosClientes,
                                    listaEstadoPaseMovilidadClientes,
                                    cantidadClientes);
                            cantidadClientes++;
                            System.out.println("Se ha registrado correctamente.");
                        } else {
                            System.out.print("¿Desea cerrar sesión? (Si/No): ");
                            respuesta = input.next();
                            while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                                System.out.print("\"¿Desea cerrar el sistema? (Si/No): ");
                                respuesta = input.next();
                            }

                            if (respuesta.equals("si")) {
                                sesionActiva = false;
                            }
                        }
                    }
                } else {
                    System.out.println("Rut y/o contraseña invalidos.");
                }
            }
        }
    }


    /**
     * Registra un usuario nuevo
     * @param input Almacena los datos leidos por pantalla
     * @param listaNombresClientes Corresponde a la lista de nombres de los clientes
     * @param listaApellidosClientes Corresponde a la lista de apellidos de los clientes
     * @param listaRutsClientes Corresponde a la lista de ruts de los clientes
     * @param listaContraseñasClientes Corresponde a la lista de contraseñas de los clientes
     * @param listaSaldosClientes Corresponde a la lista de saldos de los clientes
     * @param listaEstadoPaseMovilidadClientes Corresponde a la lista de estado de pases de movilidad de los clientes
     * @param cantidadClientes Almacena la cantidad de clientes que hay en el sistema*/
    public static void registrarUsuario(Scanner input,
                                        String[] listaNombresClientes,
                                        String[] listaApellidosClientes,
                                        String[] listaRutsClientes,
                                        String[] listaContraseñasClientes,
                                        int[] listaSaldosClientes,
                                        String[] listaEstadoPaseMovilidadClientes,
                                        int cantidadClientes) {
        System.out.print("Ingrese su Nombre: ");
        String nombre = input.next();
        System.out.print("Ingrese su Apellido: ");
        String apellido = input.next();
        System.out.print("Ingrese su Rut: ");
        String rutFormateado = obtenerRutFormateado(input.next());
        while (rutFormateado.equals("")) {
            System.out.print("Error de formato, ingrese su Rut nuevamente: ");
            rutFormateado = obtenerRutFormateado(input.next());
        }

        System.out.print("Ingrese su Contraseña: ");
        String contraseña = input.next();
        System.out.print("Ingrese su saldo: ");
        int saldo = input.nextInt();
        input.nextLine();
        System.out.print("Ingrese su estado de pase de movilidad: ");
        String estadoPaseMovilidad = input.nextLine();
        while (!estadoPaseMovilidad.equalsIgnoreCase("NO HABILITADO") && !estadoPaseMovilidad.equalsIgnoreCase(
                "HABILITADO")) {
            System.out.print("Error, ingrese su estado de pase de movilidad nuevamente (HABILITADO / NO HABILITADO): ");
            estadoPaseMovilidad = input.nextLine();
        }
        listaNombresClientes[cantidadClientes] = nombre;
        listaApellidosClientes[cantidadClientes] = apellido;
        listaRutsClientes[cantidadClientes] = rutFormateado;
        listaContraseñasClientes[cantidadClientes] = contraseña;
        listaSaldosClientes[cantidadClientes] = saldo;
        listaEstadoPaseMovilidadClientes[cantidadClientes] = estadoPaseMovilidad;
    }



    /** Se despliega el menu de usuario con sus opciones
     * @param input Almacena los datos ingresado por pantalla
     * @param rut Guarda el rut del usuario registrado
     * @param listaNombresClientes Corresponde a la lista de nombres de los clientes
     * @param listaApellidosClientes Corresponde a la lista de apellidos de los clientes
     * @param listaRutsClientes Corresponde a la lista de ruts de los clientes
     * @param listaContraseñasClientes Corresponde a la lista de contraseñas de los clientes
     * @param listaSaldosClientes Corresponde a la lista de saldos de los clientes
     * @param listaEstadoPaseMovilidadClientes Corresponde a la lista de estado de pases de movilidad de los clientes
     * @param listaNombresPeliculasEntradasCompradas
     * @param listaNombresPeliculas Corresponde a la lista que contiene los nombre de las peliculas
     * @param listaTipoPeliculas Corresponde a la lista que contiene el tipo de cada pelicula
     * @param matrizHorariosFuncionesSalas Corresponde a la matriz que contiene los horarios de las funciones de cada sala
     * @param listaFilasSalas Corresponde a la lista de filas de las salas
     * @param matrizCineSala1 Corresponde a la matriz sala 1
     * @param matrizCineSala2 Corresponde a la matriz sala 2
     * @param matrizCineSala3 Corresponde a la matriz sala 3
     * @param cantidadClientes Cantidad de clientes que se encuentran en el sistema
     * @param cantidadPeliculas Cantidad de peliculas que se encuentran en el sistema
     */
    public static void menuUsuario(Scanner input,
                                   String rut,
                                   String[] listaNombresClientes,
                                   String[] listaApellidosClientes,
                                   String[] listaRutsClientes,
                                   String[] listaContraseñasClientes,
                                   int[] listaSaldosClientes,
                                   String[] listaEstadoPaseMovilidadClientes,
                                   String[] listaNombresPeliculasEntradasCompradas,
                                   String[] listaNombresPeliculas,
                                   String[] listaTipoPeliculas,
                                   int[][] matrizHorariosFuncionesSalas,
                                   String[] listaFilasSalas,
                                   int[][] matrizCineSala1,
                                   int[][] matrizCineSala2,
                                   int[][] matrizCineSala3,
                                   int cantidadClientes,
                                   int cantidadPeliculas) {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println("Bienvenido al Menu Usuario");
            System.out.println("[1] Comprar entrada");
            System.out.println("[2] Informacion usuario");
            System.out.println("[3] Devolver entrada");
            System.out.println("[4] Cartelera");
            System.out.println("[5] Cerrar sesion");
            System.out.print("Ingrese una opcion: ");
            opcion = input.nextInt();
            while (opcion < 1 || opcion > 5) {
                System.out.print("Error, ingrese una opcion nuevamente: ");
                opcion = input.nextInt();
            }

            switch (opcion) {
                case 1:
                    comprarEntrada(input,
                                   rut,
                                   listaNombresPeliculas,
                                   listaTipoPeliculas,
                                   matrizHorariosFuncionesSalas,
                                   listaFilasSalas,
                                   matrizCineSala1,
                                   matrizCineSala2,
                                   matrizCineSala3,
                                   listaSaldosClientes,
                                   listaRutsClientes,
                                   cantidadClientes,
                                   cantidadPeliculas);
                    break;
                case 2:
                    desplegarInformacionUsuario(rut,
                                                listaNombresClientes,
                                                listaApellidosClientes,
                                                listaRutsClientes,
                                                listaSaldosClientes,
                                                cantidadClientes);
                    break;
                case 3:
                    //devolverEntrada();
                    break;
                case 4:
                    desplegarInformacionCartelera(listaNombresPeliculas,
                                                  matrizHorariosFuncionesSalas,
                                                  cantidadPeliculas);
                    break;
                case 5:
                    break;
            }
        }
    }


    /**
     * Despliega el menú para el admin
     * @param input
     * @param listaRecaudacionesTotalPeliculas
     * @param matrizRecaudacionesPeliculas
     * @param listaNombresClientes
     * @param listaApellidosClientes
     * @param listaSaldosClientes
     * @param listaHorariosEntradasCompradas
     * @param listaAsientosEntradasCompradas
     * @param listaNombresPeliculas
     * @param cantidadPeliculas
     * @param cantidadClientes
     */
    public static void menuAdmin(Scanner input,
                                 int[] listaRecaudacionesTotalPeliculas,
                                 int[][] matrizRecaudacionesPeliculas,
                                 String[] listaNombresClientes,
                                 String[] listaApellidosClientes,
                                 int[] listaSaldosClientes,
                                 int[] listaHorariosEntradasCompradas,
                                 String[] listaAsientosEntradasCompradas,
                                 String[] listaNombresPeliculas,
                                 int cantidadPeliculas,
                                 int cantidadClientes) {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println("Bienvenido al Menu Admin");
            System.out.println("[1] Información taquilla");
            System.out.println("[2] Informacion cliente");
            System.out.println("[3] Cerrar sistema");

            System.out.print("Ingrese una opcion: ");
            opcion = input.nextInt();
            while (opcion < 1 || opcion > 5) {
                System.out.print("Error, ingrese una opcion nuevamente: ");
                opcion = input.nextInt();
            }

            switch (opcion) {
                case 1:
                    desplegarInformacionTaquilla(listaRecaudacionesTotalPeliculas,
                                                 matrizRecaudacionesPeliculas,
                                                 listaNombresPeliculas,
                                                 cantidadPeliculas);
                    break;
                case 2:
                    desplegarInformacionCliente(listaNombresClientes,
                                                listaApellidosClientes,
                                                listaSaldosClientes,
                                                listaHorariosEntradasCompradas,
                                                listaAsientosEntradasCompradas,
                                                cantidadClientes);
                    break;
                case 3:
                    break;
            }
        }
    }

    public static void cerrarSesion() {


    }



    /** Despliega la informacion de un cliente
     * @param listaNombresClientes Corresponde a la lista de nombres de los clientes
     * @param listaApellidosClientes Corresponde a la lista de apellidos de los clientes
     * @param listaSaldosClientes Corresponde a la lista de saldos de los clientes
     * @param listaHorariosEntradasCompradas Corresponde a la lista de horarios de entradas compradas
     * @param listaAsientosEntradasCompradas Corresponde a la lista de asientos de entradas compradas
     * @param cantidadClientes Almacena la cantidad de clientes que hay en el sistema
     */
    public static void desplegarInformacionCliente(String[] listaNombresClientes,
                                                   String[] listaApellidosClientes,
                                                   int[] listaSaldosClientes,
                                                   int[] listaHorariosEntradasCompradas,
                                                   String[] listaAsientosEntradasCompradas,
                                                   int cantidadClientes) {
        System.out.print("Ingrese el rut del cliente: ");

        for (int c = 0; c < cantidadClientes; c++) {
            System.out.println(listaNombresClientes[c]);
            System.out.println(listaApellidosClientes[c]);
            System.out.println(listaSaldosClientes[c]);
        }
    }

    /**
     * Despliega la informacion de la taquilla, su recaudacion total y la recaudacion a lo largo del día.
     * @param listaRecaudacionesTotalPeliculas Lista que guarda la recaudacion de cada pelicula.
     * @param matrizRecaudacionesPeliculas Guarda las recaudaciones de las peliculas.
     * @param listaNombresPeliculas Guarda los nombres de cada pelicula.
     * @param cantidadPeliculas Guarda la cantidad e peliculas que se muestran.
     */
    public static void desplegarInformacionTaquilla(int[] listaRecaudacionesTotalPeliculas,
                                                    int[][] matrizRecaudacionesPeliculas,
                                                    String[] listaNombresPeliculas,
                                                    int cantidadPeliculas) {
        for (int p = 0; p < cantidadPeliculas; p++) {
            System.out.println(listaNombresPeliculas[p]);
            System.out.println("Recaudacion total: " + listaRecaudacionesTotalPeliculas[p]);
            int recaudacionDuranteElDia = 0;
            for (int c = 0; c < 2; c++) {
                recaudacionDuranteElDia += matrizRecaudacionesPeliculas[p][c];
            }
            System.out.println("Recaudacion a lo largo del dia: " + recaudacionDuranteElDia);
        }
    }
    /**
    * Despliega la por pantalla la informaciondel usuario
    * @param rut
    * @param listaNombresClientes
    * @param listaApellidosClientes
    * @param listaRutsClientes
    * @param listaSaldosClientes
    * @param cantidadClientes
    */
    public static void desplegarInformacionUsuario(String rut,
                                                   String[] listaNombresClientes,
                                                   String[] listaApellidosClientes,
                                                   String[] listaRutsClientes,
                                                   int[] listaSaldosClientes,
                                                   int cantidadClientes) {
        int posicionUsuario = obtenerPosicionElementoEnListaString(rut, listaRutsClientes, cantidadClientes);
        System.out.println(listaNombresClientes[posicionUsuario]);
        System.out.println(listaApellidosClientes[posicionUsuario]);
        System.out.println(listaRutsClientes[posicionUsuario]);
        System.out.println(listaSaldosClientes[posicionUsuario]);

    }


    /**
    * Despliega por pantalla el horario de cada pelicula
    * @param listaNombresPeliculas contiene el nombre de cada pelicula
    * @param matrizHorariosFuncionesSalas contiene el horario de cada funcion y sala
    * @param cantidadPeliculas Contiene la cantidad de peliculas para mostrar
    */
    public static void desplegarInformacionCartelera(String[] listaNombresPeliculas,
                                                     int[][] matrizHorariosFuncionesSalas,
                                                     int cantidadPeliculas) {
        for (int f = 0; f < cantidadPeliculas; f++) {
            System.out.println(listaNombresPeliculas[f]);
            for (int c = 0; c < 3; c++) {
                System.out.println("Sala" + (c + 1));
                if (matrizHorariosFuncionesSalas[f][c] == -1) {
                    System.out.println("Mañana");
                }
                else if (matrizHorariosFuncionesSalas[f][c] == 1) {
                    System.out.println("Tarde");
                }
                else if (matrizHorariosFuncionesSalas[f][c] == 2) {
                    System.out.println("Mañana");
                    System.out.println("Tarde");
                }
                else {
                    System.out.println("No tiene horarios disponibles");
                }
            }
        }
    }

    /**
     * Se encarga de realizar la compra de una entrada, manejando excepciones y llenando las matrices ingresadas en caso de que la compra haya sido exitosa.
     * @param input Se encarga de guarda los datos ingresados por pantalla
     * @param listaNombresPeliculas Arreglo que almacena los nombres de las peliculas.
     * @param listaTipoPeliculas Corresponde a la lista que contiene el tipo de cada pelicula
     * @param matrizHorariosFuncionesSalas Corresponde a la matriz que contiene los horarios de las funciones de cada sala
     * @param listaFilasSalas Corresponde a la lista de filas de las salas
     * @param matrizCineSala1 Corresponde a la matriz sala 1
     * @param matrizCineSala2 Corresponde a la matriz sala 2
     * @param matrizCineSala3 Corresponde a la matriz sala 3
     * @param listaSaldosClientes Corresponde a la lista de saldos de los clientes
     * @param listaRutsClientes Corresponde a la lista de ruts de los clientes
     * @param cantidadClientes cantidadClientes Corresponde a la cantidad de clientes
     * @param cantidadPeliculas cantidadPeliculas Corresponde a la cantidad de peliculas
    */
    public static void comprarEntrada(Scanner input,
                                      String rut,
                                      String[] listaNombresPeliculas,
                                      String[] listaTipoPeliculas,
                                      int[][] matrizHorariosFuncionesSalas,
                                      String[] listaFilasSalas,
                                      int[][] matrizCineSala1,
                                      int[][] matrizCineSala2,
                                      int[][] matrizCineSala3,
                                      int[] listaSaldosClientes,
                                      String[] listaRutsClientes,
                                      int cantidadClientes,
                                      int cantidadPeliculas) {
        input.nextLine();
        System.out.println();
        System.out.print("Ingrese el nombre de la pelicula: ");
        String nombrePelicula = input.nextLine();
        int posicionPelicula = obtenerPosicionElementoEnListaString(nombrePelicula,
                listaNombresPeliculas,
                cantidadPeliculas);
        if (posicionPelicula != -1) {
            desplegarHorariosDisponiblesPelicula(posicionPelicula,
                                                  matrizHorariosFuncionesSalas);

            System.out.print("Ingrese el numero de la sala: ");
            int numeroSala = input.nextInt() - 1;
            while (numeroSala < 0 || numeroSala > 2 || matrizHorariosFuncionesSalas[posicionPelicula][numeroSala] == 0) {
                if (numeroSala >= 0 && numeroSala <= 2) {
                    System.out.println("Esa sala no tiene funciones disponibles!");
                } else {
                    System.out.println("Esa sala no existe!");
                }

                System.out.print("Ingrese el numero de la sala nuevamente: ");
                numeroSala = input.nextInt() - 1;
            }


            System.out.print("Ingrese el numero del horario: ");
            int numeroHorario = input.nextInt();
            if (matrizHorariosFuncionesSalas[posicionPelicula][numeroSala] != 2) {
                while (matrizHorariosFuncionesSalas[posicionPelicula][numeroSala] != numeroHorario) {
                    System.out.print("Error, ingrese el numero del horario nuevamente: ");
                    numeroHorario = input.nextInt();
                }
            } else {
                while (numeroHorario != 1 && numeroHorario != -1) {
                    System.out.print("Error, ingrese el numero del horario nuevamente: ");
                    numeroHorario = input.nextInt();
                }
            }
        }
    }

    /**
     * Obtiene el total de la compra para el total de entradas que compra un cliente, retorna un entero con el valor total.
     * @param numeroEntradas Guarda el numero de entradas que el cliente va a comprar.
     * @param tipoPelicula Guarda el tipo de pelicula a comprar para así dar un valor distinto dependiendo el tipo.
    */
    public static int obtenerTotalCompra(int numeroEntradas, String tipoPelicula) {
        int total = 0;
        if (tipoPelicula.equalsIgnoreCase("estreno")) {
            total = 5500 * numeroEntradas;
        }
        else {
            total = 4000 * numeroEntradas;
        }

        return total;
    }


    /**
     * Muestra la sala de cine y sus respectivos asientos disponibles..
     * @param matrizCineSala corresponde a la matriz de cada sala ingresada.
     * @param listaFilasSalas Corresponde a la listas de las filas de cada sala.
    */
    public static void desplegarAsientosFuncionPeliculas(int[][] matrizCineSala,
                                                         String[] listaFilasSalas) {
        int NUMERO_FILAS = 10;
        int NUMERO_COLUMNAS = 30;
        System.out.println("(D)=Desocupado / (O)=Ocupados");
        System.out.print("   ");
        for (int c = 0; c < NUMERO_COLUMNAS; c++) {
            System.out.print((c + 1) + "   ");
        }

        System.out.println();
        for (int f = 0; f < NUMERO_FILAS - 6; f++) {
            System.out.print(listaFilasSalas[f] + "                     ");
            for (int c = 5; c < NUMERO_COLUMNAS - 5; c++) {
                if (matrizCineSala[f][c] == -1) {
                    System.out.print("(D) ");
                }
                else {
                    System.out.print("(O) ");
                }

                if (c >= 8) {
                    System.out.print(" ");
                }

            }
            System.out.println();
        }

        for (int f = 4; f < NUMERO_FILAS; f++) {
            System.out.print(listaFilasSalas[f] + " ");
            for (int c = 0; c < NUMERO_COLUMNAS; c++) {
                if (matrizCineSala[f][c] == -1) {
                    System.out.print("(D) ");
                }
                else {
                    System.out.print("(O) ");
                }

                if (c >= 8) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }


    /***
     * Se encargara de desplegar los horarios disponibles de una pelicula
     * @param posicionPelicula Corresponde a la posicion de la pelicula
     * @param matrizHorariosFuncionesSalas Corresponde a la matriz que alman
     *
     */
    public static void desplegarHorariosDisponiblesPelicula(int posicionPelicula,
                                                            int[][] matrizHorariosFuncionesSalas) {
        for (int c = 0; c < 3; c++) {
            System.out.println("Sala " + (c + 1));
            int horario = matrizHorariosFuncionesSalas[posicionPelicula][c];
            System.out.println("Horarios:");
            if (horario == 1) {
                System.out.println("[1] Tarde");
            }

            else if (horario == -1) {
                System.out.println("[-1] Mañana");
            }

            else if (horario == 2) {
                System.out.println("[-1] Mañana");
                System.out.println("[1] Tarde");
            }
            else {
                System.out.println("No se han encontrado funciones disponibles.");
            }
            System.out.println();
        }
    }
}