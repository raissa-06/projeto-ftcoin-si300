package src.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import src.dao.CarteiraDAO;
import src.dao.CotacaoDAO;
import src.dao.MovimentacaoDAO;
import src.model.Carteira;
import src.model.Cotacao;
import src.model.Movimentacao;
import src.model.TipoMovimentacao;

public class MovimentacaoController {

    private final MovimentacaoDAO movimentacaoDAO;
    private final CotacaoDAO cotacaoDAO;
    private final CarteiraDAO carteiraDAO;

    public MovimentacaoController(
            MovimentacaoDAO movimentacaoDAO,
            CotacaoDAO cotacaoDAO,
            CarteiraDAO carteiraDAO) {

        this.movimentacaoDAO = movimentacaoDAO;
        this.cotacaoDAO = cotacaoDAO;
        this.carteiraDAO = carteiraDAO;
    }

    // Registra uma COMPRA
    public boolean registrarCompra(int idCarteira, LocalDate data, BigDecimal quantidade) {

        Carteira carteira = carteiraDAO.consultar(idCarteira);

        if (carteira == null) {
            System.out.println("Carteira não encontrada.");
            return false;
        }

        Cotacao cotacao = cotacaoDAO.consultar(data);

        if (cotacao == null) {
            System.out.println("Erro: cotação não encontrada para a data " + data);
            return false;
        }

    System.out.println("Cotação encontrada: " + cotacao.getValor());

        BigDecimal valorCompra = quantidade.multiply(cotacao.getValor());

        if (carteira.getSaldoFinanceiro().compareTo(valorCompra) < 0) {
            System.out.println("Saldo financeiro insuficiente.");
            return false;
        }

        carteira.setSaldoFinanceiro(carteira.getSaldoFinanceiro().subtract(valorCompra));

        carteiraDAO.atualizar(carteira);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setIdCarteira(idCarteira);
        movimentacao.setDataOperacao(data);
        movimentacao.setTipoMovimentacao(TipoMovimentacao.COMPRA);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setCotacaoNaData(cotacao.getValor());

        movimentacaoDAO.inserir(movimentacao);

        return true;
    }

    // Registra uma VENDA
    public boolean registrarVenda(int idCarteira, LocalDate data, BigDecimal quantidade) {

        BigDecimal saldoMoedas = calcularSaldo(idCarteira);

        if (quantidade.compareTo(saldoMoedas) > 0) {
            System.out.println("Saldo de moedas insuficiente.");
            return false;
        }

        Carteira carteira = carteiraDAO.consultar(idCarteira);

        if (carteira == null) {
            System.out.println("Carteira não encontrada.");
            return false;
        }

        Cotacao cotacao = cotacaoDAO.consultar(data);

        if (cotacao == null) {
            System.out.println("Erro: cotação não encontrada para a data " + data);
            return false;
        }

        BigDecimal valorVenda = quantidade.multiply(cotacao.getValor());

        carteira.setSaldoFinanceiro(
                carteira.getSaldoFinanceiro().add(valorVenda));

        carteiraDAO.atualizar(carteira);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setIdCarteira(idCarteira);
        movimentacao.setDataOperacao(data);
        movimentacao.setTipoMovimentacao(TipoMovimentacao.VENDA);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setCotacaoNaData(cotacao.getValor());

        movimentacaoDAO.inserir(movimentacao);

        return true;
    }

    // Calcula o saldo de moedas (compras - vendas)
    public BigDecimal calcularSaldo(int idCarteira) {

        List<Movimentacao> lista = movimentacaoDAO.listarPorCarteira(idCarteira);

        BigDecimal saldo = BigDecimal.ZERO;

        for (Movimentacao m : lista) {
            if (m.getTipoMovimentacao() == TipoMovimentacao.COMPRA) {
                saldo = saldo.add(m.getQuantidade());
            } else {
                saldo = saldo.subtract(m.getQuantidade());
            }
        }

        return saldo;
    }

    // Lista movimentações de uma carteira
    public List<Movimentacao> listarPorCarteira(int idCarteira) {
        return movimentacaoDAO.listarPorCarteira(idCarteira);
    }

    // Lista todas as movimentações
    public List<Movimentacao> listarTodos() {
        return movimentacaoDAO.listarTodas();
    }

    // Busca movimentação por ID
    public Movimentacao buscarPorId(int id) {
        return movimentacaoDAO.consultar(id);
    }

    // Exclui movimentação
    public void deletar(int id) {
        movimentacaoDAO.excluir(id);
    }
}

