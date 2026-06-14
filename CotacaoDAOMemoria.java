import java.util.ArrayList;
import java.util.List;
public class CotacaoDAOMemoria implements CotacaoDAO {
    private final List<Cotacao> bancoDeDados = new ArrayList<>();

    @Override
    public void atualizar(Cotacao cotacao) {
        for (int i = 0; i < bancoDeDados.size(); i++) {
            if (bancoDeDados.get(i).getIdentificador() == cotacao.getIdentificador()) {
                bancoDeDados.set(i, cotacao);
                return;
            }
        }
    }

    @Override
    public Cotacao consultar(int id) {
        for (Cotacao cotacaoID : bancoDeDados) {
            if (cotacaoID.getIdentificador() == id) {
                return cotacaoID;
            }
        }
        return null;
    }

    @Override
    public void excluir(int id) {
        bancoDeDados.removeIf(cotacaoID -> cotacaoID.getIdentificador() == id);
    }

    @Override
    public void inserir(Cotacao cotacao) {
        bancoDeDados.add(cotacao);
    }

    @Override
    public List<Cotacao> listarTodas() {
        return new ArrayList<>(bancoDeDados);
    }
    
    
}
