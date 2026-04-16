package ec.edu.uce.cifrados;

public class CifradoRailFence implements Cifrador {
    private int numRieles;

    // Constructor para definir la "clave" (cuántos rieles tendrá la valla)
    public CifradoRailFence(int numRieles) {
        this.numRieles = numRieles;
    }

    @Override
    public String cifrar(String texto) {
        // Casos base: si no hay texto o solo hay un riel, el texto no cambia
        if (numRieles <= 1 || texto.length() <= numRieles) {
            return texto;
        }

        // Crear un array de StringBuilders, uno para cada riel
        StringBuilder[] rieles = new StringBuilder[numRieles];
        for (int i = 0; i < numRieles; i++) {
            rieles[i] = new StringBuilder();
        }

        int rielActual = 0;
        boolean bajando = false;

        // Recorrer el texto y colocar cada letra en su riel correspondiente
        for (char c : texto.toCharArray()) {
            rieles[rielActual].append(c);

            // Lógica de "rebote": si llegamos arriba (0) o abajo (numRieles-1), cambiamos dirección
            if (rielActual == 0 || rielActual == numRieles - 1) {
                bajando = !bajando;
            }

            // Moverse al siguiente riel según la dirección
            rielActual += bajando ? 1 : -1;
        }

        // Unir todos los rieles para formar la cadena cifrada final
        StringBuilder resultado = new StringBuilder();
        for (StringBuilder riel : rieles) {
            resultado.append(riel);
        }

        return resultado.toString();
    }

    @Override
    public String descifrar(String texto) {
        if (numRieles <= 1 || texto.length() <= numRieles) return texto;

        int len = texto.length();
        // 1. Calculamos la longitud de cada riel simulando el recorrido
        int[] longitudRieles = new int[numRieles];
        int rielActual = 0;
        boolean bajando = false;

        for (int i = 0; i < len; i++) {
            longitudRieles[rielActual]++;
            if (rielActual == 0 || rielActual == numRieles - 1) bajando = !bajando;
            rielActual += bajando ? 1 : -1;
        }

        // 2. Extraemos los pedazos de texto para cada riel
        String[] textosRieles = new String[numRieles];
        int indiceTexto = 0;
        for (int i = 0; i < numRieles; i++) {
            textosRieles[i] = texto.substring(indiceTexto, indiceTexto + longitudRieles[i]);
            indiceTexto += longitudRieles[i];
        }

        // 3. Reconstruimos leyendo en zigzag
        StringBuilder resultado = new StringBuilder();
        int[] indicesLectura = new int[numRieles]; // Para saber por qué letra vamos en cada riel
        rielActual = 0;
        bajando = false;

        for (int i = 0; i < len; i++) {
            resultado.append(textosRieles[rielActual].charAt(indicesLectura[rielActual]++));
            if (rielActual == 0 || rielActual == numRieles - 1) bajando = !bajando;
            rielActual += bajando ? 1 : -1;
        }

        return resultado.toString();
    }
}
