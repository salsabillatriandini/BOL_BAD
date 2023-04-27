package entity;

public class Users {
    private int id;
    private String nama;
    private String nik;
    private String birth;
    private String alamat;

    public Users() {

    }
    public Users(int id, String nama, String nik, String birth, String alamat) {
        this.id = id;
        this.nama = nama;
        this.nik = nik;
        this.birth = birth;
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
