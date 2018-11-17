package Cliente;

class Immagine {
    private String id;
    private String b64Image;
    private Luogo luogo;
    private String data;
    private Autore autore;
    private Immagine immagineRiferimento;
    private String descrizione;
    private String [] tags;
    private double similarita;  //Similirita con fotoRiferimento
    private int ratio; //Like-Dislike
    
    public Immagine(String b64Image,String id, Luogo luogo, String data, Autore autore, Immagine fotoRiferimento, String descrizione, String tags, String similarita, String ratio) {
        this.id = id;
        this.b64Image= b64Image;                
        this.luogo = luogo;
        if(data!=null){
            this.data=data;
        }
        this.autore = autore;
        this.immagineRiferimento = fotoRiferimento;
        this.descrizione = descrizione;
        if(tags!=null){
            String [] strTags= tags.split(",");
            this.tags = strTags;
        }      
        if(similarita!=null){
            this.similarita = Double.parseDouble(similarita);
        }
        if(ratio!=null){
            this.ratio = Integer.parseInt(ratio);
        }        
    }

    public String getBase64() {
        return b64Image;
    }

    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
    
    public void setBase64(String b64Image) {
        this.b64Image = b64Image;
    }

    public Luogo getLuogo() {
        return luogo;
    }

    public void setLuogo(Luogo luogo) {
        this.luogo = luogo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Autore getAutore() {
        return autore;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }

    public Immagine getImmagineRiferimento() {
        return immagineRiferimento;
    }

    public void setImmagineRiferimento(Immagine immagineRiferimento) {
        this.immagineRiferimento = immagineRiferimento;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public double getSimilarita() {
        return similarita;
    }

    public void setSimilarita(double similarita) {
        this.similarita = similarita;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
 
//    @Override
//    public String toString() {
//        String rtn="id=" + id + ", b64immagine " + b64Image + ", luogo[" + luogo + "], data=" + data + ", autore[" + autore+"]";
//        if(fotoRiferimento!=null){
//            rtn+=",fotoRiferimento["+fotoRiferimento.toString()+"]";
//        }
//        rtn+= ", descrizione=" + descrizione+", tags[";
//        if(tags!=null){
//            rtn+=tags[0];
//            for(int i=0;i<tags.length;i++){
//                rtn+=","+tags[i];
//            }
//        }else{
//            rtn+="null";
//        }
//        rtn+="]";
//        
//                
//        rtn+=", similarita=" + similarita + ", ratio=" + ratio;
//        
//        return rtn;
//    }

    @Override
    public String toString() {
        return "Immagine{" + "id=" + id + ", b64Image=" + b64Image + ", luogo=" + luogo + ", data=" + data + ", autore=" + autore + ", fotoRiferimento=" + immagineRiferimento + ", descrizione=" + descrizione + ", tags=" + tags + ", similarita=" + similarita + ", ratio=" + ratio + '}';
    }
    public String parseImge(){
        String rtn=id+", "+luogo.getNome()+", "+ luogo.getCoordinate()+", "+data+","+autore.getNominativo()+", "+immagineRiferimento.getId()+", "+descrizione;
        rtn+=", "+tags[0];
        for(int i=1;i<tags.length;i++){
            rtn+=" "+tags[i];
        }
        rtn+=", "+b64Image;
        //id immagine, pos, data, nome autore, id riferimento, descrizione, tags[],immagine b64
        return rtn;
    }
            
}
