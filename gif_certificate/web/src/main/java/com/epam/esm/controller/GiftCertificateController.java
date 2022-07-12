package com.epam.esm.controller;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> allGiftCertificates() throws DaoException {
        return giftCertificateService.getAll();
    }

    @GetMapping("/{id}")
    public GiftCertificate giftCertificateById(@PathVariable long id) throws DaoException, IncorrectParameterException {
        return giftCertificateService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGiftCertificate(@PathVariable long id) throws DaoException, IncorrectParameterException {
        giftCertificateService.removeById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    @PostMapping
    public ResponseEntity createGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws DaoException, IncorrectParameterException {
        giftCertificateService.insert(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/{id}/tags")
    public List<Tag> getAssociatedTags(@PathVariable long id) throws DaoException, IncorrectParameterException {
        return giftCertificateService.getAssociatedTags(id);
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity addAssociatedTags(@RequestBody List<Tag> tags,
                                            @PathVariable long id) throws DaoException, IncorrectParameterException {
        giftCertificateService.addAssociatedTags(id, tags);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @DeleteMapping("/{id}/tags")
    public ResponseEntity deleteAssociatedTags(@RequestBody List<Tag> tags,
                                               @PathVariable long id) throws DaoException, IncorrectParameterException {
        giftCertificateService.deleteAssociatedTags(id, tags);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateGiftCertificate(@PathVariable long id,
                                                        @RequestBody GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        giftCertificateService.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/filter")
    public List<GiftCertificate> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams) throws DaoException {
        return giftCertificateService.doFilter(allRequestParams);
    }
}
