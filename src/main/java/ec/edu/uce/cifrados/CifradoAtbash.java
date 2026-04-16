package ec.edu.uce.cifrados;

public class CifradoAtbash implements Cifrador{
    @Override
    public String cifrar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for(char c : texto.toCharArray()){
            if(Character.isLetter(c)){
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                resultado.append((char) ((base == 'A'?'Z':'z')-(c-base)));
            }else {
                resultado.append(c);
            }
        }
        return resultado.toString();

    }

    @Override
    public String descifrar(String texto) {
        // En Atbash, descifrar es aplicar el mismo algoritmo
        return cifrar(texto);
    }
    //No se necesita llave

}
