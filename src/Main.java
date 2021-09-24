public class Main {
    public static void main(String[] args) {
        String[] listaNombresClientes = new String[999];
        String[] listaApellidosClientes = new String[999];
        String[] listaRutsClientes = new String[999];
        String[] listaContraseasClientes = new String[999];
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
       
    }


    public static int[][] generarSalaCine() {
        int NUMERO_FILAS = 10;
        int NUMERO_COLUMNAS = 30;
        int[][] matrizSala = new int[NUMERO_FILAS][NUMERO_COLUMNAS];
        for (int f = 0; f < NUMERO_FILAS - 6; f++) {
            for (int c = 5; c < NUMERO_COLUMNAS - 5; c++) {
                matrizSala[f][c] = -1;
            System.out.println("esto esta creeado en una rama");
            
            }
        }

        for (int f = 4; f < NUMERO_FILAS; f++) {
            for (int c = 0; c < NUMERO_COLUMNAS; c++) {
                matrizSala[f][c] = -1;
            }
        }

        return matrizSala;
    }
}