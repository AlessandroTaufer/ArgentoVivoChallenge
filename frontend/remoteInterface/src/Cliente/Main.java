package Cliente;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

public class Main {
    public static void main(String args[]){
        Client cl = new Client("192.168.43.153",1243);
        ArrayList<Immagine> immagini = cl.richiediSchermata(1, "");
        ArrayList<Immagine> immagini2= cl.richiediSchermata(2, "Fiumes");
        ArrayList<Immagine> immagini3 = cl.richiediSchermata(3, "Mayr Franz");
        int a=0;
        for(Immagine im : immagini){
           salva(im.getBase64(),a);
           a++;
        }
        for(Immagine im : immagini2){
           salva(im.getBase64(),a);
           a++;
        }
        for(Immagine im : immagini3){
           salva(im.getBase64(),a);
           a++;
        }
    }
    public static void salva(String base64, int path){
        byte[] b = DatatypeConverter.parseBase64Binary(base64);
        ByteArrayInputStream s = new ByteArrayInputStream(b);
        BufferedImage image;
        try {
            image = ImageIO.read(s);
            ImageIO.write(image, "jpg", new File("C:\\Users\\Hp\\Desktop\\lmao\\"+path+".jpg")); 
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
