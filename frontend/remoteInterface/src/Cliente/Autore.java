package Cliente;

class Autore {
    private String id;
    private String nominativo, password;
    private Immagine immagineProfilo;

    public Autore(String id, String nominativo, String password, Immagine immagineProfilo) {
        this.id = id;
        this.nominativo = nominativo;
        this.password = password;
        this.immagineProfilo= immagineProfilo;
    }
    public Immagine getimmagineProfilo(){
        return immagineProfilo;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNominativo() {
        return nominativo;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImmagineProfilo(Immagine immagineProfilo){
        this.immagineProfilo=immagineProfilo;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nominativo=" + nominativo + ", password=" + password + ", immagineProfilo[" +"]";
    }
}
