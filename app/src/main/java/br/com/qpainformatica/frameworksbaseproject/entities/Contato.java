package br.com.qpainformatica.frameworksbaseproject.entities;

import com.orm.SugarRecord;

/**
 * Created by elcio on 24/05/15.
 */
public class Contato extends SugarRecord<Contato> {

    public Long id;
    public String nome;
    public String telefone;
    public String nomeImagem;

    public Contato() {
    }

    public Contato(String nome, String telefone, String nomeImagem) {
        this.nome = nome;
        this.telefone = telefone;
        this.nomeImagem = nomeImagem;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }
}
