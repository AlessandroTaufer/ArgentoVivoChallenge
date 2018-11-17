package Cliente;

public class Luogo {
    private String id;
    private String nome;
    private Coordinate coordinate; 


    public Luogo(String id, String nome, String coordinate) {
        this.id = id;
        this.nome = nome;     
        String [] coord = coordinate.split("/");
        this.coordinate = new Coordinate(Double.parseDouble(coord[0]),Double.parseDouble(coord[1]));
    }

    public String getId(){
        return id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setId(String id){
            this.id=id;
    }

    @Override
    public String toString() {
        String rtn =  "id=" + id + ", nome=" + nome + ", posizione" + coordinate + "]";
        return rtn;
    }
    
    
    
            
}
