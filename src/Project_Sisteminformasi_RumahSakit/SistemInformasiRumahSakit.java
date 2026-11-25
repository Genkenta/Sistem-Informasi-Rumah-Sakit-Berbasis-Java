package Project_Sisteminformasi_RumahSakit;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SistemInformasiRumahSakit {
    public static void main(String[] args) {

        int pilihan = -1, umur, poli, tarif, nomor, estimasi, diskon;
        int sistolik, diastolik;
        int kode, jumlah, stokTemp, harga, subtotal;
        int kelas, malam, penjamin, persen, bayar, total, ditanggung, tagihan, kembalian;
        int adaPasien = 0, totalPasien = 0, totalPendapatan = 0;
        int biayaKonsul = 0, biayaObat = 0, biayaInap = 0;
        int antrU = 0, antrA = 0, antrG = 0, antrD = 0, antrJ = 0;
        int stok1 = 100, stok2 = 50, stok3 = 80, stok4 = 120, stok5 = 60;
        double berat, tinggi, tinggiM, suhu, bmi;
        String namaPasien = "", kategori = "", namaPoli = "", kodePoli = "";
        String namaObat = "", namaKamar = "", namaPenjamin = "";
        String statusBmi, statusSuhu, statusTensi;

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.println();
            System.out.println("   SISTEM INFORMASI RUMAH SAKIT   ");
            System.out.println("| ============================== |");
            System.out.println("| 1. Pendaftaran Pasien Baru     |");
            System.out.println("| 2. Pemeriksaan Awal            |");
            System.out.println("| 3. Resep & Tebus Obat          |");
            System.out.println("| 4. Rawat Inap                  |");
            System.out.println("| 5. Pembayaran                  |");
            System.out.println("| 6. Ringkasan Harian            |");
            System.out.println("| 0. Keluar                      |");
            System.out.println("| ============================== |");
            if (adaPasien == 1) {
                System.out.println("Pasien aktif: " + namaPasien + " (" + kategori + ")");
            }
            System.out.println();

            try {
                System.out.print("Input Pilihan [0-6]: ");
                pilihan = Integer.parseInt(input.readLine());

                switch (pilihan) {

                    // ================= 1. PENDAFTARAN =================
                    case 1:
                        System.out.print("Masukkan Nama Pasien          : ");
                        namaPasien = input.readLine();

                        System.out.print("Masukkan Umur Pasien (tahun)  : ");
                        umur = Integer.parseInt(input.readLine());

                        if (umur < 0) {
                            System.out.println("Umur tidak valid!");
                            break;
                        }

                        if (umur <= 12) {
                            kategori = "Anak";
                        } else if (umur <= 59) {
                            kategori = "Dewasa";
                        } else {
                            kategori = "Lansia";
                        }

                        System.out.println();
                        System.out.println("Daftar Poliklinik:");
                        System.out.println("1. Umum           - Rp. 50000");
                        System.out.println("2. Anak           - Rp. 75000");
                        System.out.println("3. Gigi           - Rp. 100000");
                        System.out.println("4. Penyakit Dalam - Rp. 150000");
                        System.out.println("5. Jantung        - Rp. 200000");

                        do {
                            System.out.print("Pilih Poliklinik [1-5]        : ");
                            poli = Integer.parseInt(input.readLine());
                            if (poli < 1 || poli > 5) {
                                System.out.println("Pilihan poliklinik tidak tersedia!");
                            }
                        } while (poli < 1 || poli > 5);

                        switch (poli) {
                            case 1:
                                namaPoli = "Umum"; kodePoli = "U"; tarif = 50000;
                                antrU = antrU + 1; nomor = antrU;
                                break;
                            case 2:
                                namaPoli = "Anak"; kodePoli = "A"; tarif = 75000;
                                antrA = antrA + 1; nomor = antrA;
                                break;
                            case 3:
                                namaPoli = "Gigi"; kodePoli = "G"; tarif = 100000;
                                antrG = antrG + 1; nomor = antrG;
                                break;
                            case 4:
                                namaPoli = "Penyakit Dalam"; kodePoli = "D"; tarif = 150000;
                                antrD = antrD + 1; nomor = antrD;
                                break;
                            default:
                                namaPoli = "Jantung"; kodePoli = "J"; tarif = 200000;
                                antrJ = antrJ + 1; nomor = antrJ;
                                break;
                        }

                        // Lansia mendapat diskon 20% biaya konsultasi
                        if (kategori.equals("Lansia")) {
                            diskon = tarif * 20 / 100;
                            tarif = tarif - diskon;
                            System.out.println("Diskon lansia 20%             : Rp. " + diskon);
                        }

                        biayaKonsul = tarif;
                        biayaObat = 0;
                        biayaInap = 0;
                        adaPasien = 1;
                        totalPasien = totalPasien + 1;

                        estimasi = (nomor - 1) * 15;

                        System.out.println();
                        System.out.println("===== PENDAFTARAN BERHASIL =====");
                        System.out.println("Nama             : " + namaPasien);
                        System.out.println("Umur / Kategori  : " + umur + " tahun / " + kategori);
                        System.out.println("Poliklinik       : " + namaPoli);
                        System.out.println("Biaya Konsultasi : Rp. " + biayaKonsul);
                        System.out.println("Nomor Antrean    : " + kodePoli + "-" + nomor);
                        System.out.println("Estimasi Tunggu  : " + estimasi + " menit");
                        break;

                    // ================= 2. PEMERIKSAAN AWAL =================
                    case 2:
                        if (adaPasien == 0) {
                            System.out.println("Belum ada pasien. Lakukan pendaftaran terlebih dahulu!");
                            break;
                        }

                        System.out.print("Masukkan Berat Badan (kg)      : ");
                        berat = Double.parseDouble(input.readLine());

                        System.out.print("Masukkan Tinggi Badan (cm)     : ");
                        tinggi = Double.parseDouble(input.readLine());

                        System.out.print("Masukkan Suhu Tubuh (C)        : ");
                        suhu = Double.parseDouble(input.readLine());

                        System.out.print("Masukkan Tekanan Sistolik      : ");
                        sistolik = Integer.parseInt(input.readLine());

                        System.out.print("Masukkan Tekanan Diastolik     : ");
                        diastolik = Integer.parseInt(input.readLine());

                        if (berat <= 0 || tinggi <= 0) {
                            System.out.println("Berat dan tinggi harus lebih dari 0!");
                            break;
                        }

                        tinggiM = tinggi / 100;
                        bmi = berat / (tinggiM * tinggiM);

                        if (bmi < 18.5) {
                            statusBmi = "Kurus";
                        } else if (bmi < 25) {
                            statusBmi = "Normal";
                        } else if (bmi < 30) {
                            statusBmi = "Kelebihan Berat Badan";
                        } else {
                            statusBmi = "Obesitas";
                        }

                        if (suhu < 36.0) {
                            statusSuhu = "Hipotermia";
                        } else if (suhu <= 37.5) {
                            statusSuhu = "Normal";
                        } else if (suhu <= 38.5) {
                            statusSuhu = "Demam";
                        } else {
                            statusSuhu = "Demam Tinggi";
                        }

                        if (sistolik < 90 || diastolik < 60) {
                            statusTensi = "Rendah (Hipotensi)";
                        } else if (sistolik < 120 && diastolik < 80) {
                            statusTensi = "Normal";
                        } else if (sistolik < 140 && diastolik < 90) {
                            statusTensi = "Pra-Hipertensi";
                        } else {
                            statusTensi = "Tinggi (Hipertensi)";
                        }

                        System.out.println();
                        System.out.println("===== HASIL PEMERIKSAAN AWAL =====");
                        System.out.println("Pasien        : " + namaPasien);
                        System.out.println("BMI           : " + String.format("%.2f", bmi) + " (" + statusBmi + ")");
                        System.out.println("Suhu Tubuh    : " + suhu + " C (" + statusSuhu + ")");
                        System.out.println("Tekanan Darah : " + sistolik + "/" + diastolik + " mmHg (" + statusTensi + ")");
                        break;

                    // ================= 3. RESEP & TEBUS OBAT =================
                    case 3:
                        if (adaPasien == 0) {
                            System.out.println("Belum ada pasien. Lakukan pendaftaran terlebih dahulu!");
                            break;
                        }

                        do {
                            System.out.println();
                            System.out.println("Daftar Obat:");
                            System.out.println("1. Paracetamol - Rp. 5000  (stok: " + stok1 + ")");
                            System.out.println("2. Amoxicillin - Rp. 12000 (stok: " + stok2 + ")");
                            System.out.println("3. Antasida    - Rp. 4000  (stok: " + stok3 + ")");
                            System.out.println("4. Vitamin C   - Rp. 3000  (stok: " + stok4 + ")");
                            System.out.println("5. Ibuprofen   - Rp. 8000  (stok: " + stok5 + ")");

                            System.out.print("Pilih kode obat [1-5, 0 = selesai] : ");
                            kode = Integer.parseInt(input.readLine());

                            if (kode >= 1 && kode <= 5) {
                                switch (kode) {
                                    case 1:
                                        namaObat = "Paracetamol"; harga = 5000; stokTemp = stok1;
                                        break;
                                    case 2:
                                        namaObat = "Amoxicillin"; harga = 12000; stokTemp = stok2;
                                        break;
                                    case 3:
                                        namaObat = "Antasida"; harga = 4000; stokTemp = stok3;
                                        break;
                                    case 4:
                                        namaObat = "Vitamin C"; harga = 3000; stokTemp = stok4;
                                        break;
                                    default:
                                        namaObat = "Ibuprofen"; harga = 8000; stokTemp = stok5;
                                        break;
                                }

                                System.out.print("Masukkan jumlah " + namaObat + " : ");
                                jumlah = Integer.parseInt(input.readLine());

                                if (jumlah <= 0) {
                                    System.out.println("Jumlah tidak valid!");
                                } else if (jumlah <= stokTemp) {
                                    switch (kode) {
                                        case 1:
                                            stok1 = stok1 - jumlah;
                                            break;
                                        case 2:
                                            stok2 = stok2 - jumlah;
                                            break;
                                        case 3:
                                            stok3 = stok3 - jumlah;
                                            break;
                                        case 4:
                                            stok4 = stok4 - jumlah;
                                            break;
                                        default:
                                            stok5 = stok5 - jumlah;
                                            break;
                                    }
                                    subtotal = jumlah * harga;
                                    biayaObat = biayaObat + subtotal;
                                    System.out.println(jumlah + " " + namaObat + " berhasil ditebus. Subtotal Rp. " + subtotal);
                                } else {
                                    System.out.println("Stok tidak cukup! Sisa stok " + namaObat + " : " + stokTemp);
                                }
                            } else if (kode != 0) {
                                System.out.println("Kode obat tidak tersedia!");
                            }
                        } while (kode != 0);

                        System.out.println("Total biaya obat saat ini : Rp. " + biayaObat);
                        break;

                    // ================= 4. RAWAT INAP =================
                    case 4:
                        if (adaPasien == 0) {
                            System.out.println("Belum ada pasien. Lakukan pendaftaran terlebih dahulu!");
                            break;
                        }

                        System.out.println();
                        System.out.println("Kelas Kamar:");
                        System.out.println("1. VIP     - Rp. 750000 / malam");
                        System.out.println("2. Kelas 1 - Rp. 500000 / malam");
                        System.out.println("3. Kelas 2 - Rp. 300000 / malam");
                        System.out.println("4. Kelas 3 - Rp. 150000 / malam");

                        do {
                            System.out.print("Pilih kelas kamar [1-4]          : ");
                            kelas = Integer.parseInt(input.readLine());
                            if (kelas < 1 || kelas > 4) {
                                System.out.println("Kelas kamar tidak tersedia!");
                            }
                        } while (kelas < 1 || kelas > 4);

                        do {
                            System.out.print("Masukkan lama menginap (malam)   : ");
                            malam = Integer.parseInt(input.readLine());
                            if (malam < 1) {
                                System.out.println("Lama menginap minimal 1 malam!");
                            }
                        } while (malam < 1);

                        switch (kelas) {
                            case 1:
                                namaKamar = "VIP"; harga = 750000;
                                break;
                            case 2:
                                namaKamar = "Kelas 1"; harga = 500000;
                                break;
                            case 3:
                                namaKamar = "Kelas 2"; harga = 300000;
                                break;
                            default:
                                namaKamar = "Kelas 3"; harga = 150000;
                                break;
                        }

                        biayaInap = harga * malam;

                        System.out.println();
                        System.out.println("===== RAWAT INAP =====");
                        System.out.println("Kamar      : " + namaKamar);
                        System.out.println("Lama Inap  : " + malam + " malam");
                        System.out.println("Biaya Inap : Rp. " + biayaInap);
                        break;

                    // ================= 5. PEMBAYARAN =================
                    case 5:
                        if (adaPasien == 0) {
                            System.out.println("Belum ada pasien. Lakukan pendaftaran terlebih dahulu!");
                            break;
                        }

                        total = biayaKonsul + biayaObat + biayaInap;

                        System.out.println();
                        System.out.println("Jenis Penjamin:");
                        System.out.println("1. Umum      (ditanggung 0%)");
                        System.out.println("2. BPJS      (ditanggung 80%)");
                        System.out.println("3. Asuransi  (ditanggung 60%)");

                        do {
                            System.out.print("Pilih penjamin [1-3] : ");
                            penjamin = Integer.parseInt(input.readLine());
                            if (penjamin < 1 || penjamin > 3) {
                                System.out.println("Pilihan penjamin tidak tersedia!");
                            }
                        } while (penjamin < 1 || penjamin > 3);

                        switch (penjamin) {
                            case 2:
                                namaPenjamin = "BPJS"; persen = 80;
                                break;
                            case 3:
                                namaPenjamin = "Asuransi"; persen = 60;
                                break;
                            default:
                                namaPenjamin = "Umum"; persen = 0;
                                break;
                        }

                        ditanggung = total * persen / 100;
                        tagihan = total - ditanggung;

                        System.out.println();
                        System.out.println("===== RINCIAN BIAYA =====");
                        System.out.println("Pasien             : " + namaPasien);
                        System.out.println("Biaya Konsultasi   : Rp. " + biayaKonsul);
                        System.out.println("Biaya Obat         : Rp. " + biayaObat);
                        System.out.println("Biaya Rawat Inap   : Rp. " + biayaInap);
                        System.out.println("Total Biaya        : Rp. " + total);
                        System.out.println("Penjamin           : " + namaPenjamin);
                        System.out.println("Ditanggung         : Rp. " + ditanggung);
                        System.out.println("Yang Harus Dibayar : Rp. " + tagihan);

                        do {
                            System.out.print("Masukkan uang pembayaran : Rp. ");
                            bayar = Integer.parseInt(input.readLine());
                            if (bayar < tagihan) {
                                System.out.println("Uang kurang Rp. " + (tagihan - bayar) + ", silakan ulangi!");
                            }
                        } while (bayar < tagihan);

                        kembalian = bayar - tagihan;
                        System.out.println("Kembalian          : Rp. " + kembalian);
                        System.out.println("Pembayaran berhasil. Terima kasih, semoga lekas sembuh!");

                        totalPendapatan = totalPendapatan + tagihan;
                        adaPasien = 0;
                        break;

                    // ================= 6. RINGKASAN HARIAN =================
                    case 6:
                        System.out.println();
                        System.out.println("===== RINGKASAN HARIAN =====");
                        System.out.println("Total Pasien     : " + totalPasien);
                        System.out.println("Total Pendapatan : Rp. " + totalPendapatan);
                        System.out.println("Sisa Stok Obat   :");
                        System.out.println("  - Paracetamol : " + stok1);
                        System.out.println("  - Amoxicillin : " + stok2);
                        System.out.println("  - Antasida    : " + stok3);
                        System.out.println("  - Vitamin C   : " + stok4);
                        System.out.println("  - Ibuprofen   : " + stok5);
                        break;

                    case 0:
                        System.out.println("Terima kasih. Program selesai.");
                        break;

                    default:
                        System.out.println("OPSI TIDAK TERSEDIA!!");
                        break;
                }
            } catch (Exception ie) {
                System.out.println("Input tidak valid, silakan ulangi!");
                pilihan = -1;
            }
        } while (pilihan != 0);
    }
}