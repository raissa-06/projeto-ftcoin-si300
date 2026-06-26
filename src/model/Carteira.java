package src.model;
import java.math.BigDecimal;

//testando
public class Carteira{
    private int identificador;
    private String nomeTitular;
    private String corretora;
    private BigDecimal saldoFinanceiro;

    public Carteira(int identificador, String nomeTitular, String corretora, BigDecimal saldo){
        this.identificador=identificador;
        this.nomeTitular=nomeTitular;
        this.corretora=corretora;
        this.saldoFinanceiro = saldo;
    }

    public int getIdentificador(){
        return identificador;
    }

    public void setIdentificador(int identificador){
        this.identificador=identificador;
    }

    public String getNomeTitular(){
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular){
        this.nomeTitular=nomeTitular;
    }

    public String getCorretora(){
        return corretora; 
    }

    public void setCorretora(String corretora){
        this.corretora = corretora; 
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Titular: %s | Corretora: %s", identificador, nomeTitular, corretora);
    }

    public BigDecimal getSaldoFinanceiro() {
        return saldoFinanceiro;
    }

    public void setSaldoFinanceiro(BigDecimal saldoFinanceiro) {
        this.saldoFinanceiro = saldoFinanceiro;
    }

}