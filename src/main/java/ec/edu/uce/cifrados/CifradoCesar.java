package ec.edu.uce.cifrados;

public class CifradoCesar implements Cifrador {
    private int llave;

    //Constructor inicial
    public CifradoCesar(int llave) {
        this.llave = llave;
    }

    @Override
    public String cifrar(String texto) {
        StringBuilder resultado = new StringBuilder();

        for(int i =0; i< texto.length();i++){
            char caracter = texto.charAt(i);
            if(Character.isLetter(caracter)){
                char base = Character.isUpperCase(caracter) ? 'A' : 'a';
                // Fórmula: (posición original + desplazamiento) % 26
                char nuevoCaracter = (char) (base + (caracter - base + llave) % 26);
                resultado.append(nuevoCaracter);
            }else {
                //Si no es letra(espacios, numeros) se queda igual
                resultado.append(caracter);
            }
        }
        return resultado.toString();
    }
}
