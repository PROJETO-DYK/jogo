package Util;

public class Menu {

    public Menu() {}
    
    public void MenuInicializacao()
    {
        System.out.println("#####################################");
        System.out.println("################ DYK ################");
        System.out.println("#####################################\n");
        
        System.out.println("[1] - Iniciar o jogo");
        System.out.println("[2] - Informações dos desenvolvedores");
        System.out.println("[3] - Explicação do Jogo");
        System.out.println("[4] - Cadastrar novas perguntas!");
        System.out.println("[0] - Sair\n");
    } 
    public void MenuInicial()
    {
        System.out.println("######################################");
        System.out.println("############ Menu Inicial ############");
        System.out.println("######################################\n");
        
        System.out.println("[1] - Logar");
        System.out.println("[2] - Cadastrar");
        System.out.println("[3] - Ranking");
        System.out.println("[0] - Sair\n");
    }
    public void MenuRegras()
    {
        System.out.println("#####################################");
        System.out.println("########### Regra do jogo ###########");
        System.out.println("#####################################\n");
        
        System.out.print("-->Necessário dois jogadores;\n" + 
                "\n" + "-->Um personagem deverá ser escolhido para cada jogador;\n" + 
                "\n" + "-->Ambos iniciam com 100% de vida;\n" + 
                "\n" + "-->Cada pergunta respondida incorretamente, será debitado o total de 5% chegando a zero;\n" + 
                "\n" + "-->A qualquer momento os jogadores podem solicitar o encerramento da partida\n" + 
                "  ->Caso o outro jogador não esteja de acordo com o término do jogo 15% de vida do jogador solicitante será deduzido\n" + 
                "  ->Esse será repassado para o outro jogador\n" + "\n" + "-->em seguida a partida é encerrada e verifica quem é o vencedor\n" + 
                "\n" + "-->O primeiro jogador a atingir 0% de vida será o perdedor da partida;\n" + "\n" + 
                "-->Vence o jogador que permanecer com a maior vida;");
        
        
        try
        {
            Thread.sleep(5000);
        }catch(InterruptedException e){}
    }
    public void MenuEncerramento()
    {
        System.out.println("#####################################");
        System.out.println("############ FIM DE JOGO ############");
        System.out.println("#####################################\n");    
        
    }
    
}
