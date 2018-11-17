package Cliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client{
    private Socket socket;
    
    public Client(String ip, int port){
        try{
            socket = new Socket(ip,port);        
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
//    public void StartClient(){
//        try {
//            BufferedReader stdIn =new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            while(true){
//                System.out.println("Trying to read...");
//                String in = stdIn.readLine();
//                System.out.println(in);                            
//                out.print("Try"+"\r\n");
//                out.flush();
//                System.out.println("Message sent");
//            }
//        } catch (IOException e) {            
//            e.printStackTrace();
//        }
//    }
    
    public Immagine initImage(String str){       
        String []  elements = str.split(",");        
        //NomeAutore, idAutore, data (dd/mm/yy), descrizione, nomeluogo, coordinate(x/y), idluogo, similarita', ratio, idImgRiferimento, NomeImgRiferimento, Tag[tag1/tag2], b64        
        
        Luogo l = new Luogo(elements[6],elements[4],elements[5]);
        Autore a = new Autore(elements[1],elements[0],null,null);
        Immagine i = new Immagine(null,elements[9],null,null,null,null,elements[10],null,null,null); //Immagine riferimento
        Immagine img = new Immagine(elements[12],null,l,elements[2],a,i,elements[3],elements[11],elements[7],elements[8]);          
        //System.out.println("Ho ricevuto"+img.toString());           
        return img;
    }
    public String getImageMetaData(String id){
        String metaData="";
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);                                        
            out.print("img "+id+"\r\n");
            
            out.flush();        
            //Lettura metadata
            metaData = stdIn.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return metaData;
    }
    public Immagine riceviImmagine(String id){
        Immagine img=null;
//        try {
                String metaData = getImageMetaData(id);            
                img = initImage(metaData);

//                Immagine tmpImage= img.getImmagineRiferimento();
//                String tmpMetaData= getImageMetaData(tmpImage.getId());            
//                tmpImage = initImage(tmpMetaData);
//                img.setImmagineRiferimento(tmpImage);
            
//            stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            //Lettura codice b64 dell'immagine
//            String inImg = stdIn.readLine();
//            System.out.println(inImg);
//            inImg= inImg.replace("\n", "");
//            img = riceviMetaDati(inMetaData);            
//            byte[] b = DatatypeConverter.parseBase64Binary(inImg);  
//            
//            ByteArrayInputStream s = new ByteArrayInputStream(b);
//            BufferedImage image = ImageIO.read(s);
//            img.setImmagine(inImg);
            
//            ImageIO.write(image, "jpg", new File("C:\\Users\\Hp\\Desktop\\testkep.jpg"));  
//            System.out.println(img.toString());
//        } catch (IOException e) {            
//            System.out.println(e.getMessage());
//        }
        return img;
    }
    public void aggiornaContatoreVoti(int value, Immagine img ){
        try{            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);                         
            out.print("update "+img.getId()+ " " + value +"\r\n");            
            out.flush();            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<Immagine> richiediSchermata(int idSchermata, String param ){ //Code 1 Feed(Global), Code 2 Current Location, 3: Followed mando nome autore
        ArrayList<Immagine> immagini = new ArrayList();
        
        try{            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);             
            out.print("view "+idSchermata+" "+param+"\r\n");             
            out.flush(); 
            String plainIDs = stdIn.readLine();
            String [] arIDs=  plainIDs.split(",");
            for (int i=0;i<arIDs.length;i++) {      
                immagini.add(riceviImmagine(arIDs[i]));
            }                    
        }catch(IOException e){
            e.printStackTrace();
        }
        return immagini; 
    }
            
    
//    public BufferedImage getImageFromBase64(String base64){  
//        BufferedImage bi = null;
//        try {
//            byte [] b = DatatypeConverter.parseBase64Binary(base64);  
//            ByteArrayInputStream s = new ByteArrayInputStream(b);
//            bi = ImageIO.read(s);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return bi;
//    }
//    public String getBase64FromImage(Immagine img){
//        string encoed= null;
//    }
            
    public void inviaImmagine(Immagine immagine){
        try{            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);                         
            out.print("elon "+immagine.parseImge()+"\r\n");            
            out.flush();            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
//    public void tmpImmagine(){
//        try{
//            BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
//            
//            File file = new File("C:\\Users\\Hp\\Desktop\\test.jpg");
//            FileInputStream fileInputStreamReader = new FileInputStream(file);
//            byte[] bytes = new byte[(int)file.length()];
//            out.print("elon "+bytes.length+"\r\n");
//            out.flush();
//            String in = stdIn.readLine();
//            fileInputStreamReader.read(bytes);
//            String encodstring = Base64.getEncoder().encodeToString(bytes);
//            
//            System.out.println(encodstring);            
//            out.print(encodstring+"\r\n");            
//            out.flush();
//            
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }   
}
