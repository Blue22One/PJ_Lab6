package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Angajat> aList = new ArrayList<>();
        aList = citire();

        System.out.println("---------1)----------");
        aList.forEach(angajat -> System.out.println(angajat.toString()));

        System.out.println("---------2)----------");
        Predicate<Float> over2500 = i -> (i > 2500);
        aList.stream()
                .filter(angajat -> over2500.test(angajat.getSalariu()))
                .forEach(System.out::println);

        System.out.println("---------3)----------");
        String year = (Year.now().minusYears(1)).toString();
        System.out.println(Integer.parseInt(year));
        Predicate<String> sefJob = s -> (s.equals("director") || s.equals("sef"));
        Predicate<LocalDate> correctDate = d -> (d.getMonth() == Month.APRIL && (d.getYear() == Integer.parseInt(year)));
        List<Angajat> sefi = aList.stream()
                .filter(angajat -> sefJob.test(angajat.getPost()))
                .filter(angajat -> correctDate.test(angajat.getData_ang()))
                .collect(Collectors.toList());
        sefi.forEach(angajat -> System.out.println(angajat.toString()));

        System.out.println("---------4)----------");
        Predicate<String> nusefJob = s -> !(s.equals("director") || s.equals("sef"));

        List<Angajat> nuSefi = aList.stream()
                .filter(angajat -> nusefJob.test(angajat.getPost()))
                .sorted(Comparator.comparing(Angajat::getSalariu).reversed())
                .collect(Collectors.toList());
        nuSefi.forEach(angajat -> System.out.println(angajat.toString()));

        System.out.println("---------5)----------");
        List<String> names = aList.stream()
                .map(angajat -> angajat.getNume().toUpperCase())
                .collect(Collectors.toList());
        System.out.println(names);

        System.out.println("---------6)----------");
        Predicate<Float> maiMicde3000 = i -> (i < 3000);
        aList.stream()
                .map(Angajat::getSalariu)
                .filter(maiMicde3000::test)
                .forEach(System.out::println);

        System.out.println("---------7)----------");
        Optional<Angajat> ang = aList.stream()
                .min(Comparator.comparing(Angajat::getData_ang));
        if (ang.isPresent())
            System.out.println(ang);
        else
            System.out.println("No data found");

        System.out.println("---------8)----------");
        DoubleSummaryStatistics l = aList.stream()
                .collect(Collectors.summarizingDouble(Angajat::getSalariu));
        //System.out.println(l);
        System.out.println("Minim: " + l.getMin() +", Mediu: " + l.getAverage() + ", Maxim: " + l.getMax());

        System.out.println("---------9)----------");
        Optional<Angajat> avemSauNuIon = aList.stream()
                .filter(angajat -> angajat.getNume().equals("Ion"))
                .findAny();

        avemSauNuIon.ifPresentOrElse(
                angajat -> System.out.println("Avem cel putin un Ion in firma"),
                () -> System.out.println("Firma nu are nici un Ion angajat")
        );

        System.out.println("---------10)----------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate start = LocalDate.parse("31/05/2022", formatter);
        LocalDate finish = LocalDate.parse("01/09/2022", formatter);
        var numar = aList.stream()
                .filter(angajat -> (angajat.getData_ang().isAfter(start) && angajat.getData_ang().isBefore(finish)))
                .count();

        System.out.println(numar);
    }

    public static List<Angajat> citire() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            File file = new File("src/main/resources/angajati.json");
            List<Angajat> a = mapper.readValue(file, new TypeReference<List<Angajat>>() {});
            return a;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void scriere(List<Angajat> list) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            File file = new File("src/main/resources/angajati.json");
            mapper.writeValue(file, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}