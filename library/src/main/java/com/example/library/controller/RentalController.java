package com.example.library.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dto.RentalRequest;
import com.example.library.dto.RentalView;
import com.example.library.entity.Book;
import com.example.library.entity.Rental;
import com.example.library.repository.BookRepository;
import com.example.library.repository.RentalRepository;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @PostMapping
    public String rent(@RequestBody RentalRequest request) {

        // ① 本取得
        Book book = bookRepository.findByQrCode(request.getQrCode())
                .orElseThrow(() -> new RuntimeException("本が見つからない"));

        // ② 在庫チェック
        if (!book.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("貸出中です");
        }

        // ③ 貸出登録
        Rental rental = new Rental();
        rental.setBookId(book.getId());
        rental.setUserId(request.getUserId());
        rental.setRentedAt(LocalDateTime.now());

        rentalRepository.save(rental);

        // ④ 本の状態更新
        book.setStatus("BORROWED");
        bookRepository.save(book);

        return "貸出完了";
    }
    
    @PostMapping("/return")
    public String returnBook(@RequestBody RentalRequest request) {

        // ① 本取得
        Book book = bookRepository.findByQrCode(request.getQrCode())
                .orElseThrow(() -> new RuntimeException("本が見つからない"));

        // ② 貸出中データ取得
        Rental rental = rentalRepository
                .findByBookIdAndReturnedAtIsNull(book.getId())
                .orElseThrow(() -> new RuntimeException("貸出中データなし"));

        // ③ 返却日時設定
        rental.setReturnedAt(LocalDateTime.now());
        rentalRepository.save(rental);

        // ④ 本の状態戻す
        book.setStatus("AVAILABLE");
        bookRepository.save(book);

        return "返却完了";
    }
    @GetMapping("/active")
    public List<RentalView> getActiveRentals() {

        List<Rental> rentals = rentalRepository.findByReturnedAtIsNull();

        return rentals.stream().map(r -> {
            Book book = bookRepository.findById(r.getBookId()).orElse(null);

            RentalView v = new RentalView();
            v.setBookTitle(book.getTitle());
            v.setQrCode(book.getQrCode());
            v.setUserId(r.getUserId());

            return v;
        }).toList();
    }
}