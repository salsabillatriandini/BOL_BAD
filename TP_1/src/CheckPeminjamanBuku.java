import java.util.Scanner;

public class CheckPeminjamanBuku {
    public static void main(String[] args) {
        int dendaBukuPelajaran = 2000;
        int dendaBukuNovel = 5000;
        int dendaBukuSkripsi = 10000;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan jenis buku (pelajaran/novel/skripsi): ");
        String jenisBuku = scanner.nextLine();

        System.out.print("Masukkan jumlah hari terlambat: ");
        int hariTerlambat = scanner.nextInt();

        int totalDenda = 0;
        if (jenisBuku.equalsIgnoreCase("pelajaran")) {
            totalDenda = dendaBukuPelajaran * hariTerlambat;
        } else if (jenisBuku.equalsIgnoreCase("novel")) {
            totalDenda = dendaBukuNovel * hariTerlambat;
        } else if (jenisBuku.equalsIgnoreCase("skripsi")) {
            totalDenda = dendaBukuSkripsi * hariTerlambat;
        } else {
            System.out.println("Jenis buku tidak valid.");
            System.exit(0);
        }

        System.out.println("Total denda: Rp" + totalDenda);
    }
}