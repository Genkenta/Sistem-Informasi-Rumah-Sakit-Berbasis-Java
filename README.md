Sistem Informasi Rumah Sakit Berbasis Java

Aplikasi console berbasis Java untuk mengurus pasien rumah sakit dari pendaftaran sampai pembayaran.

Petugas mendaftarkan pasien, memilih poliklinik, mencatat hasil pemeriksaan awal, menebus obat, lalu memproses pembayaran. Di akhir hari, program merangkum jumlah pasien dan total pendapatan.

Fitur

- Pendaftaran pasien baru dengan kategori umur: anak, dewasa, lansia
- Pilihan poliklinik dengan tarif konsultasi berbeda
- Nomor antrean dan estimasi waktu tunggu
- Pemeriksaan awal: hitung BMI, status suhu, dan status tekanan darah
- Resep dan tebus obat, dengan pengecekan stok
- Rawat inap: biaya dihitung dari kelas kamar dan lama menginap
- Pembayaran dengan penjamin umum, BPJS, atau asuransi, plus hitungan kembalian
- Ringkasan harian: total pasien, total pendapatan, sisa stok obat

Tech

- Java (console)

Tim

Kami mengerjakan project ini bertiga:

- Genta Priya Kencana ([@Genkenta](https://github.com/Genkenta))
- Prasetya Adi Putra ([@Rasethh](https://github.com/Rasethh))
- Iqbal Rafianto Said ([@RyujiIshida7](https://github.com/RyujiIshida7))

package PRAUAS;
import java.util.Scanner;
import java.util.Locale
public class nyube {
    ;

/**
 * Sistem Informasi Rumah Sakit Berbasis Java (Console)
 * Mengurus alur pasien dari pendaftaran, poliklinik, pemeriksaan awal,
 * penebusan obat, rawat inap, pembayaran, hingga ringkasan harian.
 */


    // Data Poliklinik
    static final String[] NAMA_POLI = {
        "Poli Umum",
        "Poli Anak",
        "Poli Penyakit Dalam",
        "Poli Gigi",
        "Poli THT"
    };
    static final double[] TARIF_POLI = {
        50000,
        75000,
        120000,
        85000,
        90000
    };
    static int[] antreanPoli = new int[NAMA_POLI.length];
    static final int ESTIMASI_MENIT_PER_PASIEN = 15;

    // Data Obat & Stok
    static final String[] NAMA_OBAT = {
        "Paracetamol 500mg",
        "Amoxicillin 500mg",
        "Vitamin C 500mg",
        "Antasida Doen",
        "Amlodipine 5mg",
        "OBH Sirup 100ml"
    };
    static final double[] HARGA_OBAT = {
        10000,
        25000,
        15000,
        12000,
        18000,
        22000
    };
    static int[] stokObat = {
        50,
        30,
        40,
        25,
        20,
        35
    };

    // Data Kamar Rawat Inap
    static final String[] KELAS_KAMAR = {
        "Kelas 3",
        "Kelas 2",
        "Kelas 1",
        "VIP"
    };
    static final double[] TARIF_KAMAR = {
        150000,
        300000,
        500000,
        1000000
    };

    // Statistik Ringkasan Harian
    static int totalPasienHariIni = 0;
    static int jumlahAnak = 0;
    static int jumlahDewasa = 0;
    static int jumlahLansia = 0;
    static int jumlahRawatInap = 0;
    static double totalPendapatanKotor = 0;
    static double totalDitanggungPenjamin = 0;
    static double totalPendapatanTunai = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in).useLocale(Locale.US);
        boolean berjalan = true;

        tampilkanHeader();

        while (berjalan) {
            System.out.println("\n================ MENU UTAMA ================");
            System.out.println("1. Layani Pasien Baru (Pendaftaran - Pembayaran)");
            System.out.println("2. Cek Stok & Daftar Harga Obat");
            System.out.println("3. Lihat Ringkasan Harian Sementara");
            System.out.println("4. Tutup Operasional & Tampilkan Laporan Akhir Hari");
            System.out.print("Pilih menu (1-4): ");

            int menu = bacaInt(input, 1, 4);

            switch (menu) {
                case 1:
                    layaniPasien(input);
                    break;
                case 2:
                    tampilkanDaftarObat();
                    break;
                case 3:
                    tampilkanRingkasanHarian(false);
                    break;
                case 4:
                    tampilkanRingkasanHarian(true);
                    berjalan = false;
                    break;
            }
        }

        input.close();
    }

    static void tampilkanHeader() {
        System.out.println("============================================================");
        System.out.println("          SISTEM INFORMASI RUMAH SAKIT SEHAT SENTOSA        ");
        System.out.println("============================================================");
    }

    static void layaniPasien(Scanner input) {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("TAHAP 1: PENDAFTARAN PASIEN BARU");
        System.out.println("------------------------------------------------------------");

        System.out.print("Nama Lengkap Pasien : ");
        String nama = bacaStringTidakKosong(input);

        System.out.print("NIK / No. Identitas : ");
        String nik = bacaStringTidakKosong(input);

        System.out.print("Umur Pasien (tahun) : ");
        int umur = bacaInt(input, 0, 150);

        String kategoriUmur = tentukanKategoriUmur(umur);
        System.out.println("-> Kategori Umur    : " + kategoriUmur);

        // Tahap 2: Pemilihan Poliklinik & Nomor Antrean
        System.out.println("\n------------------------------------------------------------");
        System.out.println("TAHAP 2: PEMILIHAN POLIKLINIK & ANTREAN");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < NAMA_POLI.length; i++) {
            System.out.printf("%d. %-22s (Tarif: Rp %,10.0f | Antrean saat ini: %d)%n",
                    (i + 1), NAMA_POLI[i], TARIF_POLI[i], antreanPoli[i]);
        }
        System.out.print("Pilih Poliklinik (1-" + NAMA_POLI.length + "): ");
        int indexPoli = bacaInt(input, 1, NAMA_POLI.length) - 1;

        String poliDipilih = NAMA_POLI[indexPoli];
        double biayaKonsultasi = TARIF_POLI[indexPoli];
        antreanPoli[indexPoli]++;
        int nomorAntrean = antreanPoli[indexPoli];
        int estimasiTunggu = (nomorAntrean - 1) * ESTIMASI_MENIT_PER_PASIEN;
        String kodeAntrean = String.format("%c-%03d", poliDipilih.charAt(5), nomorAntrean);

        System.out.println("-> Nomor Antrean    : " + kodeAntrean + " (" + poliDipilih + ")");
        System.out.println("-> Estimasi Tunggu  : " + estimasiTunggu + " menit");

        // Tahap 3: Pemeriksaan Awal (Triage / Tanda Vital)
        System.out.println("\n------------------------------------------------------------");
        System.out.println("TAHAP 3: PEMERIKSAAN AWAL (TANDA VITAL & BMI)");
        System.out.println("------------------------------------------------------------");

        System.out.print("Berat Badan (kg)           : ");
        double berat = bacaDouble(input, 1.0, 400.0);

        System.out.print("Tinggi Badan (cm)          : ");
        double tinggiCm = bacaDouble(input, 30.0, 260.0);

        System.out.print("Suhu Tubuh (°C)            : ");
        double suhu = bacaDouble(input, 30.0, 45.0);

        System.out.print("Tekanan Darah Sistolik     : ");
        int sistolik = bacaInt(input, 50, 260);

        System.out.print("Tekanan Darah Diastolik    : ");
        int diastolik = bacaInt(input, 30, 160);

        double tinggiM = tinggiCm / 100.0;
        double bmi = berat / (tinggiM * tinggiM);
        String statusBmi = hitungStatusBMI(bmi);
        String statusSuhu = hitungStatusSuhu(suhu);
        String statusTensi = hitungStatusTekananDarah(sistolik, diastolik);

        System.out.println("\n--- Hasil Pemeriksaan Awal ---");
        System.out.printf("BMI            : %.2f (%s)%n", bmi, statusBmi);
        System.out.printf("Suhu Tubuh     : %.1f °C (%s)%n", suhu, statusSuhu);
        System.out.printf("Tekanan Darah  : %d/%d mmHg (%s)%n", sistolik, diastolik, statusTensi);

        // Tahap 4: Resep & Tebus Obat
        System.out.println("\n------------------------------------------------------------");
        System.out.println("TAHAP 4: RESEP & TEBUS OBAT");
        System.out.println("------------------------------------------------------------");

        double totalBiayaObat = 0;
        StringBuilder rincianObat = new StringBuilder();
        boolean tambahObat = true;

        System.out.print("Apakah pasien memerlukan resep obat? (y/n): ");
        tambahObat = bacaYaTidak(input);

        while (tambahObat) {
            tampilkanDaftarObat();
            System.out.print("Pilih nomor obat (1-" + NAMA_OBAT.length + ", atau 0 untuk selesai): ");
            int pilihObat = bacaInt(input, 0, NAMA_OBAT.length);

            if (pilihObat == 0) {
                break;
            }

            int idxObat = pilihObat - 1;
            if (stokObat[idxObat] <= 0) {
                System.out.println("[!] Maaf, stok " + NAMA_OBAT[idxObat] + " sedang habis.");
            } else {
                System.out.print("Masukkan jumlah " + NAMA_OBAT[idxObat] + " (Stok tersedia: " + stokObat[idxObat] + "): ");
                int qty = bacaInt(input, 1, 1000);

                if (qty > stokObat[idxObat]) {
                    System.out.println("[!] Stok tidak mencukupi! Stok tersedia hanya " + stokObat[idxObat] + " unit.");
                } else {
                    stokObat[idxObat] -= qty;
                    double subtotalObat = qty * HARGA_OBAT[idxObat];
                    totalBiayaObat += subtotalObat;
                    rincianObat.append(String.format("   - %-20s x %2d = Rp %,10.0f%n",
                            NAMA_OBAT[idxObat], qty, subtotalObat));
                    System.out.printf("-> Berhasil menambahkan %d x %s (Subtotal: Rp %,.0f)%n",
                            qty, NAMA_OBAT[idxObat], subtotalObat);
                }
            }

            System.out.print("Tambah obat lain? (y/n): ");
            tambahObat = bacaYaTidak(input);
        }

        // Tahap 5: Rawat Inap
        System.out.println("\n------------------------------------------------------------");
        System.out.println("TAHAP 5: RAWAT INAP");
        System.out.println("------------------------------------------------------------");
        double biayaRawatInap = 0;
        String infoRawatInap = "Tidak Rawat Inap (Rawat Jalan)";

        System.out.print("Apakah pasien perlu menjalani Rawat Inap? (y/n): ");
        boolean perluRawatInap = bacaYaTidak(input);

        if (perluRawatInap) {
            System.out.println("Pilihan Kelas Kamar Rawat Inap:");
            for (int i = 0; i < KELAS_KAMAR.length; i++) {
                System.out.printf("%d. %-10s - Rp %,10.0f / malam%n", (i + 1), KELAS_KAMAR[i], TARIF_KAMAR[i]);
            }
            System.out.print("Pilih Kelas Kamar (1-" + KELAS_KAMAR.length + "): ");
            int idxKamar = bacaInt(input, 1, KELAS_KAMAR.length) - 1;

            System.out.print("Lama menginap (hari/malam): ");
            int lamaMenginap = bacaInt(input, 1, 365);

            biayaRawatInap = TARIF_KAMAR[idxKamar] * lamaMenginap;
            infoRawatInap = String.format("%s selama %d malam (@ Rp %,.0f)",
                    KELAS_KAMAR[idxKamar], lamaMenginap, TARIF_KAMAR[idxKamar]);
            jumlahRawatInap++;
        }

        // Tahap 6: Pembayaran
        System.out.println("\n------------------------------------------------------------");
        System.out.println("TAHAP 6: PEMBAYARAN & PENJAMIN");
        System.out.println("------------------------------------------------------------");

        double totalTagihan = biayaKonsultasi + totalBiayaObat + biayaRawatInap;

        System.out.println("Rincian Biaya Sementara:");
        System.out.printf("- Biaya Konsultasi (%s) : Rp %,12.0f%n", poliDipilih, biayaKonsultasi);
        System.out.printf("- Total Biaya Obat            : Rp %,12.0f%n", totalBiayaObat);
        System.out.printf("- Biaya Rawat Inap            : Rp %,12.0f%n", biayaRawatInap);
        System.out.printf("TOTAL TAGIHAN KOTOR           : Rp %,12.0f%n", totalTagihan);

        System.out.println("\nPilih Jenis Penjamin Pembayaran:");
        System.out.println("1. Umum / Mandiri (Bayar Penuh 100%)");
        System.out.println("2. BPJS Kesehatan (Ditanggung 100% sesuai ketentuan)");
        System.out.println("3. Asuransi Swasta (Ditanggung 80%, Pasien bayar Co-pay 20%)");
        System.out.print("Pilih Penjamin (1-3): ");
        int jenisPenjamin = bacaInt(input, 1, 3);

        String namaPenjamin = "";
        double potonganPenjamin = 0;

        switch (jenisPenjamin) {
            case 1:
                namaPenjamin = "Umum / Mandiri";
                potonganPenjamin = 0;
                break;
            case 2:
                namaPenjamin = "BPJS Kesehatan";
                potonganPenjamin = totalTagihan; // Ditanggung 100%
                break;
            case 3:
                namaPenjamin = "Asuransi Swasta (80%)";
                potonganPenjamin = totalTagihan * 0.80; // Ditanggung 80%
                break;
        }

        double tagihanHarusDibayar = totalTagihan - potonganPenjamin;
        double uangDibayar = 0;
        double kembalian = 0;

        System.out.printf("-> Ditanggung Penjamin        : Rp %,12.0f%n", potonganPenjamin);
        System.out.printf("-> Tagihan Harus Dibayar      : Rp %,12.0f%n", tagihanHarusDibayar);

        if (tagihanHarusDibayar > 0) {
            while (true) {
                System.out.print("Masukkan Nominal Uang Tunai   : Rp ");
                uangDibayar = bacaDouble(input, 0, 1000000000);
                if (uangDibayar >= tagihanHarusDibayar) {
                    kembalian = uangDibayar - tagihanHarusDibayar;
                    break;
                } else {
                    System.out.printf("[!] Uang tidak cukup! Kurang Rp %,.0f%n", (tagihanHarusDibayar - uangDibayar));
                }
            }
        } else {
            System.out.println("-> Tagihan Rp 0 (Lunas ditanggung penuh oleh BPJS Kesehatan).");
        }

        // Cetak Struk Lengkap
        System.out.println("\n============================================================");
        System.out.println("                 STRUK PELAYANAN RUMAH SAKIT                ");
        System.out.println("============================================================");
        System.out.println("Nama Pasien      : " + nama + " (NIK: " + nik + ")");
        System.out.println("Umur / Kategori  : " + umur + " tahun (" + kategoriUmur + ")");
        System.out.println("Poliklinik       : " + poliDipilih + " | No. Antrean: " + kodeAntrean);
        System.out.println("Estimasi Tunggu  : " + estimasiTunggu + " menit");
        System.out.println("------------------------------------------------------------");
        System.out.println("HASIL PEMERIKSAAN AWAL:");
        System.out.printf("  - BMI           : %.2f (%s)%n", bmi, statusBmi);
        System.out.printf("  - Suhu Tubuh    : %.1f °C (%s)%n", suhu, statusSuhu);
        System.out.printf("  - Tekanan Darah : %d/%d mmHg (%s)%n", sistolik, diastolik, statusTensi);
        System.out.println("------------------------------------------------------------");
        System.out.println("RINCIAN BIAYA:");
        System.out.printf("  1. Konsultasi %-18s : Rp %,12.0f%n", poliDipilih, biayaKonsultasi);
        System.out.printf("  2. Obat-obatan                   : Rp %,12.0f%n", totalBiayaObat);
        if (rincianObat.length() > 0) {
            System.out.print(rincianObat.toString());
        }
        System.out.printf("  3. Rawat Inap                    : Rp %,12.0f%n", biayaRawatInap);
        System.out.println("     (" + infoRawatInap + ")");
        System.out.println("------------------------------------------------------------");
        System.out.printf("Total Tagihan Kotor                : Rp %,12.0f%n", totalTagihan);
        System.out.println("Penjamin Pembayaran                : " + namaPenjamin);
        System.out.printf("Ditanggung Penjamin                : Rp %,12.0f%n", potonganPenjamin);
        System.out.printf("Total Dibayar Pasien               : Rp %,12.0f%n", tagihanHarusDibayar);
        System.out.printf("Uang Tunai Diterima                : Rp %,12.0f%n", uangDibayar);
        System.out.printf("Kembalian                          : Rp %,12.0f%n", kembalian);
        System.out.println("============================================================");

        // Update Statistik Harian
        totalPasienHariIni++;
        if (kategoriUmur.equals("Anak")) {
            jumlahAnak++;
        } else if (kategoriUmur.equals("Dewasa")) {
            jumlahDewasa++;
        } else {
            jumlahLansia++;
        }

        totalPendapatanKotor += totalTagihan;
        totalDitanggungPenjamin += potonganPenjamin;
        totalPendapatanTunai += tagihanHarusDibayar;
    }

    // Fungsi penentu kategori umur
    static String tentukanKategoriUmur(int umur) {
        if (umur <= 17) {
            return "Anak";
        } else if (umur < 60) {
            return "Dewasa";
        } else {
            return "Lansia";
        }
    }

    // Fungsi status BMI
    static String hitungStatusBMI(double bmi) {
        if (bmi < 18.5) {
            return "Kurus (Underweight)";
        } else if (bmi < 25.0) {
            return "Normal (Ideal)";
        } else if (bmi < 30.0) {
            return "Kelebihan Berat Badan (Overweight)";
        } else {
            return "Obesitas";
        }
    }

    // Fungsi status suhu tubuh
    static String hitungStatusSuhu(double suhu) {
        if (suhu < 36.0) {
            return "Hipotermia (Suhu Rendah)";
        } else if (suhu <= 37.5) {
            return "Normal";
        } else if (suhu <= 39.0) {
            return "Demam";
        } else {
            return "Demam Tinggi (Hiperpireksia)";
        }
    }

    // Fungsi status tekanan darah
    static String hitungStatusTekananDarah(int sistolik, int diastolik) {
        if (sistolik < 90 || diastolik < 60) {
            return "Hipotensi (Tekanan Darah Rendah)";
        } else if (sistolik < 120 && diastolik < 80) {
            return "Normal";
        } else if (sistolik < 140 && diastolik < 90) {
            return "Pra-Hipertensi";
        } else {
            return "Hipertensi (Tekanan Darah Tinggi)";
        }
    }

    static void tampilkanDaftarObat() {
        System.out.println("\n--- Daftar Obat & Sisa Stok ---");
        System.out.printf("%-4s %-22s %-15s %-10s%n", "No.", "Nama Obat", "Harga Satuan", "Sisa Stok");
        for (int i = 0; i < NAMA_OBAT.length; i++) {
            System.out.printf("%-4d %-22s Rp %,10.0f   %d unit%n",
                    (i + 1), NAMA_OBAT[i], HARGA_OBAT[i], stokObat[i]);
        }
    }

    static void tampilkanRingkasanHarian(boolean akhirHari) {
        System.out.println("\n============================================================");
        if (akhirHari) {
            System.out.println("          LAPORAN RINGKASAN AKHIR HARI RUMAH SAKIT          ");
        } else {
            System.out.println("          RINGKASAN OPERASIONAL SEMENTARA HARI INI          ");
        }
        System.out.println("============================================================");
        System.out.println("1. STATISTIK PASIEN:");
        System.out.println("   - Total Pasien Dilayani : " + totalPasienHariIni + " pasien");
        System.out.println("   - Pasien Anak (0-17 th) : " + jumlahAnak + " pasien");
        System.out.println("   - Pasien Dewasa (18-59) : " + jumlahDewasa + " pasien");
        System.out.println("   - Pasien Lansia (60+)   : " + jumlahLansia + " pasien");
        System.out.println("   - Pasien Rawat Inap     : " + jumlahRawatInap + " pasien");

        System.out.println("\n2. KUNJUNGAN PER POLIKLINIK:");
        for (int i = 0; i < NAMA_POLI.length; i++) {
            System.out.printf("   - %-22s : %d pasien%n", NAMA_POLI[i], antreanPoli[i]);
        }

        System.out.println("\n3. RINGKASAN PENDAPATAN:");
        System.out.printf("   - Total Nilai Pelayanan (Kotor)   : Rp %,14.0f%n", totalPendapatanKotor);
        System.out.printf("   - Klaim Penjamin (BPJS/Asuransi)  : Rp %,14.0f%n", totalDitanggungPenjamin);
        System.out.printf("   - Pendapatan Tunai Kasir          : Rp %,14.0f%n", totalPendapatanTunai);

        System.out.println("\n4. SISA STOK OBAT APOTEK:");
        for (int i = 0; i < NAMA_OBAT.length; i++) {
            System.out.printf("   - %-22s : %3d unit%n", NAMA_OBAT[i], stokObat[i]);
        }
        System.out.println("============================================================");
        if (akhirHari) {
            System.out.println("Terima kasih. Sistem Informasi Rumah Sakit telah ditutup.");
        }
    }

    // Helper input validasi
    static String bacaStringTidakKosong(Scanner input) {
        while (true) {
            String teks = input.nextLine().trim();
            if (!teks.isEmpty()) {
                return teks;
            }
            System.out.print("Input tidak boleh kosong, silakan isi kembali: ");
        }
    }

    static int bacaInt(Scanner input, int min, int max) {
        while (true) {
            String baris = input.nextLine().trim();
            try {
                int nilai = Integer.parseInt(baris);
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
                System.out.print("Masukkan angka antara " + min + " - " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid. Masukkan angka bulat: ");
            }
        }
    }

    static double bacaDouble(Scanner input, double min, double max) {
        while (true) {
            String baris = input.nextLine().trim().replace(',', '.');
            try {
                double nilai = Double.parseDouble(baris);
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
                System.out.printf("Masukkan nilai antara %.1f - %.1f: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid. Masukkan angka: ");
            }
        }
    }

    static boolean bacaYaTidak(Scanner input) {
        while (true) {
            String jawab = input.nextLine().trim().toLowerCase();
            if (jawab.equals("y") || jawab.equals("ya")) {
                return true;
            } else if (jawab.equals("n") || jawab.equals("tidak") || jawab.equals("t")) {
                return false;
            }
            System.out.print("Masukkan 'y' (Ya) atau 'n' (Tidak): ");
        }
    }
}
