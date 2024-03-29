package com.epam.esm.extractor.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.extractor.FieldExtractor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.entity.GiftCertificateTableColumnNames.*;

@Component
public class CertificateFieldExtractor implements FieldExtractor<GiftCertificate> {
    public Map<String, String> extract(GiftCertificate item) {
        String name = item.getName();
        String description = item.getDescription();
        String priceString = item.getPrice() != null ? String.valueOf(item.getPrice()) : null;
        String durationString = item.getDuration() != null ? String.valueOf(item.getDuration()) : null;
        LocalDateTime lastUpdateDate = item.getLastUpdateDate();
        String idString = String.valueOf(item.getId());

        Map<String, String> map = new HashMap<>();
        map.put(NAME, name);
        map.put(DESCRIPTION, description);
        map.put(PRICE, priceString);
        map.put(DURATION, durationString);
        map.put(LAST_UPDATE_DATE, lastUpdateDate.toString());
        map.put(ID, idString);
        return map;
    }

}
