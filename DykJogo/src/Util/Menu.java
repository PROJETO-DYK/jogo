package Util;

public class Menu {

    public Menu() {}
    
    public void MenuInicializacao()
    {
        System.out.println("######################################");
        System.out.println("############ Menu Inicial ############");
        System.out.println("######################################\n");
        
        System.out.println("[1] - Iniciar o jogo");
        System.out.println("[2] - Informações dos desenvolvedores");
        System.out.println("[3] - Explicação do Jogo");
        System.out.println("[0] - Sair\n");
    } 
    public void MenuInicial()
    {
    
    }
    public void MenuInformacoes()
    {
    
    }
    public void MenuRegras()
    {
        System.out.println("Regra do Jogo: \n\n");
        System.out.print("""
                         -->Necess\u00e1rio dois jogadores;
                         
                         -->Um personagem dever\u00e1 ser escolhido para cada jogador;
                         
                         -->Ambos iniciam com 100% de vida;
                         
                         -->Cada pergunta respondida incorretamente, ser\u00e1 debitado o total de 5% chegando a zero;
                         
                         -->A qualquer momento os jogadores podem solicitar o encerramento da partida
                           ->Caso o outro jogador n\u00e3o esteja de acordo com o t\u00e9rmino do jogo 15% de vida do jogador solicitante ser\u00e1 deduzido 
                           ->Esse será repassado para o outro jogador
                         
                         -->em seguida a partida \u00e9 encerrada e verifica quem \u00e9 o vencedor
                         
                         -->O primeiro jogador a atingir 0% de vida ser\u00e1 o perdedor da partida;
                         
                         -->Vence o jogador que permanecer com a maior vida;""");
        
        
        try
        {
            Thread.sleep(5000);
        }catch(InterruptedException e){}
    }
    public void MenuEncerramento()
    {
        System.out.println("######################################");
        System.out.println("############ FIM DE JOGO #############");
        System.out.println("######################################\n");    
        
    }
    
}
