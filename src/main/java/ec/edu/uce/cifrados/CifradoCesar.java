package ec.edu.uce.cifrados;

public class CifradoCesar implements Cifrador {
    private int llave;

    //Constructor inicial
    public CifradoCesar(int llave) {
        this.llave = llave;
    }

    @Override
    public String cifrar(String texto) {
        return procesar(texto, this.llave);
    }

    @Override
    public String descifrar(String texto) {
        // Para descifrar, nos movemos en reversa (26 - llave)
        return procesar(texto, 26 - this.llave);
    }

    // Metodo interno para no repetir el bucle for
    private String procesar(String texto, int desplazamiento) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            if (Character.isLetter(caracter)) {
                char base = Character.isUpperCase(caracter) ? 'A' : 'a';
                char nuevoCaracter = (char) (base + (caracter - base + desplazamiento) % 26);
                resultado.append(nuevoCaracter);
            } else {
                resultado.append(caracter);
            }
        }
        return resultado.toString();
    }

}
