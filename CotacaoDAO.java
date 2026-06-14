import java.util.List;

public interface CotacaoDAO {
    void inserir(Cotacao cotacao);
    Cotacao consultar(int id);
    void atualizar(Cotacao cotacao);
    void excluir(int id);
    List<Cotacao> listarTodas();
}