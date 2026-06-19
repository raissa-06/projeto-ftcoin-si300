package com.carteira.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa uma movimentação (compra ou venda) em uma carteira de moeda virtual.
 */
public class Movimentacao {

    public enum TipoOperacao {
        COMPRA('C'), VENDA('V');

        private final char codigo;

        TipoOperacao(char codigo) {
            this.codigo = codigo;
        }

        public char getCodigo() {
            return codigo;
        }

        public static TipoOperacao fromCodigo(char codigo) {
            for (TipoOperacao tipo : values()) {
                if (tipo.codigo == codigo) return tipo;
            }
            throw new IllegalArgumentException("Tipo de operação inválido: " + codigo);
        }
    }

    private int idMovimento;
    private int idCarteira;
    private LocalDate dataOperacao;
    private TipoOperacao tipoOperacao;
    private BigDecimal quantidade;
    private BigDecimal cotacaoNaData; // cotação do oráculo na data da operação

    public Movimentacao() {}

    public Movimentacao(int idMovimento, int idCarteira, LocalDate dataOperacao,
                        TipoOperacao tipoOperacao, BigDecimal quantidade, BigDecimal cotacaoNaData) {
        this.idMovimento = idMovimento;
        this.idCarteira = idCarteira;
        this.dataOperacao = dataOperacao;
        this.tipoOperacao = tipoOperacao;
        this.quantidade = quantidade;
        this.cotacaoNaData = cotacaoNaData;
    }

    // Calcula o valor financeiro da operação (quantidade × cotação)
    public BigDecimal getValorFinanceiro() {
        if (cotacaoNaData == null || quantidade == null) return BigDecimal.ZERO;
        return quantidade.multiply(cotacaoNaData);
    }

    // Getters e Setters
    public int getIdMovimento() { return idMovimento; }
    public void setIdMovimento(int idMovimento) { this.idMovimento = idMovimento; }

    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }

    public LocalDate getDataOperacao() { return dataOperacao; }
    public void setDataOperacao(LocalDate dataOperacao) { this.dataOperacao = dataOperacao; }

    public TipoOperacao getTipoOperacao() { return tipoOperacao; }
    public void setTipoOperacao(TipoOperacao tipoOperacao) { this.tipoOperacao = tipoOperacao; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public BigDecimal getCotacaoNaData() { return cotacaoNaData; }
    public void setCotacaoNaData(BigDecimal cotacaoNaData) { this.cotacaoNaData = cotacaoNaData; }

    @Override
    public String toString() {
        return String.format("Movimentacao{id=%d, carteira=%d, data=%s, tipo=%s, qtd=%s, cotacao=%s}",
                idMovimento, idCarteira, dataOperacao, tipoOperacao, quantidade, cotacaoNaData);
    }
}
