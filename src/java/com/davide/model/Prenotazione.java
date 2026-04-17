/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davide.model;

import java.util.Date;

/**
 *
 * @author david
 */
public class Prenotazione {
    
    private int id;
    private Utente utente;
    private Date data;
    private Date oraDa;
    private Date oraA;
    private int posti;
    private String note;
    private String nomeIdentificativo;
    private String telefono;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNomeIdentificativo() {
        return nomeIdentificativo;
    }

    public void setNomeIdentificativo(String nomeIdentificativo) {
        this.nomeIdentificativo = nomeIdentificativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Costruttore vuoto
    public Prenotazione() {
    }

    // Costruttore completo
    public Prenotazione(Utente utente, Date data, Date oraDa, Date oraA, int posti, String note) {
        this.utente = utente;
        this.data = data;
        this.oraDa = oraDa;
        this.oraA = oraA;
        this.posti = posti;
        this.note = note;
    }

    // Getter e Setter
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getOraDa() {
        return oraDa;
    }

    public void setOraDa(Date oraDa) {
        this.oraDa = oraDa;
    }

    public Date getOraA() {
        return oraA;
    }

    public void setOraA(Date oraA) {
        this.oraA = oraA;
    }

    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}
