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
}
