package com.adpanel.adpanel.logic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LinkGenerator {
    Random random = new Random();
    StringBuilder link;

    public String generateLink() {
        link = new StringBuilder();
        link.append(generateFirst5Chars()).append('-').append(generateRestOfLink());
        return link.toString();
    }
    private String generateFirst5Chars() {
        StringBuilder first5Chars = new StringBuilder();
        char randomChar;
        for(int i = 0; i < 5; i++) {
            switch (random.nextInt(3)) {
                case 0:
                    randomChar = (char) (random.nextInt(10) + 48);
                    break;
                case 1:
                    randomChar = (char) (random.nextInt(26) + 65);
                    break;
                case 2:
                    randomChar = (char) (random.nextInt(26) + 97);
                    break;
                default:
                    randomChar = ' ';
            }
            first5Chars.append(randomChar);
        }
        return first5Chars.toString();
    }
    private String generateRestOfLink() {
        StringBuilder sb = new StringBuilder();
        Date notFormatDay = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMHHmm");
        return sdf.format(notFormatDay);
    }
}
